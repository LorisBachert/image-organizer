package org.bachert.imageorganizer.mapper;

import org.apache.commons.io.FileUtils;
import org.bachert.imageorganizer.model.Duplicates;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.rest.dto.DuplicatesDTO;
import org.bachert.imageorganizer.rest.dto.FileMetadataDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring", uses = { FileMetadataMapper.class })
public interface DuplicatesMapper extends GenericMapper<DuplicatesDTO, Duplicates> {
}
