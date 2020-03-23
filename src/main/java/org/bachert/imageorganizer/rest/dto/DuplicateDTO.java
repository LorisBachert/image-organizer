package org.bachert.imageorganizer.rest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Setter
@Getter
public class DuplicateDTO {

    private Set<FileMetadataDTO> files = new HashSet<>();
}
