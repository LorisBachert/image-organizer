package org.bachert.imageorganizer.rest;

import org.apache.tomcat.jni.File;
import org.bachert.imageorganizer.loader.FileCrawler;
import org.bachert.imageorganizer.mapper.DuplicatesMapper;
import org.bachert.imageorganizer.model.Duplicates;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.rest.dto.CrawlFileResultDTO;
import org.bachert.imageorganizer.rest.dto.DuplicatesDTO;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.function.Consumer;

@Service
public class ImageService {

    @Autowired
    private SessionDataService sessionDataService;

    @Autowired
    private DuplicatesMapper duplicatesMapper;

    public CrawlFileResultDTO crawlFiles(String path) {
        List<FileMetadata> files = FileCrawler.getFiles(path);
        List<FileMetadata> resultingFiles = sessionDataService.init(files);
        return new CrawlFileResultDTO(resultingFiles.size());
    }

    public void findDuplicates(DeferredResult<List<DuplicatesDTO>> response) {
        sessionDataService.waitForState(SessionDataService.State.SCANNED_DUPLICATES, () -> {
            response.setResult(duplicatesMapper.toDTOs(sessionDataService.getDuplicates()));
        });
    }
}
