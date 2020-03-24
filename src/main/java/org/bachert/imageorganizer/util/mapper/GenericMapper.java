package org.bachert.imageorganizer.util.mapper;

import java.util.List;
import java.util.Set;

public interface GenericMapper<DTO, Model> {

    DTO toDTO(Model model);

    Model toModel(DTO dto);

    List<DTO> toDTOs(List<Model> models);

    List<Model> toModels(List<DTO> models);

    Set<DTO> toDTOs(Set<Model> models);

    Set<Model> toModels(Set<DTO> models);
}
