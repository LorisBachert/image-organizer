package org.bachert.imageorganizer.images;

import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping
    public List<FileMetadataDTO> findGallery() throws InterruptedException {
        return imagesService.findGallery();
    }

    @GetMapping("/{id}")
    public @ResponseBody byte[] getImage(@PathVariable Long id, @RequestParam(required = false, defaultValue = "true") boolean thumbnail) throws IOException {
        return imagesService.getImage(id, thumbnail);
    }

    @GetMapping(value = "/done", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String isDone() {
        return String.valueOf(imagesService.isDone());
    }

    @PostMapping("/{id}/delete")
    public void setToDelete(@PathVariable Long id, @RequestParam boolean toDelete) {
        imagesService.setToDelete(id, toDelete);
    }
}
