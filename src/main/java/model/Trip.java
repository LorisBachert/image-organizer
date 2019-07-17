package model;

import com.drew.lang.GeoLocation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Trip {

    private List<FileMetadata> files = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    private String city;

    private String country;

    public void addFile(FileMetadata file) {
        files.add(file);
        if (file.getCreationDate() != null) {
            if (startDate == null || startDate.after(file.getCreationDate())) {
                startDate = file.getCreationDate();
            }
            if (endDate == null || endDate.before(file.getCreationDate())) {
                endDate = file.getCreationDate();
            }
        }
    }

    public GeoLocation getGeoLocation() {
        if (files.isEmpty()) {
            return null;
        } else {
            return files.get(0).getGeoLocation();
        }
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
