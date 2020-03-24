package org.bachert.imageorganizer.metadata;

import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class MetadataExpanderService {

    @Autowired
    private List<Consumer<FileMetadata>> expanders;

    public FileMetadata expandMetadata(FileMetadata file) {
        expanders.forEach(expander -> expander.accept(file));
        return file;
    }
}
