package engine.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JSONLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode Parse(String src) {
        try {
            return objectMapper.readTree(src);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
