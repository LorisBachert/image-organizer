package org.bachert.imageorganizer.galleries;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.galleries.dto.GalleryDTO;
import org.bachert.imageorganizer.galleries.model.Gallery;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.session.SessionDataService;
import org.bachert.imageorganizer.galleries.mapper.GalleryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class GalleryService {

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private GalleryMapper galleryMapper;

    public boolean isDone() {
        return sessionDataService.isDoneDetectingGalleries();
    }

    public List<GalleryDTO> getGalleries() {
        return sessionDataService.getGalleries().stream().map(galleryMapper::toDTO).collect(toList());
    }

    public void update(Long id, GalleryDTO gallery) {
        Gallery existingGallery = sessionDataService.getGallery(id);
        existingGallery.setName(gallery.getName());
        List<FileMetadata> files = gallery.getFiles().stream().map(fileId -> sessionDataService.get(fileId)).collect(toList());
        existingGallery.updateFiles(files);
        existingGallery.setFavorite(gallery.isFavorite());
    }

    public GalleryDTO create(GalleryDTO galleryDTO) {
        Gallery gallery = galleryMapper.toModel(galleryDTO);
        Gallery savedGallery = this.sessionDataService.addGallery(gallery);
        return galleryMapper.toDTO(savedGallery);
    }
}
