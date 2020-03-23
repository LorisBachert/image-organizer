package org.bachert.imageorganizer.mapper;

import com.drew.lang.GeoLocation;
import org.apache.commons.io.FileUtils;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.rest.dto.FileMetadataDTO;
import org.mapstruct.*;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper extends GenericMapper<FileMetadataDTO, FileMetadata> {

    @AfterMapping
    default void setBase64(FileMetadata model, @MappingTarget FileMetadataDTO dto) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(model.getPath().toFile());
            dto.setBase64(Base64.getEncoder().encodeToString(fileContent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
