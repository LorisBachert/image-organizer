package org.bachert.imageorganizer.images;

import net.coobird.thumbnailator.Thumbnails;
import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;
import org.bachert.imageorganizer.metadata.mapper.FileMetadataMapper;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImagesService {

    private static final int PAGE_SIZE = 100;

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private FileMetadataMapper fileMetadataMapper;

    public List<FileMetadataDTO> findGallery(int page) {
        int startIndex = page * PAGE_SIZE;
        List<FileMetadata> result = new ArrayList<>(sessionDataService.getSortedFiles());
        result.subList(startIndex, Math.min(startIndex + PAGE_SIZE, Math.max(result.size() - 1, 0)));
        return result.stream().map(fileMetadataMapper::toDTO).collect(Collectors.toList());
    }

    public byte[] getImage(String pathString, boolean thumbnail) throws IOException {
        if (! thumbnail) {
            return Files.readAllBytes(Paths.get(pathString));
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(new FileInputStream(pathString))
                    .size(300, 300)
                    .keepAspectRatio(true)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        }
    }

    public boolean isDone() {
        return sessionDataService.isDoneLoadingFiles();
    }
}
