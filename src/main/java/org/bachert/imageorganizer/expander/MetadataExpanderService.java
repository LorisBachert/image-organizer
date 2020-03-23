package org.bachert.imageorganizer.expander;

import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class MetadataExpanderService {

    @Autowired
    private List<Consumer<FileMetadata>> expanders;

    public void expandMetadata(List<FileMetadata> files) {
        files.forEach(file -> {
            expanders.forEach(expander -> expander.accept(file));
        });
    }
}
