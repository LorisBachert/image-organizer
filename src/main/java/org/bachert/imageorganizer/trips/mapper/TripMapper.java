package org.bachert.imageorganizer.trips.mapper;

import org.bachert.imageorganizer.metadata.mapper.FileMetadataMapper;
import org.bachert.imageorganizer.trips.dto.TripDTO;
import org.bachert.imageorganizer.trips.model.Trip;
import org.bachert.imageorganizer.util.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FileMetadataMapper.class})
public interface TripMapper extends GenericMapper<TripDTO, Trip> {
}
