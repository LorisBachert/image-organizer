package org.bachert.imageorganizer.metadata.mapper;

import org.bachert.imageorganizer.metadata.dto.FileMetadataDTO;
import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.util.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper extends GenericMapper<FileMetadataDTO, FileMetadata> {
}
