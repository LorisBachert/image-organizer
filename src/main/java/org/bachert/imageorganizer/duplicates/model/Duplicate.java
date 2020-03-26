package org.bachert.imageorganizer.duplicates.model;

import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class Duplicate {

    private Long id;

    private Set<FileMetadata> files = new HashSet<>();

    private boolean resolved;

    public void add(FileMetadata file) {
        this.files.add(file);
    }
}
