package engine.map.ldtk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LDtkParser {

    public static final ObjectMapper objectMapper = GenerateObjectMapper();

    private static ObjectMapper GenerateObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static LDtkWorldFile Parse(String src) {
        try {
            LDtkWorldFile worldFile = objectMapper.readValue(src, LDtkWorldFile.class);
            worldFile.ParseLevels();

            return worldFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
