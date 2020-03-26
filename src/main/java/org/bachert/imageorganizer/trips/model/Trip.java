package org.bachert.imageorganizer.trips.model;

import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.*;

@Getter
@Setter
public class Trip {

    private Long id;

    private String name;

    private List<Long> files = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    private String city;

    private String country;

    public void addFile(FileMetadata file) {
        files.add(file.getId());
        if (file.getCreationDate() != null) {
            if (startDate == null || startDate.after(file.getCreationDate())) {
                startDate = file.getCreationDate();
            }
            if (endDate == null || endDate.before(file.getCreationDate())) {
                endDate = file.getCreationDate();
            }
        }
    }

    public void updateFiles(List<FileMetadata> files) {
        this.files.clear();
        files.forEach(this::addFile);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "images=" + files.size() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
