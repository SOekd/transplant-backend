package com.transplantados.variables;

import com.transplantados.shared.Routes;
import com.transplantados.variables.dto.CreateVariableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Routes.Variable.BASE_ROUTE)
@RequiredArgsConstructor
public class VariableController {

    private final VariableService variableService;

    @GetMapping(Routes.Variable.GET_ALL)
    public List<Variable> getAll() {
        return variableService.findAll();
    }

    @DeleteMapping(Routes.Variable.REMOVE)
    public void remove(@PathVariable UUID variableId) {
        variableService.remove(variableId);
    }

    @PostMapping(Routes.Variable.CREATE)
    public Variable create(@RequestBody @Validated CreateVariableRequest request) {
        return variableService.create(request);
    }

}
