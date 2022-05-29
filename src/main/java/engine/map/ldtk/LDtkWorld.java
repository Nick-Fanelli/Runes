package engine.map.ldtk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.HashMap;

public class LDtkWorld {

    public ArrayNode levels;

    public HashMap<String, LDtkLevel> ldtkLevels = new HashMap<>();

    public void ParseLevels() throws IOException {
        for(JsonNode node : levels) {
            LDtkLevel level = LDtkParser.objectMapper.treeToValue(node, LDtkLevel.class);
            level.ParseLayerInstances();
            ldtkLevels.put(level.identifier, level);
        }
    }

}
