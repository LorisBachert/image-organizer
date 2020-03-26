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

    public List<DuplicateDTO> getDuplicates() {
        return sessionDataService.getDuplicates().stream().map(duplicateMapper::toDTO).collect(toList());
    }

    public void resolveDuplicate(Long id, DuplicateDTO duplicateDTO) {
        Duplicate duplicate = sessionDataService.getDuplicate(id);
        duplicateDTO.getFiles().forEach(file -> sessionDataService.markForDeletion(file.getId(), file.isToDelete()));
        duplicate.setResolved(true);
    }

    public boolean isDone() {
        return sessionDataService.isDoneDetectingDuplicates();
    }
}
