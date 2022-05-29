package engine.map.ldtk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;

public class LDtkLayer {

    public String __identifier;
    public String __tilesetRelPath;
    public ArrayNode autoLayerTiles;
    public int __cWid;
    public int __cHei;
    public int __gridSize;

    public ArrayList<LDtkTile> ldtkTiles = new ArrayList<>();

    public void ParseTiles() throws IOException {
        for(JsonNode node : autoLayerTiles)
            ldtkTiles.add(LDtkParser.objectMapper.treeToValue(node, LDtkTile.class));
    }

}
