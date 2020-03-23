package org.bachert.imageorganizer.duplicates;

import lombok.extern.slf4j.Slf4j;
import org.bachert.imageorganizer.model.Duplicates;
import org.bachert.imageorganizer.model.FileMetadata;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DuplicatesService {

    private enum State {
        NO_DUPLICATE, DUPLICATE_DETECTED
    }

    public List<Duplicates> findDuplicates(List<FileMetadata> files) {
        List<Duplicates> result = new ArrayList<>();
        State state = State.NO_DUPLICATE;
        Duplicates currentDuplicates = new Duplicates();
        BufferedImage lastImage = null;
        FileMetadata lastFile = null;
        for (FileMetadata file : files) {
            if (! shouldCompareImages(file, lastFile)) {
                lastFile = file;
                if (state.equals(State.DUPLICATE_DETECTED)) {
                    state = State.NO_DUPLICATE;
                    result.add(currentDuplicates);
                    currentDuplicates = new Duplicates();
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
                    currentDuplicates.add(file);
                    currentDuplicates.add(lastFile);
                } else if (state.equals(State.DUPLICATE_DETECTED)) {
                    state = State.NO_DUPLICATE;
                    result.add(currentDuplicates);
                    currentDuplicates = new Duplicates();
                }
                lastImage = currentImage;
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            lastFile = file;
        }
        return result;
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
