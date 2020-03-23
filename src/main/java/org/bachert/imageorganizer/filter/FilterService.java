package org.bachert.imageorganizer.filter;

import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    private List<Function<FileMetadata, Boolean>> filters;

    public List<FileMetadata> filter(List<FileMetadata> files) {
        return files.stream()
                .filter(file -> filters.stream().allMatch(filter -> filter.apply(file)))
                .collect(Collectors.toList());
    }
}
