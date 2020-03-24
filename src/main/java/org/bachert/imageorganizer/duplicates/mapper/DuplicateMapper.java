package org.bachert.imageorganizer.duplicates.mapper;

import org.bachert.imageorganizer.duplicates.model.Duplicate;
import org.bachert.imageorganizer.duplicates.dto.DuplicateDTO;
import org.bachert.imageorganizer.metadata.mapper.FileMetadataMapper;
import org.bachert.imageorganizer.util.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { FileMetadataMapper.class })
public interface DuplicateMapper extends GenericMapper<DuplicateDTO, Duplicate> {
}
