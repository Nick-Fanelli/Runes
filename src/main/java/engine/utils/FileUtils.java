package engine.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtils {

    public static String ReadAssetFile(String filepath) {

        File file = new File(filepath);

        try {
            return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
