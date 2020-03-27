package org.bachert.imageorganizer.process.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessConfigurationDTO {

    private String directory;

    private DuplicateConfigurationDTO duplicates;

    private GalleryConfigurationDTO galleries;
}
