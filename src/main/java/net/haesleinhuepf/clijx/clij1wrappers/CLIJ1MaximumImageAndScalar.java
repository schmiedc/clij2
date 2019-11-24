package net.haesleinhuepf.clijx.clij1wrappers;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLImage;
import net.haesleinhuepf.clij.kernels.Kernels;
// this is generated code. See src/test/java/net/haesleinhuepf/clijx/codegenerator for details
public class CLIJ1MaximumImageAndScalar{
   
    /**
     * Computes the maximum of a constant scalar s and each pixel value x in a given image X.
     * 
     * <pre>f(x, s) = max(x, s)</pre>
     */
    public boolean maximumImageAndScalar(CLIJ clij, ClearCLImage source, ClearCLImage destination, double scalar) {
        return Kernels.maximumImageAndScalar(clij, source, destination, new Double (scalar).floatValue());
    }

}