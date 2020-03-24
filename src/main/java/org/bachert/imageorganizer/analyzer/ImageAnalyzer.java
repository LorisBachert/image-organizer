package org.bachert.imageorganizer.analyzer;

import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.List;
import java.util.function.Consumer;

public interface ImageAnalyzer extends Consumer<List<FileMetadata>> {
}
