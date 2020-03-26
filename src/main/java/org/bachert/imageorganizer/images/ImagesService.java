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

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private FileMetadataMapper fileMetadataMapper;

    public List<FileMetadataDTO> findGallery() {
        return sessionDataService.getSortedFiles().stream().map(fileMetadataMapper::toDTO).collect(Collectors.toList());
    }

    public byte[] getImage(Long id, boolean thumbnail) throws IOException {
        Path path = sessionDataService.get(id).getPath();
        if (! thumbnail) {
            return Files.readAllBytes(path);
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(new FileInputStream(path.toFile()))
                    .size(700, 700)
                    .keepAspectRatio(true)
                    .toOutputStream(outputStream);
            return outputStream.toByteArray();
        }
    }

    public boolean isDone() {
        return sessionDataService.isDoneLoadingFiles();
    }

    public void setToDelete(Long id, boolean toDelete) {
        sessionDataService.get(id).setToDelete(toDelete);
    }
}
