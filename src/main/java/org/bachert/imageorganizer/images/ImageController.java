package org.bachert.imageorganizer.images;

import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/gallery")
    public List<FileMetadataDTO> findGallery(@RequestParam int page) throws InterruptedException {
        return imagesService.findGallery(page);
    }

    @GetMapping
    public @ResponseBody byte[] getImage(@RequestParam String path, @RequestParam(required = false, defaultValue = "true") boolean thumbnail) throws IOException {
        return imagesService.getImage(path, thumbnail);
    }
}
