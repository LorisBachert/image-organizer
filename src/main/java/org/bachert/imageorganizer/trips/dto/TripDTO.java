package org.bachert.imageorganizer.trips.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;
import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TripDTO {

    private Long id;

    private List<FileMetadataDTO> files = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    private String city;

    private String country;
}
