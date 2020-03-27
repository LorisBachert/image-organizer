package org.bachert.imageorganizer.process.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateConfigurationDTO {

    private boolean enabled;

    private double diff;
}
