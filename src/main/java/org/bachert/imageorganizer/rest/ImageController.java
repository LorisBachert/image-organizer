package org.bachert.imageorganizer.rest;

import org.bachert.imageorganizer.rest.dto.CrawlFileResultDTO;
import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

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

    @GetMapping(value = "/duplicates/{index}")
    public DeferredResult<DuplicateDTO> findNextDuplicate(@PathVariable Integer index) throws InterruptedException {
        return imageService.findNextDuplicate(index);
    }

    @PostMapping(value = "/duplicates/{index}/resolve")
    public DeferredResult<DuplicateDTO> resolveDuplicate(@PathVariable Integer index, @RequestBody DuplicateDTO duplicate) throws InterruptedException {
        return imageService.resolveDuplicate(index, duplicate);
    }
}
