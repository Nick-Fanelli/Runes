package engine.state.component;

import engine.render.sprite.SpriteSheet;
import engine.render.Texture;
import engine.render.sprite.SpriteRenderer;
import org.joml.Vector4f;

public class SpriteRendererComponent extends Component {

    public Vector4f color;
    public Texture texture;
    public SpriteSheet.Sprite sprite = null;

    private SpriteRenderer spriteRenderer = null;

    public SpriteRendererComponent() {
        this(new Vector4f(1.0f));
    }

    public SpriteRendererComponent(Vector4f color) {
        this(color, null);
    }

    public SpriteRendererComponent(Vector4f color, Texture texture) {
        this.color = color;
        this.texture = texture;
    }

    public SpriteRendererComponent(Vector4f color, Texture texture, SpriteSheet.Sprite sprite) {
        this.color = color;
        this.texture = texture;
        this.sprite = sprite;
    }

    @Override
    public void OnCreate() {
        this.spriteRenderer = super.parentObject.GetParentState().GetRenderer().GetSpriteRenderer();
    }

    @Override
    public void OnRender() {
        spriteRenderer.DrawQuad(super.parentObject.transform, color, texture, sprite);
    }

}
