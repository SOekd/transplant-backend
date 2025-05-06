package com.transplantados.variables.dto;

import com.transplantados.variables.DeviceVariable;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.Nullable;

public record CreateVariableRequest(
        @NotBlank
        String name,

        @NotBlank
        String unityOfMeasure,

        @NotBlank
        boolean isSwitch,

        @NotBlank
        boolean showInLogBook,

        @Nullable
        DeviceVariable deviceVariable
) {
}
