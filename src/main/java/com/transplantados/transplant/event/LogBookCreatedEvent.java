package com.transplantados.transplant.event;

import com.transplantados.transplant.TransplantLogBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LogBookCreatedEvent {

    private TransplantLogBook logBook;

}
