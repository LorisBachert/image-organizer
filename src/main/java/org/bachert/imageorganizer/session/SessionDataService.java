package org.bachert.imageorganizer.session;

import org.bachert.imageorganizer.duplicates.DuplicatesService;
import org.bachert.imageorganizer.expander.MetadataExpanderService;
import org.bachert.imageorganizer.filter.FilterService;
import org.bachert.imageorganizer.model.Duplicates;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.sort.FileMetadataComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class SessionDataService {

    public enum State {
        NONE, INIT, METADATA_EXPANDED, SORTED, SCANNED_DUPLICATES, GROUPED
    }

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private FilterService filterService;

    @Autowired
    private MetadataExpanderService expanderService;

    @Autowired
    private DuplicatesService duplicatesService;

    private State state = State.NONE;

    private List<FileMetadata> files = new ArrayList<>();

    private List<Duplicates> duplicates = new ArrayList<>();

    private Map<State, List<Runnable>> stateChangeListener = new HashMap<>();

    public List<FileMetadata> init(List<FileMetadata> files) {
        this.setState(State.INIT);
        this.files = filterService.filter(files);
        expandMetadataAndSort();
        return this.files;
    }

    private void expandMetadataAndSort() {
        taskExecutor.execute(() -> {
            expanderService.expandMetadata(this.files);
            this.setState(State.METADATA_EXPANDED);
            this.files.sort(new FileMetadataComparator());
            this.setState(State.SORTED);
            this.duplicates = duplicatesService.findDuplicates(this.files);
            this.setState(State.SCANNED_DUPLICATES);
        });
    }

    private void setState(State newState) {
        this.state = newState;
        if (this.stateChangeListener.containsKey(newState)) {
            this.stateChangeListener.get(newState).forEach(Runnable::run);
        }
    }

    public void waitForState(State state, Runnable runnable) {
        // already in a further state
        if (state.ordinal() <= this.state.ordinal()) {
            runnable.run();
        } else {
            this.stateChangeListener.computeIfAbsent(state, s -> new ArrayList<>()).add(runnable);
        }
    }

    public List<Duplicates> getDuplicates() {
        return duplicates;
    }
}
