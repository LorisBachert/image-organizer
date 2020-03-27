package org.bachert.imageorganizer.galleries.mapper;

import org.bachert.imageorganizer.metadata.mapper.FileMetadataMapper;
import org.bachert.imageorganizer.galleries.dto.GalleryDTO;
import org.bachert.imageorganizer.galleries.model.Gallery;
import org.bachert.imageorganizer.util.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FileMetadataMapper.class})
public interface GalleryMapper extends GenericMapper<GalleryDTO, Gallery> {
}
