package org.bachert.imageorganizer.process.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripConfigurationDTO {

    private boolean enabled;

    private double distance;

    private double hoursBetween;
}
