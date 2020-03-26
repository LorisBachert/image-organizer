package org.bachert.imageorganizer.metadata.model;

import com.drew.lang.GeoLocation;
import lombok.*;

import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class FileMetadata {

    private Long id;

    private @NonNull Path path;

    private Date creationDate;

    private GeoLocation geoLocation;

    private String extension;

    private Integer width;

    private Integer height;

    private boolean toDelete;
}
