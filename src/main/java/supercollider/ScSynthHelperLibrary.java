package supercollider;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Random;

public class ScSynthHelperLibrary {

    static {
        try {

            Native.register("scsynth_jna");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static native ScsynthJnaStartOptions.ByReference scsynth_jna_get_default_start_options();

    public static native int scsynth_jna_get_device_count();

    public static native String scsynth_jna_get_device_name(int i);

    public static native int scsynth_jna_get_device_max_input_channels(int i);

    public static native int scsynth_jna_get_device_max_output_channels(int i);
    

    public static native int scsynth_jna_init();

    public static native void scsynth_jna_cleanup();

    public static native Pointer scsynth_jna_start(ScsynthJnaStartOptions options);

    public static native SndBuf scsynth_jna_copy_sndbuf(Pointer world, int index);

}
