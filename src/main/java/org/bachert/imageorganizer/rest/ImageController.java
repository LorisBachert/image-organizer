package org.bachert.imageorganizer.rest;

import org.bachert.imageorganizer.model.Duplicates;
import org.bachert.imageorganizer.rest.dto.CrawlFileResultDTO;
import org.bachert.imageorganizer.rest.dto.DuplicatesDTO;
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

    @GetMapping
    public CrawlFileResultDTO crawlFiles(@RequestParam String path) {
        return imageService.crawlFiles(path);
    }

    @GetMapping("/duplicates")
    public DeferredResult<List<DuplicatesDTO>> findDuplicates() {
        DeferredResult<List<DuplicatesDTO>> response = new DeferredResult<>(10000000L);
        imageService.findDuplicates(response);
        return response;
    }
}
