package org.bachert.imageorganizer.metadata.sort;

import org.bachert.imageorganizer.metadata.model.FileMetadata;

import java.util.Comparator;

public class FileMetadataComparator implements Comparator<FileMetadata> {
    @Override
    public int compare(FileMetadata o1, FileMetadata o2) {
        if (o1.getCreationDate() == null && o2.getCreationDate() == null) {
            return 0;
        } else if (o1.getCreationDate() == null) {
            return 1;
        } else if (o2.getCreationDate() == null) {
            return -1;
        } else {
            return o1.getCreationDate().compareTo(o2.getCreationDate());
        }
    }
}
