package com.transplantados.alert;

import com.transplantados.alert.dto.ConfirmAlertRequest;
import com.transplantados.alert.dto.CreateAlertRuleRequest;
import com.transplantados.shared.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.Alert.BASE_ROUTE)
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping(Routes.Alert.GET_ALL)
    public ResponseEntity<List<Alert>> getAllAlerts(@RequestParam(required = false) Boolean confirmed) {
        return ResponseEntity.ok(alertService.findAllAlerts(confirmed));
    }

    @PostMapping(Routes.Alert.CREATE)
    public ResponseEntity<AlertRule> createAlertRule(@RequestBody @Validated CreateAlertRuleRequest request) {
        return ResponseEntity.ok(alertService.createAlertRule(request));
    }

    @PostMapping(Routes.Alert.CONFIRM)
    public ResponseEntity<Alert> confirmAlert(@PathVariable UUID alertId, @RequestBody @Validated ConfirmAlertRequest request) {
        return ResponseEntity.ok(alertService.confirmAlert(alertId, request.confirmAll()));
    }

    @DeleteMapping(Routes.Alert.REMOVE)
    public ResponseEntity<Void> removeAlertRule(@PathVariable UUID alertRuleId) {
        alertService.removeAlertRule(alertRuleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(Routes.Alert.GET_ALL_RULES)
    public ResponseEntity<List<AlertRule>> getAllAlertRules() {
        return ResponseEntity.ok(alertService.findAllAlertRules());
    }

}
