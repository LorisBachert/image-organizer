package org.bachert.imageorganizer.session;

import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.metadata.sort.FileMetadataComparator;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class SessionDataService {

    @Getter
    @Setter
    private boolean doneDetectingDuplicates = false;

    @Getter
    @Setter
    private boolean doneLoadingFiles = false;

    private Map<Long, FileMetadata> files = new HashMap<>();

    private Map<Long, Duplicate> duplicates = new HashMap<>();

    public void add(FileMetadata fileMetadata) {
        fileMetadata.setId((long) files.size());
        files.put(fileMetadata.getId(), fileMetadata);
    }

    public FileMetadata get(Long id) {
        return Optional.ofNullable(files.get(id))
                .orElseThrow(() -> new IllegalArgumentException("File does not exist: " + id));
    }

    public void markForDeletion(Long id, boolean toDelete) {
        get(id).setToDelete(toDelete);
    }

    public List<FileMetadata> getSortedFiles() {
        ArrayList<FileMetadata> files = new ArrayList<>(this.files.values());
        files.sort(new FileMetadataComparator());
        return files;
    }

    public void addDuplicate(Duplicate duplicate) {
        duplicate.setId((long) duplicates.size());
        this.duplicates.put(duplicate.getId(), duplicate);
    }

    public Duplicate getDuplicate(Long id) {
        return this.duplicates.get(id);
    }

    public List<Duplicate> getDuplicates() {
        return new ArrayList<>(this.duplicates.values());
    }

    public void reset() {
        this.files.clear();
        this.duplicates.clear();
        this.doneLoadingFiles = false;
        this.doneDetectingDuplicates = false;
    }
}
