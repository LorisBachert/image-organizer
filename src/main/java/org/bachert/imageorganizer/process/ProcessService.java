package org.bachert.imageorganizer.process;

import org.bachert.imageorganizer.analyzer.ImageAnalyzerService;
import org.bachert.imageorganizer.filter.FilterService;
import org.bachert.imageorganizer.io.IOService;
import org.bachert.imageorganizer.metadata.MetadataExpanderService;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.process.dto.ProcessConfigurationDTO;
import org.bachert.imageorganizer.process.dto.ProcessStateDTO;
import org.bachert.imageorganizer.process.dto.StartProcessResultDTO;
import org.bachert.imageorganizer.session.SessionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private FilterService filterService;

    @Autowired
    private MetadataExpanderService metadataExpanderService;

    @Autowired
    private ImageAnalyzerService imageAnalyzerService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private SessionDataService sessionDataService;

    private ProcessConfigurationDTO configuration;

    public StartProcessResultDTO process(ProcessConfigurationDTO configuration) {
        this.sessionDataService.reset();
        this.configuration = configuration;
        List<Path> filePaths = IOService.getFiles(configuration.getDirectory());
        taskExecutor.execute(() ->
                Flux.fromIterable(filePaths)
                        .map(FileMetadata::new)
                        .filter(filterService::filter)
                        .map(metadataExpanderService::expandMetadata)
                        .doOnNext(sessionDataService::add)
                        .collect(Collectors.toList())
                        .doOnNext((files) -> sessionDataService.setDoneLoadingFiles(true))
                        .subscribe(files -> imageAnalyzerService.analyze(files, configuration))
        );
        return new StartProcessResultDTO(filePaths.size());
    }

    public ProcessStateDTO getState() {
        Optional<ProcessConfigurationDTO> configuration = Optional.ofNullable(this.configuration);
        return new ProcessStateDTO(configuration.isPresent(), configuration.map(ProcessConfigurationDTO::getDirectory).orElse(null));
    }

    public void end() {
        this.sessionDataService.getTrips().forEach(trip -> {
            List<FileMetadata> files = trip.getFiles().stream()
                    .map(sessionDataService::get)
                    .filter(file -> !file.isToDelete())
                    .collect(Collectors.toList());
            IOService.moveFiles(configuration.getDirectory(), trip, files);
        });
    }
}
