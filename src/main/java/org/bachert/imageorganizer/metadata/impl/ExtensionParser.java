package org.bachert.imageorganizer.metadata.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bachert.imageorganizer.metadata.MetadataExpander;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class ExtensionParser implements MetadataExpander {

    @Override
    public void accept(FileMetadata fileMetadata) {
        log.debug("Parsing extension of file {}", fileMetadata.getPath().toString());
        fileMetadata.setExtension(FilenameUtils.getExtension(fileMetadata.getPath().toString()));
    }
}
