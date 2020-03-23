package org.bachert.imageorganizer.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
public class Duplicates {

    private Set<FileMetadata> files = new HashSet<>();

    public void add(FileMetadata file) {
        this.files.add(file);
    }
}
