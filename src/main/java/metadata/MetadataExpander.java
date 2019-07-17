package metadata;

import model.FileMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MetadataExpander {

    public static final List<Consumer<FileMetadata>> EXPANDERS = Arrays.asList(new ExifParser());

    public static Consumer<FileMetadata> expandMetadata = fileMetadata -> {
        MetadataExpander.EXPANDERS
                .forEach(expander -> expander.accept(fileMetadata));
    };
}
