package org.bachert.imageorganizer.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileCrawler {

    public static List<Path> getFiles(String path) {
        try {
            log.debug("Fetching files from path {}", path);
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to crawl files", e);
        }
        return new ArrayList<>();
    }
}
