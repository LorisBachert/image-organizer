package org.bachert.imageorganizer.rest;

import org.bachert.imageorganizer.rest.dto.CrawlFileResultDTO;
import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.bachert.imageorganizer.rest.dto.FileMetadataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public CrawlFileResultDTO crawlFiles(@RequestParam String path) {
        return imageService.crawlFiles(path);
    }

    @GetMapping(value = "/gallery")
    public List<FileMetadataDTO> findGallery(@RequestParam int page) throws InterruptedException {
        return imageService.findGallery(page);
    }
}
