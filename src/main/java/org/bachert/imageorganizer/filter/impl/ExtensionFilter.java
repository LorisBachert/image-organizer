package org.bachert.imageorganizer.filter.impl;

import org.apache.commons.io.FilenameUtils;
import org.bachert.imageorganizer.filter.FileFilter;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.springframework.stereotype.Component;

@Component
public class ExtensionFilter implements FileFilter {

    @Override
    public Boolean apply(FileMetadata fileMetadata) {
        String extension = FilenameUtils.getExtension(fileMetadata.getPath().toString()).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg");
    }
}
