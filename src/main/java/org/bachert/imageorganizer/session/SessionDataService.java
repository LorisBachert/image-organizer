package org.bachert.imageorganizer.session;

import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.metadata.sort.FileMetadataComparator;
import org.bachert.imageorganizer.trips.model.Trip;
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

    @Getter
    @Setter
    private boolean doneDetectingTrips = false;

    private Map<Long, FileMetadata> files = new HashMap<>();

    private Map<Long, Duplicate> duplicates = new HashMap<>();

    private Map<Long, Trip> trips = new HashMap<>();

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

    public void addTrip(Trip trip) {
        trip.setId((long) trips.size());
        this.trips.put(trip.getId(), trip);
    }

    public Trip getTrip(Long id) {
        return this.trips.get(id);
    }

    public List<Trip> getTrips() {
        return new ArrayList<>(this.trips.values());
    }

    public void reset() {
        this.files.clear();
        this.duplicates.clear();
        this.trips.clear();
        this.doneLoadingFiles = false;
        this.doneDetectingDuplicates = false;
        this.doneDetectingTrips = false;
    }
}
