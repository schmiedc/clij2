## drawBox
![Image](images/mini_clijx_logo.png)

Draws a box at a given start point with given size. All pixels other than in the box are untouched. Consider using clij.op.set(buffer, 0); in advance.

### Usage in ImageJ macro
```
Ext.CLIJx_drawBox(Image destination, Number x, Number y, Number z, Number width, Number height, Number depth);
```


### Usage in Java
```
// init CLIJ and GPU
import net.haesleinhuepf.clijx.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
CLIJx clijx = CLIJx.getInstance();

// get input parameters
ClearCLBuffer arg1 = clijx.push(arg1ImagePlus);
float arg2 = 1.0;
float arg3 = 2.0;
float arg4 = 3.0;
float arg5 = 4.0;
```

```
// Execute operation on GPU
clijx.drawBox(clij, arg1, arg2, arg3, arg4, arg5);
```

```
//show result

// cleanup memory on GPU
arg1.close();
```


[Back to CLIJ documentation](https://clij.github.io/)

[Imprint](https://clij.github.io/imprint)