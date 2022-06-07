package engine.physics2d.colliders;

import engine.render.sprite.SpriteRenderer;
import engine.state.Transform;
import engine.state.component.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class BoxCollider2D extends Component {

    public static final boolean SHOULD_DEBUG_DRAW = false;

    public Vector2f positionOffset = new Vector2f();
    public Vector2f scale = new Vector2f();

    private SpriteRenderer spriteRenderer;

    @Override
    public void OnCreate() {
        this.spriteRenderer = super.parentObject.GetParentState().GetRenderer().GetSpriteRenderer();
        scale = super.parentObject.transform.scale;
    }

    @Override
    public void OnRender() {
        if(!SHOULD_DEBUG_DRAW)
            return;

        spriteRenderer.DrawQuad(new Transform(
                new Vector2f(super.parentObject.transform.position).add(positionOffset),
                scale, super.parentObject.transform.rotation
        ), new Vector4f(1.0f, 0.0f, 0.0f, 0.4f),
                null, null);
    }
}
