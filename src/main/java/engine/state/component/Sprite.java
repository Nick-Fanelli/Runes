package engine.state.component;

import engine.render.sprite.SpriteSheet;
import engine.render.Texture;
import engine.render.sprite.SpriteRenderer;
import org.joml.Vector4f;

public class Sprite extends Component {

    public Vector4f color;
    public Texture texture;
    public SpriteSheet.Sprite sprite = null;

    private SpriteRenderer spriteRenderer = null;

    public Sprite() {
        this(new Vector4f(1.0f));
    }

    public Sprite(Vector4f color) {
        this(color, null);
    }

    public Sprite(Vector4f color, Texture texture) {
        this.color = color;
        this.texture = texture;
    }

    public Sprite(Vector4f color, Texture texture, SpriteSheet.Sprite sprite) {
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
