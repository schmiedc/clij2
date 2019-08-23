package net.haesleinhuepf.clij.advancedmath;

import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.test.TestUtilities;
import net.haesleinhuepf.clij2.CLIJ2;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GreaterTest {
    @Test
    public void test() {
        Img<UnsignedShortType> a = ArrayImgs.unsignedShorts(new short[]{
                1, 2, 3,
                4, 5, 6
        }, new long[]{3, 2});
        Img<UnsignedShortType> b = ArrayImgs.unsignedShorts(new short[]{
                1, 3, 3,
                4, 4, 6
        }, new long[]{3, 2});
        Img<UnsignedShortType> c = ArrayImgs.unsignedShorts(new short[]{
                0, 0, 0,
                0, 1, 0
        }, new long[]{3, 2});

        CLIJ2 clij2 = CLIJ2.getInstance();

        ClearCLBuffer clA = clij2.push(a);
        ClearCLBuffer clB = clij2.push(b);
        ClearCLBuffer clTest = clij2.create(clA);
        ClearCLBuffer clC = clij2.push(c);

        clij2.op.greater(clA, clB, clTest);
        TestUtilities.printBuffer(CLIJ.getInstance(), clTest);
        assertTrue(clij2.op.matrixEqual(clTest, clC, 0f));

        clA.close();
        clB.close();
        clC.close();
        clTest.close();
    }
}