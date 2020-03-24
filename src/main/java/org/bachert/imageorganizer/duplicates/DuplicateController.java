package org.bachert.imageorganizer.duplicates;

import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/duplicates")
public class DuplicateController {

    @Autowired
    private DuplicateService duplicateService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DuplicateDTO> streamDuplicates() {
        return duplicateService.streamDuplicates();
    }

    @GetMapping
    public List<DuplicateDTO> findDuplicates() {
        return duplicateService.getDuplicates();
    }
}
