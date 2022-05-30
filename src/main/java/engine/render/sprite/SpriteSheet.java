package engine.render.sprite;

import engine.render.Texture;
import org.joml.Vector2f;

public class SpriteSheet {

    private final Texture texture;
    public final int spriteWidth, spriteHeight;
    public final int spriteWidthSpan, spriteHeightSpan;

    public record Sprite(Vector2f[] textureCoords) {}

    public enum SpriteFlip {
        NO_FLIP(0),
        FLIP_VERTICAL(1),
        FLIP_HORIZONTAL(2),
        FLIP_BOTH(3);

        public final int flipValue;

        SpriteFlip(int flipValue) {
            this.flipValue = flipValue;
        }

        public static SpriteFlip GetFlip(int flipValue) {
            return switch (flipValue) {
                case 0 -> NO_FLIP;
                case 1 -> FLIP_VERTICAL;
                case 2 -> FLIP_HORIZONTAL;
                default -> FLIP_BOTH;
            };
        }
    }

    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight) {
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spriteWidthSpan = texture.GetWidth() / spriteWidth;
        this.spriteHeightSpan = texture.GetHeight() / spriteHeight;
    }

    public Sprite GetSprite(int x, int y, int flip) {
        y = (spriteHeightSpan - 1) - y;

        float startX = (float) (x * spriteWidth) / texture.GetWidth();
        float startY = (float) (y * spriteHeight) / texture.GetHeight();

        float endX = (float) ((x + 1) * spriteWidth) / texture.GetWidth();
        float endY = (float) ((y + 1) * spriteHeight) / texture.GetHeight();

        if(flip == 0) { // No Flip
            return new Sprite(new Vector2f[]{
                    new Vector2f(startX, startY), // 0
                    new Vector2f(endX, startY), // 1
                    new Vector2f(endX, endY), // 2
                    new Vector2f(startX, endY) // 3
            });
        } else if(flip == 1) { // Horizontal Flip
            return new Sprite(new Vector2f[] {
                    new Vector2f(endX, startY),
                    new Vector2f(startX, startY),
                    new Vector2f(startX, endY),
                    new Vector2f(endX, endY)
            });
        } else if(flip == 2) { // Vertical Flip
            return new Sprite(new Vector2f[]{
                    new Vector2f(endX, startY), // 1
                    new Vector2f(startX, startY), // 0
                    new Vector2f(startX, endY), // 3
                    new Vector2f(endX, endY) // 2
            });
        } else { // Both Flip
            return new Sprite(new Vector2f[]{
                    new Vector2f(startX, endY),
                    new Vector2f(endX, endY),
                    new Vector2f(endX, startY),
                    new Vector2f(startX, startY),
            });
        }
    }

    public Sprite GetSprite(int x, int y) {
        y = (spriteHeightSpan - 1) - y;

        float startX = (float) (x * spriteWidth) / texture.GetWidth();
        float startY = (float) (y * spriteHeight) / texture.GetHeight();

        float endX = (float) ((x + 1) * spriteWidth) / texture.GetWidth();
        float endY = (float) ((y + 1) * spriteHeight) / texture.GetHeight();

        return new Sprite(new Vector2f[] {
                new Vector2f(startX, startY),
                new Vector2f(endX, startY),
                new Vector2f(endX, endY),
                new Vector2f(startX, endY)
        });
    }

    public Sprite GetSprite(int t, SpriteFlip flip) {
        int y = t / spriteWidthSpan;
        int x = t % spriteWidthSpan;

        return GetSprite(x, y, flip.flipValue);
    }

    public Sprite GetSpriteFlipped(int x, int y) {
        y = (spriteHeightSpan - 1) - y;

        float startX = (float) (x * spriteWidth) / texture.GetWidth();
        float startY = (float) (y * spriteHeight) / texture.GetHeight();

        float endX = (float) ((x + 1) * spriteWidth) / texture.GetWidth();
        float endY = (float) ((y + 1) * spriteHeight) / texture.GetHeight();

        return new Sprite(new Vector2f[] {
                new Vector2f(endX, startY),
                new Vector2f(startX, startY),
                new Vector2f(startX, endY),
                new Vector2f(endX, endY)
        });
    }

}
