package org.bachert.imageorganizer.rest.dto;

import com.drew.lang.GeoLocation;
import lombok.*;

import java.nio.file.Path;
import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class FileMetadataDTO {

    private @NonNull Path path;

    private Date creationDate;

    private GeoLocation geoLocation;

    private String extension;

    private int width;

    private int height;

    private String base64;
}
