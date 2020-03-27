package org.bachert.imageorganizer.galleries;

import org.bachert.imageorganizer.galleries.dto.GalleryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/galleries")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public List<GalleryDTO> findGalleries() {
        return galleryService.getGalleries();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody GalleryDTO trip) {
        galleryService.update(id, trip);
    }

    @GetMapping(value = "/done", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String isDone() {
        return String.valueOf(galleryService.isDone());
    }
}
