package org.bachert.imageorganizer.duplicates.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Setter
@Getter
public class DuplicateDTO {

    private String id;

    private Set<FileMetadataDTO> files = new HashSet<>();

    private boolean resolved;
}
