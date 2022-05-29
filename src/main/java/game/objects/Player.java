package game.objects;

import engine.asset.AssetManager;
import engine.core.Input;
import engine.render.sprite.SpriteAnimation;
import engine.render.sprite.SpriteSheet;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteAnimatorComponent;
import engine.state.component.SpriteRendererComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Player extends GameObject {

    private static final float speed = 0.75f;

    private final SpriteSheet spriteSheet;
    private final SpriteAnimation walkingAnimation;

    private SpriteRendererComponent rendererComponent;
    private SpriteAnimatorComponent animatorComponent;

    public Player(State state) {
        super(state);

        this.transform.scale = new Vector2f(0.2f);

        Texture texture = AssetManager.LoadTexture(super.parentState, "spritesheet.png");
        spriteSheet = new SpriteSheet(texture, 32, 32);

        rendererComponent = new SpriteRendererComponent(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), texture, spriteSheet.GetSprite(0, 0));
        animatorComponent = new SpriteAnimatorComponent(spriteSheet, rendererComponent);

        super.AddComponent(rendererComponent);
        super.AddComponent(animatorComponent);

        this.walkingAnimation = new SpriteAnimation(1, 65, 8, true);
    }

    private void HandleInput(float deltaTime) {
        Vector2f deltaPosition = new Vector2f();

        if(Input.IsKey(Input.KEY_RIGHT)) {
            deltaPosition.x = deltaTime * speed;
            walkingAnimation.isFlipped = false;
            animatorComponent.PlayIfNot(walkingAnimation);
        }

        if(Input.IsKey(Input.KEY_LEFT)) {
            deltaPosition.x = -deltaTime * speed;
            walkingAnimation.isFlipped = true;
            animatorComponent.PlayIfNot(walkingAnimation);
        }

        if(Input.IsKey(Input.KEY_UP)) {
            deltaPosition.y = deltaTime * speed;
        }

        if(Input.IsKey(Input.KEY_DOWN)) {
            deltaPosition.y = -deltaTime * speed;
        }

        if(deltaPosition.x == 0.0f && deltaPosition.y == 0.0f) {
            this.animatorComponent.FreezeAnimation();
        }

        this.transform.position.add(deltaPosition);
    }

    private void UpdateSprite() {}

    @Override
    public void OnUpdate(float deltaTime) {
        super.OnUpdate(deltaTime);

        HandleInput(deltaTime);
        UpdateSprite();
    }
}
