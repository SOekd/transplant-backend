package com.transplantados.alert;

import com.transplantados.transplant.TransplantLogBook;
import com.transplantados.variables.VariableInput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertService {

    private final AlertRuleRepository alertRuleRepository;

    private final AlertRepository alertRepository;

    public void evaluateAndNotify(@Validated TransplantLogBook logBook) {
        Map<UUID, BigDecimal> values = logBook.getInputs().stream()
                .collect(Collectors.toMap(i -> i.getVariable().getId(), VariableInput::getValue));

        alertRuleRepository.findAll().forEach(rule -> {
            boolean match = rule.getConditions().stream()
                    .map(cond -> {
                        BigDecimal actual = values.get(cond.getVariable().getId());
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
                        .confirmed(false)
                        .build();

                alertRepository.save(alert);

                if (rule.isNotifyPatient()) {
                    // ... lógica de notificação ao paciente
                }
                if (rule.isNotifyMedicalTeam()) {
                    // ... lógica de notificação à equipe
                }
            }
        });
    }

}
