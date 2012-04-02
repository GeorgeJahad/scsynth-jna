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

public class ScSynthLibrary {

    public static String getUgensPath() {
        return ugensDir;
    }

    public static String getScSynthPath() {
        return scsynthDir;
    }
    private static String ugensDir = "";
    private static String scsynthDir = "";

    private static File copyResourceToFS(String resourcePath, String targetFsLocation) throws IOException {

        URL res = ScSynthLibrary.class.getResource(resourcePath);

        InputStream is = res.openStream();
        File file = new File(targetFsLocation);
        FileOutputStream fos = new FileOutputStream(file);

        /* Copy the DLL fro the JAR to the filesystem */
        byte[] array = new byte[1024];
        for (int i = is.read(array);
                i != -1;
                i = is.read(array)) {
            fos.write(array, 0, i);
        }
        fos.close();
        is.close();

        return file;
    }

    static {
        try {

            final String baseTempPath = System.getProperty("java.io.tmpdir");

            Random rand = new Random();
	    //            int randomInt = 100000 + rand.nextInt(899999);
	    int randomInt = 100000;

	    System.out.println("gbj java3\n");
            File tempDir = new File(baseTempPath + File.separator + "scsynth_jna" + randomInt);
            if (tempDir.exists() == false) {
                tempDir.mkdir();
            }
	    //            tempDir.deleteOnExit();

            File tempUgensDir = new File(tempDir.getPath() + File.separator + "ugens");
            if (tempUgensDir.exists() == false) {
                tempUgensDir.mkdir();
            }
            tempUgensDir.deleteOnExit();

            // Copy scsynth to temp dir
            {
                String fn = ScSynthLibrary.getScSynth();
                String source = ScSynthLibrary.getScSynthLocation() + "/" + fn;
                String target = tempDir.getPath() + File.separator + fn;
                File lib = copyResourceToFS(source, target);
                lib.deleteOnExit();
            }

            // Copy scsynth dependencies to temp dir
            for (String fn : ScSynthLibrary.getScSynthDependencies()) {
                String source = ScSynthLibrary.getScSynthLocation() + "/" + fn;
                String target = tempDir.getPath() + File.separator + fn;
                File lib = copyResourceToFS(source, target);
                lib.deleteOnExit();
            }

            // Copy ugens to temp dir
            for (String fn : ScSynthLibrary.getUgens()) {
                String source = ScSynthLibrary.getUgensLocation() + "/" + fn;
                String target = tempUgensDir.getPath() + File.separator + fn;
                File ugen = copyResourceToFS(source, target);
                ugen.deleteOnExit();
            }


            scsynthDir = tempDir.getPath();
            ugensDir = tempUgensDir.getPath();

            System.setProperty("jna.library.path", tempDir.getPath());
            Native.register("scsynth");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static native void World_Cleanup(Pointer world);

    public static native int World_OpenUDP(Pointer world, int b);

    public static native int World_OpenTCP(Pointer world, int b, int c, int d);

    public static native boolean World_SendPacket(Pointer world, int bufferSize, ByteBuffer buffer, ReplyCallback d);

    public static native boolean World_SendPacketWithContext(Pointer world, int b, String c, ReplyCallback d, Pointer e);

    public static native void World_WaitForQuit(Pointer world);

    private static String getUgensLocation() {
        return getScSynthLocation() + "/ugens";
    }

    private static String[] getScSynthDependencies() {
        if (Util.getOsName().equals("windows")) {
            return new String[]{"libsndfile-1.dll"};
        }
        else if (Util.getOsName().equals("linux")) {
            return new String[]{"libscsynth.so"};
        }
        return new String[]{};
    }

    private static String[] getUgens() {

        if (Util.getOsName().equals("linux")) {
            return new String[]{
                        "BinaryOpUGens.so", "KeyboardUGens.so", "MouseUGens.so",
                        "GendynUGens.so", "PanUGens.so",
                        "ChaosUGens.so", "GrainUGens.so", "PhysicalModelingUGens.so",
                        "DelayUGens.so", "IOUGens.so", "PV_ThirdParty.so",
                        "DemandUGens.so", "LFUGens.so", "ReverbUGens.so",
                        "DiskIO_UGens.so", "ML_UGens.so", "TestUGens.so",
                        "DynNoiseUGens.so", "MulAddUGens.so", "TriggerUGens.so",
                        "FFT_UGens.so", "NoiseUGens.so", "UnaryOpUGens.so",
                        "FilterUGens.so", "OscUGens.so", "UnpackFFTUGens.so"};
        } else if (Util.getOsName().equals("windows")) {
            return new String[]{
                        "BinaryOpUGens.scx", "ChaosUGens.scx", "DelayUGens.scx",
                        "DemandUGens.scx", "DiskIOUGens.scx", "DynNoiseUGens.scx",
                        "FilterUGens.scx", "GendynUGens.scx", "GrainUGens.scx",
                        "IOUGens.scx", "LFUGens.scx", "libfftw3-3.dll",
                        "libfftw3f-3.dll", "libfftw3l-3.dll", "libsndfile-1.dll",
                        "MachineListeningUGens.scx", "MouseUGens.scx", "MulAddUGens.scx",
                        "NoiseUGens.scx", "OSCUGens.scx", "PanUGens.scx",
                        "PhysicalModelingUGens.scx", "ReverbUGens.scx", "scsynth.dll",
                        "TestUGens.scx", "TriggerUGens.scx", "UnaryOpUGens.scx",
                        "UnpackFFTUGens.scx"};
        } else if (Util.getOsName().equals("macosx")) {
            return new String[]{
                        "IOUGens.scx", "OscUGens.scx", "UnaryOpUGens.scx",
                        "DiskIO_UGens.scx", "DynNoiseUGens.scx", "GrainUGens.scx",
                        "LFUGens.scx", "NoiseUGens.scx", "FFT_UGens.scx",
                        "BinaryOpUGens.scx", "GendynUGens.scx", "ChaosUGens.scx",
                        "MulAddUGens.scx", "DelayUGens.scx", "PhysicalModelingUGens.scx",
                        "PV_ThirdParty.scx", "DemandUGens.scx", "PanUGens.scx",
                        "KeyboardUGens.scx", "TriggerUGens.scx", "TestUGens.scx",
                        "UnpackFFTUGens.scx", "MouseUGens.scx", "ReverbUGens.scx",
                        "FilterUGens.scx", "ML_UGens.scx"};
        }

        return new String[]{};
    }

    private static String getScSynth() {
        String retval = "";
        if (Util.getOsName().equals("linux")) {
            retval = "libscsynth_jna.so";
        } else if (Util.getOsName().equals("windows")) {
            retval = "scsynth_jna.dll";
        } else if (Util.getOsName().equals("macosx")) {
            retval = "libscsynth_jna.dylib";
        }
        return retval;
    }

    private static String getScSynthLocation() {
        return "/supercollider/scsynth/" + Util.getOsName() + "/" + Util.getOsArch();
    }

}
