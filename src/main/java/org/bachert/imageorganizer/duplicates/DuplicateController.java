package org.bachert.imageorganizer.duplicates;

import org.bachert.imageorganizer.duplicates.dto.DuplicateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/duplicates")
public class DuplicateController {

    @Autowired
    private DuplicateService duplicateService;

    @GetMapping
    public List<DuplicateDTO> findDuplicates() {
        return duplicateService.getDuplicates();
    }

    @PostMapping("/{id}/resolve")
    public void resolveDuplicate(@PathVariable Long id, @RequestBody DuplicateDTO duplicateDTO) {
        duplicateService.resolveDuplicate(id, duplicateDTO);
    }

    @GetMapping(value = "/done", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String isDone() {
        return String.valueOf(duplicateService.isDone());
    }
}
