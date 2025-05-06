package com.transplantados.transplant;

import com.transplantados.shared.Routes;
import com.transplantados.transplant.dto.CreateTransplantLogBookRequest;
import com.transplantados.transplant.dto.CreateTransplantRequest;
import com.transplantados.transplant.dto.UpdateTransplantRequest;
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

    @DeleteMapping(Routes.Transplant.REMOVE)
    public void remove(@PathVariable UUID transplantId) {
        transplantService.remove(transplantId);
    }

    @PostMapping(Routes.Transplant.CREATE)
    public Transplant create(@RequestBody @Validated CreateTransplantRequest request) {
        return transplantService.create(request);
    }

    @PutMapping(Routes.Transplant.UPDATE)
    public Transplant update(@RequestBody @Validated UpdateTransplantRequest request) {
        return transplantService.update(request);
    }

    @PostMapping(Routes.Transplant.LOGBOOK)
    public void createLogBook(@RequestBody @Validated CreateTransplantLogBookRequest request) {
        transplantService.createLogBook(request);
    }


}
