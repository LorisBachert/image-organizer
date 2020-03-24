package org.bachert.imageorganizer.duplicates;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.analyzer.ImageAnalyzer;
import org.bachert.imageorganizer.duplicates.dto.DuplicateDTO;
import org.bachert.imageorganizer.duplicates.mapper.DuplicateMapper;
import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class DuplicateService {

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private DuplicateMapper duplicateMapper;

    public Flux<DuplicateDTO> streamDuplicates() {
        FluxProcessor<Duplicate, Duplicate> processor = DirectProcessor.<Duplicate>create().serialize();
        FluxSink<Duplicate> sink = processor.sink();
        return processor.map(duplicateMapper::toDTO);
    }

    public List<DuplicateDTO> getDuplicates() {
        return sessionDataService.getDuplicates().stream().map(duplicateMapper::toDTO).collect(toList());
    }

    public void resolveDuplicate(String id, DuplicateDTO duplicateDTO) {
        Duplicate duplicate = sessionDataService.getDuplicate(id);
        duplicate.getFiles().forEach(file -> {
            duplicateDTO.getFiles()
                    .stream().filter(sentFile -> sentFile.getPath().equals(file.getPath()))
                    .findFirst()
                    .ifPresent(sentFile -> file.setToDelete(sentFile.isToDelete()));
        });
        duplicate.setResolved(true);
    }
}
