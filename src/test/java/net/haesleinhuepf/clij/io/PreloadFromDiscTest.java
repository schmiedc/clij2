package net.haesleinhuepf.clij.io;

import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreloadFromDiscTest {

    @Test
    public void testWithDifferentlySizedImages() {
        String[] filenames = {
                "src/test/resources/blobs.tif",
                "src/test/resources/miniBlobs.tif",
                "src/test/resources/t1-head.tif"
        };

        CLIJ2 clij = CLIJ2.getInstance();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < filenames.length; i++) {
                ClearCLBuffer buffer = null;
                buffer = clij.op.preloadFromDisc(buffer, filenames[i], filenames[i%filenames.length], "");
                buffer.close();
            }
        }
    }

    @Test
    public void testWithSameSizedImages() {
        String[] filenames = {
                "src/test/resources/blobs.tif",
                "src/test/resources/blobs2.tif",
                "src/test/resources/blobs3.tif"
        };

        CLIJ2 clij = CLIJ2.getInstance();
        for (int j = 0; j < 2; j++) {
            ClearCLBuffer buffer = null;
            for (int i = 0; i < filenames.length; i++) {
                buffer = clij.op.preloadFromDisc(buffer, filenames[i], filenames[i%filenames.length], "");
            }
            buffer.close();
        }
    }
}