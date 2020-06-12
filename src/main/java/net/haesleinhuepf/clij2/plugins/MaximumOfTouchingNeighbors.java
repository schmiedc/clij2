package net.haesleinhuepf.clij2.plugins;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_maximumOfTouchingNeighbors")
public class MaximumOfTouchingNeighbors extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized {
    @Override
    public String getCategories() {
        return "Measurements";
    }

    @Override
    public String getParameterHelpText() {
        return "Image values, Image touch_matrix, ByRef Image maximum_values_destination";
    }

    @Override
    public boolean executeCL() {
        Object[] args = openCLBufferArgs();
        boolean result = getCLIJ2().maximumOfTouchingNeighbors((ClearCLBuffer) (args[0]), (ClearCLBuffer) (args[1]),  (ClearCLBuffer) (args[2]));
        releaseBuffers(args);
        return result;
    }

    public static boolean maximumOfTouchingNeighbors(CLIJ2 clij2, ClearCLBuffer src_values, ClearCLBuffer touch_matrix, ClearCLBuffer dst_values) {

        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("src_values", src_values);
        parameters.put("src_touch_matrix", touch_matrix);
        parameters.put("dst_values", dst_values);

        // it is possible to use measurent vectors, which have one element less because they don't
        // contain a measurement for the background
        if (touch_matrix.getWidth() == src_values.getWidth() + 1) {
            parameters.put("x_correction", -1);
        } else {
            parameters.put("x_correction", 0);
        }

        long[] globalSizes = new long[]{src_values.getWidth()};

        clij2.activateSizeIndependentKernelCompilation();
        clij2.execute(MaximumOfTouchingNeighbors.class, "maximum_of_touching_neighbors_x.cl", "maximum_value_of_touching_neighbors", globalSizes, globalSizes, parameters);

        return true;
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        return clij.create(new long[]{input.getWidth(), 1, 1}, NativeTypeEnum.Float);
    }

    @Override
    public String getDescription() {
        return "Takes a touch matrix and a vector of values to determine the maximum value among touching neighbors for every object. \n\n" +
                "";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D";
    }
}
