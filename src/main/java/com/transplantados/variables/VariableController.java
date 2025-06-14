package com.transplantados.variables;

import com.transplantados.shared.Routes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.Variable.BASE_ROUTE)
@RequiredArgsConstructor
public class VariableController {

    private final VariableService variableService;

    @GetMapping(Routes.Variable.GET_ALL)
    public List<Variable> getAll() {
        return variableService.findAll();
    }

}
