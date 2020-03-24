package org.bachert.imageorganizer.analyzer;

import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class ImageAnalyzerService {

    @Autowired
    private List<ImageAnalyzer> analyzers;

    public void analyze(List<FileMetadata> files) {
        analyzers.forEach(expander -> expander.accept(files));
    }
}
