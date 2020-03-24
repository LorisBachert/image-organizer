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

    @Setter
    private SessionState state;

    private Map<Path, FileMetadata> files = new HashMap<>();

    private Map<String, Duplicate> duplicates = new HashMap<>();

    public void add(FileMetadata fileMetadata) {
        files.put(fileMetadata.getPath(), fileMetadata);
    }

    public FileMetadata get(String path) {
        return Optional.ofNullable(files.get(Paths.get(path)))
                .orElseThrow(() -> new IllegalArgumentException("File does not exist: " + path));
    }

    public void markForDeletion(String path, boolean toDelete) {
        get(path).setToDelete(toDelete);
    }

    public List<FileMetadata> getSortedFiles() {
        ArrayList<FileMetadata> files = new ArrayList<>(this.files.values());
        files.sort(new FileMetadataComparator());
        return files;
    }

    public void addDuplicate(Duplicate duplicate) {
        duplicate.setId(UUID.randomUUID().toString());
        this.duplicates.put(duplicate.getId(), duplicate);
    }

    public Duplicate getDuplicate(String id) {
        return this.duplicates.get(id);
    }

    public List<Duplicate> getDuplicates() {
        return new ArrayList<>(this.duplicates.values());
    }
}
