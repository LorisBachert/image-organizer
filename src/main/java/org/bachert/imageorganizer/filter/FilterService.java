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

    public boolean filter(FileMetadata file) {
        return filters.stream().allMatch(filter -> filter.apply(file));
    }
}
