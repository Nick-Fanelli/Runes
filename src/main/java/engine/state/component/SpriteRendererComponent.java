package engine.state.component;

import engine.render.spirterender.SpriteRenderer;
import engine.state.component.Component;
import org.joml.Vector4f;

public class SpriteRendererComponent extends Component {

    public Vector4f color;

    private SpriteRenderer spriteRenderer = null;

    public SpriteRendererComponent() {
        this(new Vector4f(1.0f));
    }
    public SpriteRendererComponent(Vector4f color) {
        this.color = color;
    }

    @Override
    public void OnCreate() {
        this.spriteRenderer = super.parentObject.GetParentState().GetRenderer().GetSpriteRenderer();
    }

    @Override
    public void OnUpdate(float deltaTime) {
        spriteRenderer.DrawQuad(super.parentObject.transform, color);
    }

    @Override
    public void OnDestroy() {

    }
}
