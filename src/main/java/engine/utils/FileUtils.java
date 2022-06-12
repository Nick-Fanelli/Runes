package engine.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtils {

    public static String ReadAssetFile(String filepath) {

        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);

        assert stream != null;

        try {
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
