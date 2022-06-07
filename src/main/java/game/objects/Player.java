package game.objects;

import engine.asset.AssetManager;
import engine.core.Input;
import engine.physics2d.Rigidbody2D;
import engine.physics2d.colliders.CircleCollider;
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

    private static final float speed = 1f;

    private final SpriteAnimation idleAnimation;
    private final SpriteAnimation walkingAnimation;

    private final SpriteAnimatorComponent animatorComponent;
    private final Rigidbody2D rigidbody2D;

    public Player(State state) {
        super(state);

        this.transform.position = new Vector2f(0.0f, 1.0f);
        this.transform.scale = new Vector2f(0.2f);

        Texture texture = AssetManager.LoadTexture(super.parentState, "spritesheet.png");
        SpriteSheet spriteSheet = new SpriteSheet(texture, 32, 32);

        SpriteRendererComponent rendererComponent = new SpriteRendererComponent(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), texture, spriteSheet.GetSprite(0, 0));
        animatorComponent = new SpriteAnimatorComponent(spriteSheet, rendererComponent);
        rigidbody2D = new Rigidbody2D();
        rigidbody2D.SetFixedRotation(true);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.radius = 0.04f;
        circleCollider.positionOffset.y = -0.03f;

        super.AddComponent(rendererComponent);
        super.AddComponent(animatorComponent);
        super.AddComponent(rigidbody2D);
        super.AddComponent(circleCollider);

        this.idleAnimation = new SpriteAnimation(0, 100, 13, true);
        this.walkingAnimation = new SpriteAnimation(1, 65, 8, true);
    }

    private void HandleInput(float deltaTime) {
        boolean isAnimating = false;

        if(Input.IsKey(Input.KEY_RIGHT)) {
            rigidbody2D.SetDesiredXLinearVelocity(speed);
            walkingAnimation.isFlipped = false;
            animatorComponent.PlayIfNot(walkingAnimation);
            isAnimating = true;
        } else if(Input.IsKey(Input.KEY_LEFT)) {
            rigidbody2D.SetDesiredXLinearVelocity(-speed);
            walkingAnimation.isFlipped = true;
            animatorComponent.PlayIfNot(walkingAnimation);
            isAnimating = true;
        } else {
            rigidbody2D.SetDesiredXLinearVelocity(0);
        }

        if(Input.IsKey(Input.KEY_UP)) {
            rigidbody2D.SetDesiredYLinearVelocity(1f);
        }

        if(!isAnimating) {
            animatorComponent.PlayIfNot(idleAnimation);
        }
    }

    private void UpdateSprite() {}

    @Override
    public void OnUpdate(float deltaTime) {
        HandleInput(deltaTime);
        UpdateSprite();

        super.OnUpdate(deltaTime);
    }
}
