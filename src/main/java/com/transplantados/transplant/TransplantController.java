package com.transplantados.transplant;

import com.transplantados.shared.Routes;
import com.transplantados.transplant.dto.CreateTransplantLogBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(Routes.Transplant.BASE_ROUTE)
@RestController
public class TransplantController {

    private final TransplantService transplantService;

    @GetMapping(Routes.Transplant.GET_ALL)
    public List<Transplant> getAll() {
        return transplantService.findAll();
    }

    @PostMapping(Routes.Transplant.LOGBOOK)
    public void createLogBook(@RequestBody @Validated CreateTransplantLogBookRequest request) {
        transplantService.createLogBook(request);
    }

    @GetMapping(Routes.Transplant.LOGBOOK)
    public List<TransplantLogBook> getLogBooks(@RequestParam() UUID patientId) {
        return transplantService.findLogBooksByPatientId(patientId);
    }

}
