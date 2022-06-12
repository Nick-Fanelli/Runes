package engine.utils;

import java.io.*;

public final class FileUtils {

    public static String ReadAssetFileAsString(String filepath) {

        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);

            assert stream != null;

            return new String(stream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] ReadAssetFileAsBytes(String filepath) {

        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);

            assert stream != null;

            return stream.readAllBytes();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
