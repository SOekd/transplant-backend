package com.transplantados.alert;

import com.transplantados.alert.dto.CreateAlertRuleRequest;
import com.transplantados.notification.NotificationRepository;
import com.transplantados.transplant.TransplantLogBook;
import com.transplantados.variables.VariableInput;
import com.transplantados.variables.VariableRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRuleRepository alertRuleRepository;

    private final VariableRepository variableRepository;

    private final AlertRepository alertRepository;

    private final NotificationRepository notificationRepository;

    public void evaluateAndNotify(@Validated TransplantLogBook logBook) {
        Map<UUID, BigDecimal> values = logBook.getInputs().stream()
                .collect(Collectors.toMap(i -> i.getVariable().getId(), VariableInput::getValue));

        alertRuleRepository.findAll().forEach(rule -> {
            boolean match = rule.getConditions().stream()
                    .map(cond -> {
                        BigDecimal actual = values.get(cond.getVariable().getId());

                        if (actual == null) {
                            return false;
                        }

                        BigDecimal threshold = cond.getThresholdValue();
                        return switch (cond.getOperator()) {
                            case LESS_THAN -> actual.compareTo(threshold) < 0;
                            case LESS_OR_EQUAL -> actual.compareTo(threshold) <= 0;
                            case EQUAL -> actual.compareTo(threshold) == 0;
                            case GREATER_OR_EQUAL -> actual.compareTo(threshold) >= 0;
                            case GREATER_THAN -> actual.compareTo(threshold) > 0;
                        };
                    })
                    .reduce(rule.getLogicalOperator() == LogicalOperator.AND
                            , (a, b) -> rule.getLogicalOperator() == LogicalOperator.AND ? a && b : a || b);

            if (match) {
                Alert alert = Alert.builder()
                        .rule(rule)
                        .logBook(logBook)
                        .patient(logBook.getPatient())
                        .confirmed(false)
                        .triggeredAt(LocalDateTime.now())
                        .build();

                alertRepository.save(alert);

                if (rule.isNotifyPatient()) {

                }
                if (rule.isNotifyMedicalTeam()) {

                }
            }
        });
    }

    public AlertRule createAlertRule(CreateAlertRuleRequest request) {
        AlertRule rule = AlertRule.builder()
                .name(request.name())
                .description(request.description())
                .logicalOperator(request.logicalOperator())
                .notifyPatient(request.notifyPatient())
                .notifyMedicalTeam(request.notifyMedicalTeam())
                .build();

        List<AlertCondition> conditions = request.conditions().stream().map(condReq -> {
            var variable = variableRepository.findById(condReq.variableId())
                    .orElseThrow(() -> new EntityNotFoundException("Variable not found with id: " + condReq.variableId()));
            return AlertCondition.builder()
                    .rule(rule)
                    .variable(variable)
                    .operator(condReq.operator())
                    .thresholdValue(condReq.thresholdValue())
                    .build();
        }).collect(Collectors.toList());

        rule.setConditions(conditions);
        return alertRuleRepository.save(rule);
    }

    public void removeAlertRule(UUID alertRuleId) {
        if (!alertRuleRepository.existsById(alertRuleId)) {
            throw new EntityNotFoundException("AlertRule not found with id: " + alertRuleId);
        }
        alertRuleRepository.deleteById(alertRuleId);
    }

    public List<Alert> findAllAlerts(Boolean confirmed) {
        if (confirmed == null) {
            return alertRepository.findAll();
        }
        return alertRepository.findByConfirmed(confirmed);
    }

    public Alert confirmAlert(UUID alertId, boolean confirmAll) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found with id: " + alertId));
        alert.setConfirmed(true);
        alert.setConfirmedAt(LocalDateTime.now());

        if (confirmAll) {
            List<Alert> allAlerts = alertRepository.findAllByPatientId(alert.getPatient().getId());
            allAlerts.forEach(a -> {
                a.setConfirmed(true);
                a.setConfirmedAt(LocalDateTime.now());
            });
            alertRepository.saveAll(allAlerts);
        }

        return alertRepository.save(alert);
    }

    public List<AlertRule> findAllAlertRules() {
        return alertRuleRepository.findAll();
    }


}
