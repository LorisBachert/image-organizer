package io;

import model.FileMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileCrawler {

    private static final Logger logger = LogManager.getLogger(FileCrawler.class);

    public static Stream<FileMetadata> getFiles(String path) {
        try {
            logger.debug("Fetching files from path {}", path);
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(FileMetadata::new);
        } catch (IOException e) {
            logger.error("Failed to crawl files", e);
        }
        return Stream.empty();
    }
}
