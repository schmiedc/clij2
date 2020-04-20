package net.haesleinhuepf.clij2.plugins;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import org.scijava.plugin.Plugin;

/**
 * Author: @haesleinhuepf
 *         April 2020
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_getAsString")
public class GetAsString extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        String result = getAsString(getCLIJ2(), (ClearCLBuffer) args[0]);
        ((String[])args[0])[0] = result;
        return true;

    }

    public static String getAsString(CLIJ2 clij2, ClearCLImageInterface input) {
        ImagePlus imp = clij2.pull(input);

        StringBuilder builder = new StringBuilder();

        for (int z = 0; z < imp.getNSlices(); z++) {
            imp.setZ(z + 1);
            if (imp.getNSlices() > 1) {
                builder.append("z = " + z + ":\n");
            }
            ImageProcessor ip = imp.getProcessor();
            for (int y = 0; y < ip.getHeight(); y++) {
                for (int x = 0; x < ip.getWidth(); x++) {
                    builder.append(ip.getf(x, y) + " ");
                }
                builder.append("\n");
            }
        }


        return builder.toString();
    }

    @Override
    public String getParameterHelpText() {
        return "Image input, ByRef String output";
    }

    @Override
    public String getDescription() {
        return "Writes an image into a string.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}