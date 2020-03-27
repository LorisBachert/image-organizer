package org.bachert.imageorganizer.analyzer;

import org.bachert.imageorganizer.metadata.model.FileMetadata;
import org.bachert.imageorganizer.process.dto.ProcessConfigurationDTO;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ImageAnalyzer extends BiConsumer<List<FileMetadata>, ProcessConfigurationDTO> {
}
