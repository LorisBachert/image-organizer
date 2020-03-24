package org.bachert.imageorganizer.duplicates;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.mapper.DuplicateMapper;
import org.bachert.imageorganizer.model.Duplicate;
import org.bachert.imageorganizer.model.FileMetadata;
import org.bachert.imageorganizer.rest.dto.DuplicateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DuplicateService {

    @Autowired
    private DuplicateMapper duplicateMapper;

    private enum State {
        PENDING, NO_DUPLICATE, DUPLICATE_DETECTED, DONE;
    }
    private State state = State.PENDING;

    private List<Duplicate> duplicates = new ArrayList<>();

    private FluxProcessor<Duplicate, Duplicate> processor;

    private FluxSink<Duplicate> sink;

    public Flux<DuplicateDTO> streamDuplicates() {
        processor = DirectProcessor.<Duplicate>create().serialize();
        sink = processor.sink();
        return processor.map(duplicateMapper::toDTO);
    }

    public List<DuplicateDTO> getDuplicates() {
        return this.duplicates.stream().map(duplicateMapper::toDTO).collect(Collectors.toList());
    }

    public void findDuplicates(List<FileMetadata> files) {
        this.duplicates = new ArrayList<>();
        state = State.NO_DUPLICATE;
        Duplicate currentDuplicate = new Duplicate();
        BufferedImage lastImage = null;
        FileMetadata lastFile = null;
        for (FileMetadata file : files) {
            if (!shouldCompareImages(file, lastFile)) {
                lastFile = file;
                if (state.equals(State.DUPLICATE_DETECTED)) {
                    this.addDuplicate(currentDuplicate);
                    currentDuplicate = new Duplicate();
                }
                continue;
            }

            try {
                BufferedImage currentImage = ImageIO.read(file.getPath().toFile());
                log.info("Comparing similarity between {} and {}", file.getPath().toString(), lastFile.getPath().toString());
                if (lastImage == null) {
                    lastImage = ImageIO.read(lastFile.getPath().toFile());
                }
                if (similar(currentImage, lastImage)) {
                    state = State.DUPLICATE_DETECTED;
                    currentDuplicate.add(file);
                    currentDuplicate.add(lastFile);
                } else if (state.equals(State.DUPLICATE_DETECTED)) {
                    this.addDuplicate(currentDuplicate);
                    currentDuplicate = new Duplicate();
                }
                lastImage = currentImage;
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            lastFile = file;
        }
        state = State.DONE;
    }

    private void addDuplicate(Duplicate duplicate) {
        this.duplicates.add(duplicate);
        if (this.sink != null && ! this.sink.isCancelled()) {
            this.sink.next(duplicate);
        }
        this.state = State.NO_DUPLICATE;
    }

    public boolean isDone() {
        return this.state.equals(State.DONE);
    }

    private boolean shouldCompareImages(FileMetadata current, FileMetadata last) {
        if (last == null) {
            return false;
        }
        boolean sameDimensions = current.getWidth() == last.getWidth() && current.getHeight() == last.getHeight();
        boolean createdWithin5Seconds = last.getCreationDate().getTime() - current.getCreationDate().getTime() < 5 * 1000;
        return sameDimensions && createdWithin5Seconds;
    }

    private static boolean similar(BufferedImage file1, BufferedImage file2) {
        try {
            Boolean result = getDifferencePercent(file1, file2).map(differencePercentage -> differencePercentage < 10).orElse(false);
            if (result) {
                log.debug("Files are similar");
            } else {
                log.debug("Files are NOT similar");
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private static Optional<Double> getDifferencePercent(BufferedImage img1, BufferedImage img2) throws IOException {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            return Optional.empty();
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return Optional.of(100.0 * diff / maxDiff);
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }
}
