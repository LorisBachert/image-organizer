package org.bachert.imageorganizer.filter.impl;

import org.apache.commons.io.FilenameUtils;
import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExtensionFilter implements Function<FileMetadata, Boolean> {

    @Override
    public Boolean apply(FileMetadata fileMetadata) {
        String extension = FilenameUtils.getExtension(fileMetadata.getPath().toString()).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg");
    }
}
