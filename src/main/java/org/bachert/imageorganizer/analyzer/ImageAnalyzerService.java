package org.bachert.imageorganizer.analyzer;

import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ImageAnalyzerService {

    @Autowired
    private List<ImageAnalyzer> analyzers;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public void analyze(List<FileMetadata> files) {
        analyzers.forEach(expander -> {
            taskExecutor.execute(() -> expander.accept(new ArrayList<>(files)));
        });
    }
}
