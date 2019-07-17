package model;

import com.drew.lang.GeoLocation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.util.Date;

@RequiredArgsConstructor
@Setter
@Getter
public class FileMetadata {

    private final @NonNull Path path;

    private Date creationDate;

    private GeoLocation geoLocation;
}
