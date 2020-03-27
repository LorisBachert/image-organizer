package org.bachert.imageorganizer.session;

import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.galleries.model.Gallery;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.metadata.sort.FileMetadataComparator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionDataService {

    @Getter
    @Setter
    private boolean doneDetectingDuplicates = false;

    @Getter
    @Setter
    private boolean doneLoadingFiles = false;

    @Getter
    @Setter
    private boolean doneDetectingGalleries = false;

    private Map<Long, FileMetadata> files = new HashMap<>();

    private Map<Long, Duplicate> duplicates = new HashMap<>();

    private Map<Long, Gallery> galleries = new HashMap<>();

    public void add(FileMetadata fileMetadata) {
        fileMetadata.setId((long) files.size());
        files.put(fileMetadata.getId(), fileMetadata);
    }

    public FileMetadata get(Long id) {
        return Optional.ofNullable(files.get(id))
                .orElseThrow(() -> new IllegalArgumentException("File does not exist: " + id));
    }

    public List<FileMetadata> getSortedFiles() {
        List<FileMetadata> files = getFiles();
        files.sort(new FileMetadataComparator());
        return files;
    }

    public List<FileMetadata> getFiles() {
        return new ArrayList<>(this.files.values());
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

    public void addGallery(Gallery gallery) {
        gallery.setId((long) galleries.size());
        this.galleries.put(gallery.getId(), gallery);
    }

    public Gallery getGallery(Long id) {
        return this.galleries.get(id);
    }

    public List<Gallery> getGalleries() {
        return new ArrayList<>(this.galleries.values());
    }

    public void reset() {
        this.files.clear();
        this.duplicates.clear();
        this.galleries.clear();
        this.doneLoadingFiles = false;
        this.doneDetectingDuplicates = false;
        this.doneDetectingGalleries = false;
    }
}
