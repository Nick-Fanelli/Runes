package engine.map;

import engine.map.ldtk.LDtkEntity;
import engine.map.ldtk.LDtkLayer;
import engine.state.Transform;
import engine.utils.Logger;
import org.joml.Vector2f;

import java.util.ArrayList;

public final class EntityQuery {

    public static Vector2f GetEntityOffset(String identifier, ArrayList<LDtkLayer> entityLayer) {

        float tileSize = 0;

        if(entityLayer == null) {
            Logger.Warn("Entity layers are null!");
            return null;
        }

        LDtkEntity targetEntity = null;

        for(LDtkLayer layer : entityLayer) {
            if(targetEntity != null)
                break;

            if(layer.GetLayerType() != LDtkLayer.LayerType.ENTITIES) {
                Logger.Warn("Layer is no an entity layer!");
                continue;
            }

            for(LDtkEntity entity : layer.ldtkEntities) {
                if(entity.__identifier.equals(identifier)) {
                    targetEntity = entity;
                    tileSize = layer.glTileSize;
                    break;
                }
            }
        }

        if(targetEntity == null) { // Could not find entity
            Logger.Error("Could not find entity with identifier: " + identifier);
            return null;
        }

        Vector2f gridPosition = new Vector2f(targetEntity.__grid[0], targetEntity.__grid[1]).mul(tileSize);
        gridPosition.x += tileSize / 2.0f;
        gridPosition.y -= 0.5f + tileSize;
        return gridPosition;
    }

}
