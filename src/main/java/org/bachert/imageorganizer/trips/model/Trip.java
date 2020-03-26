package org.bachert.imageorganizer.trips.model;

import com.drew.lang.GeoLocation;
import lombok.Getter;
import lombok.Setter;
import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Trip {

    private Long id;

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

    public boolean isMulitpleDays() {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(this.startDate);
        int startDay = startDate.get(Calendar.DATE);
        int startMonth = startDate.get(Calendar.MONTH);
        int startYear = startDate.get(Calendar.YEAR);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(this.endDate);
        int endDay = endDate.get(Calendar.DATE);
        int endMonth = endDate.get(Calendar.MONTH);
        int endYear = endDate.get(Calendar.YEAR);
        return !(startDay == endDay && startMonth == endMonth && startYear == endYear);
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
