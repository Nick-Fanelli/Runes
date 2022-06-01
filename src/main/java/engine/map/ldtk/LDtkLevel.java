package engine.map.ldtk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;

public class LDtkLevel {

    public String identifier;
    public int uid;
    public int pxWid, pxHei;
    public String __bgColor;

    public ArrayNode layerInstances;

    public ArrayList<LDtkLayer> ldtkLayers = new ArrayList<>();
    private final ArrayList<LDtkLayer> entitiesLayers = new ArrayList<>();

    public void ParseLayerInstances() throws IOException {
        for(JsonNode node : layerInstances) {
            LDtkLayer layer = LDtkParser.objectMapper.treeToValue(node, LDtkLayer.class);
            layer.ParseTiles();

            if(layer.GetLayerType() == LDtkLayer.LayerType.ENTITIES)
                entitiesLayers.add(layer);

            ldtkLayers.add(layer);
        }
    }

    public ArrayList<LDtkLayer> GetEntitiesLayers() { return this.entitiesLayers; }

}
