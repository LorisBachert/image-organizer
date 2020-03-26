package org.bachert.imageorganizer.duplicates;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.duplicates.dto.DuplicateDTO;
import org.bachert.imageorganizer.duplicates.mapper.DuplicateMapper;
import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void resolveDuplicate(Long id) {
        Duplicate duplicate = sessionDataService.getDuplicate(id);
        duplicate.setResolved(true);
    }

    public boolean isDone() {
        return sessionDataService.isDoneDetectingDuplicates();
    }
}
