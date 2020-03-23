package org.bachert.imageorganizer.rest;

import com.sun.org.apache.xpath.internal.axes.SubContextList;
import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.duplicates.DuplicatesService;
import org.bachert.imageorganizer.expander.MetadataExpanderService;
import org.bachert.imageorganizer.filter.FilterService;
import org.bachert.imageorganizer.loader.FileCrawler;
import org.bachert.imageorganizer.mapper.DuplicatesMapper;
import org.bachert.imageorganizer.mapper.FileMetadataMapper;
import org.bachert.imageorganizer.model.Duplicate;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.rest.dto.CrawlFileResultDTO;
import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.bachert.imageorganizer.rest.dto.FileMetadataDTO;
import org.bachert.imageorganizer.sort.FileMetadataComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {

    private static final int PAGE_SIZE = 20;

    @Autowired
    private FilterService filterService;

    @Autowired
    private MetadataExpanderService expanderService;

    @Autowired
    private DuplicatesMapper duplicatesMapper;

    @Autowired
    private DuplicatesService duplicatesService;

    @Autowired
    private FileMetadataMapper fileMetadataMapper;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private List<FileMetadata> files = new ArrayList<>();

    private List<Duplicate> duplicates = new ArrayList<>();

    public CrawlFileResultDTO crawlFiles(String path) {
        this.files = FileCrawler.getFiles(path).stream()
                .filter(filterService::filter)
                .collect(Collectors.toList());
        taskExecutor.execute(this::expandMetadata);
        return new CrawlFileResultDTO(this.files.size());
    }

    private void expandMetadata() {
        List<FileMetadata> sortedFiles = this.files.stream()
                .map(this.expanderService::expandMetadata)
                .sorted(new FileMetadataComparator())
                .collect(Collectors.toList());
        this.duplicates = new ArrayList<>();
        this.duplicatesService.findDuplicates(sortedFiles, this.duplicates);
    }

    public DeferredResult<DuplicateDTO> findNextDuplicate(int index) throws InterruptedException {
        DeferredResult<DuplicateDTO> result = new DeferredResult<>();
        while (this.duplicates.isEmpty() && this.duplicates.size() < index && !this.duplicatesService.isDone()) {
            Thread.sleep(1000);
        }
        if (this.duplicates.size() > index) {
            result.setResult(duplicatesMapper.toDTO(this.duplicates.get(index)));
        } else {
            result.setResult(null);
        }
        return result;
    }

    public DeferredResult<DuplicateDTO> resolveDuplicate(Integer index, DuplicateDTO duplicate) throws InterruptedException {
        duplicate.getFiles().forEach(postedFiles -> {
            this.files.stream()
                    .filter(file -> file.getPath().toString().equals(postedFiles.getPath().toString()))
                    .findFirst()
                    .ifPresent(existingFile -> existingFile.setToDelete(postedFiles.isToDelete()));
        });
        this.duplicates.get(index).setResolved(true);
        return findNextDuplicate(index + 1);
    }

    public List<FileMetadataDTO> findGallery(int page) {
        int startIndex = page * PAGE_SIZE;
        List<FileMetadata> result = new ArrayList<>(this.files);
        result.subList(startIndex, startIndex + PAGE_SIZE);
        return result.stream().map(fileMetadataMapper::toDTO).collect(Collectors.toList());
    }
}
