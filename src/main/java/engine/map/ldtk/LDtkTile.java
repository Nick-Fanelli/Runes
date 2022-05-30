package engine.map.ldtk;

public class LDtkTile {

    public int[] px; // Pixel Coordinate of tile in layer (optional layer offsets)
    public int t; // Tile ID
    public int f; // Flip f=0 (no flip), f=1 (X flip only), f=2 (Y flip only), f=3 (both flips)

    @Override
    public String toString() {
        return String.format("{ px: [%d, %d], t: %d }", px[0], px[1], t);
    }
}
