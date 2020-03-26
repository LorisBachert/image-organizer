package org.bachert.imageorganizer.metadata.dto;

import lombok.*;

import java.nio.file.Path;
import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class FileMetadataDTO {

    private Long id;

    private @NonNull
    Path path;

    private Date creationDate;

    private String extension;

    private int width;

    private int height;

    private boolean toDelete;
}
