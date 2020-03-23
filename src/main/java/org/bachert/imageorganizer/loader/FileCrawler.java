package org.bachert.imageorganizer.loader;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bachert.imageorganizer.model.FileMetadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCrawler {

    private static final Logger logger = LogManager.getLogger(FileCrawler.class);

    public static List<FileMetadata> getFiles(String path) {
        try {
            logger.debug("Fetching files from path {}", path);
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(file -> FilenameUtils.getExtension(file.toString()).toLowerCase().equals("jpg"))
                    .map(FileMetadata::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to crawl files", e);
        }
        return new ArrayList<>();
    }
}
