package engine.map.ldtk;

import org.joml.Vector2f;

public class LDtkEntity {

    public String __identifier;
    public int[] __grid;

    public Vector2f position;

    public void ComputeEntity(LDtkLayer layer) {
        this.position = new Vector2f(__grid[0] * layer.glTileSize, 1 - __grid[1] * layer.glTileSize);
        System.out.println(this.position);
    }

}
