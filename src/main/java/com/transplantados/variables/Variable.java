package com.transplantados.variables;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Entity
@Table
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Variable {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String unityOfMeasure;

    private boolean isSwitch;

    private boolean showInLogBook;

    @Nullable
    @Enumerated(EnumType.STRING)
    private DeviceVariable deviceVariable;

}
