package org.bachert.imageorganizer.session;

import lombok.Getter;
import lombok.Setter;

public enum SessionState {
    NOT_STARTED,
    CRAWLING_FILES,
    EXPANDING_METADATA,
    SCANNED_DUPLICATES
}
