package org.bachert.imageorganizer.expander.impl;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ExtensionParser implements Consumer<FileMetadata> {

    private static final Logger logger = LogManager.getLogger(ExtensionParser.class);

    @Override
    public void accept(FileMetadata fileMetadata) {
        logger.debug("Parsing extension of file {}", fileMetadata.getPath().toString());
        fileMetadata.setExtension(FilenameUtils.getExtension(fileMetadata.getPath().toString()));
    }
}
