package org.bachert.imageorganizer.process.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessStateDTO {

    private boolean started;

    private String directory;
}
