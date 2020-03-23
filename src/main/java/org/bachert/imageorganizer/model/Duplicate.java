package org.bachert.imageorganizer.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class Duplicate {

    private Set<FileMetadata> files = new HashSet<>();

    private boolean resolved;

    public void add(FileMetadata file) {
        this.files.add(file);
    }
}
