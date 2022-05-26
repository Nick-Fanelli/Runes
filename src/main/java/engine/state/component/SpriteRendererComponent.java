package engine.state.component;

import engine.render.Texture;
import engine.render.spirterender.SpriteRenderer;
import org.joml.Vector4f;

public class SpriteRendererComponent extends Component {

    public Vector4f color;
    public Texture texture;

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

    @Override
    public void OnCreate() {
        this.spriteRenderer = super.parentObject.GetParentState().GetRenderer().GetSpriteRenderer();
    }

    @Override
    public void OnUpdate(float deltaTime) {
        spriteRenderer.DrawQuad(super.parentObject.transform, color, texture);
    }

    @Override
    public void OnDestroy() {

    }
}
