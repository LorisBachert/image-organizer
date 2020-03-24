package org.bachert.imageorganizer.mapper;

import org.bachert.imageorganizer.model.Duplicate;
import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { FileMetadataMapper.class })
public interface DuplicateMapper extends GenericMapper<DuplicateDTO, Duplicate> {
}
