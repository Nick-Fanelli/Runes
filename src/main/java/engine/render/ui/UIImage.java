package engine.render.ui;

import engine.render.Texture;
import engine.render.sprite.SpriteSheet;
import org.joml.Vector4f;

public class UIImage extends UIElement {

    public Vector4f tint = new Vector4f(1.0f);
    public Texture texture;
    public SpriteSheet.Sprite sprite;

    public UIImage() {}

    public UIImage(Vector4f tint, Texture texture, SpriteSheet.Sprite sprite) {
        this.tint = tint;
        this.texture = texture;
        this.sprite = sprite;
    }

    @Override
    public void OnRender(UIRenderer uiRenderer) {
        uiRenderer.DrawQuad(transform, anchorPoint, tint, texture, sprite);
    }
}
