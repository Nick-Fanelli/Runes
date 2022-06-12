package engine.utils;

import java.io.*;

public final class IOUtils {

    public static InputStream ReadAssetFileAsInputStream(String filepath) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
    }

    public static String ReadAssetFileAsString(String filepath) {
        try {
            InputStream stream = ReadAssetFileAsInputStream(filepath);
            assert stream != null;

            byte[] bytes = stream.readAllBytes();

            stream.close();

            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] ReadAssetFileAsBytes(String filepath) {
        try {
            InputStream stream = ReadAssetFileAsInputStream(filepath);
            assert stream != null;

            byte[] bytes = stream.readAllBytes();

            stream.close();

            return bytes;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
