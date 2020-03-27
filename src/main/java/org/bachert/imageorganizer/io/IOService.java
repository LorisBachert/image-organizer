package org.bachert.imageorganizer.io;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.galleries.model.Gallery;
import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class IOService {

    private static final String GALLERY_DIRECTORY_PATTERN = "%s\\%s";

    private static final String FILE_PATTERN = "%s\\%s_%d.%s";

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

    public static void delete(FileMetadata file) {
        try {
            log.debug("Deleting file: {}", file.getPath());
            Files.delete(file.getPath());
        } catch (IOException e) {
            log.error("Failed to delete file: {}", file.getPath(), e);
        }
    }

    public static void moveFiles(String rootDirectory, Gallery gallery, List<FileMetadata> files) {
        if (files.isEmpty()) {
            log.info("Gallery {} contains no files", gallery.getName());
            return;
        }
        log.info("Moving files for gallery: {}", gallery.getName());
        String galleryDirectoryPath = String.format(GALLERY_DIRECTORY_PATTERN, rootDirectory, gallery.getName());
        File galleryDirectory = new File(galleryDirectoryPath);
        if (!galleryDirectory.exists() && !galleryDirectory.mkdir()) {
            log.error("Failed to create directory: {}", galleryDirectoryPath);
            return;
        }
        IntStream.range(0, files.size()).forEach(index -> {
            FileMetadata file = files.get(index);
            log.info("Moving file '{}' for gallery '{}'", file.getPath(), gallery.getName());
            String newFileName = String.format(FILE_PATTERN, galleryDirectoryPath, gallery.getName(), index, file.getExtension());
            try {
                Files.copy(file.getPath(), Paths.get(newFileName));
            } catch (IOException e) {
                log.error("Failed to delete file: {}", file.getPath(), e);
            }
        });
    }
}
