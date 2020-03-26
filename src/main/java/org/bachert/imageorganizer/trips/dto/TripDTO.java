package org.bachert.imageorganizer.trips.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TripDTO {

    private Long id;

    private String name;

    private List<Long> files = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    private String city;

    private String country;
}
