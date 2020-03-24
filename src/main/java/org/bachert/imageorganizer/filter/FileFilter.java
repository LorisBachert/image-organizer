package org.bachert.imageorganizer.filter;

import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.function.Function;

public interface FileFilter extends Function<FileMetadata, Boolean> {
}
