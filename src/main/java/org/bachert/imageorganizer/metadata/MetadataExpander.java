package org.bachert.imageorganizer.metadata;

import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.function.Consumer;
import java.util.function.Function;

public interface MetadataExpander extends Consumer<FileMetadata> {
}
