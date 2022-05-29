package engine.map.ldtk;

public class LDtkTile {

    public int[] px; // Pixel Coordinate of tile in layer (optional layer offsets)
    public int t; // Tile ID

    @Override
    public String toString() {
        return String.format("{ px: [%d, %d], t: %d }", px[0], px[1], t);
    }
}
