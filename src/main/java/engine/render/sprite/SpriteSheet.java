package engine.render.sprite;

import engine.render.Texture;
import org.joml.Vector2f;

public class SpriteSheet {

    private final Texture texture;
    public final int spriteWidth, spriteHeight;
    public final int spriteWidthSpan, spriteHeightSpan;

    public record Sprite(Vector2f[] textureCoords) {}

    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight) {
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.spriteWidthSpan = texture.GetWidth() / spriteWidth;
        this.spriteHeightSpan = texture.GetHeight() / spriteHeight;
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
