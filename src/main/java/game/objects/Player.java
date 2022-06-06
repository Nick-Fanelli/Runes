package game.objects;

import engine.asset.AssetManager;
import engine.core.Input;
import engine.physics2d.Rigidbody2D;
import engine.physics2d.colliders.BoxCollider2D;
import engine.physics2d.colliders.CircleCollider;
import engine.render.sprite.SpriteAnimation;
import engine.render.sprite.SpriteSheet;
import engine.render.Texture;
import engine.state.GameObject;
import engine.state.State;
import engine.state.component.SpriteAnimatorComponent;
import engine.state.component.SpriteRendererComponent;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Player extends GameObject {

    private static final float speed = 1f;

    private final SpriteSheet spriteSheet;
    private final SpriteAnimation walkingAnimation;

    private final SpriteRendererComponent rendererComponent;
    private final SpriteAnimatorComponent animatorComponent;
    private final Rigidbody2D rigidbody2D;

    public Player(State state) {
        super(state);

        this.transform.position = new Vector2f(0.0f, 1.0f);
        this.transform.scale = new Vector2f(0.2f);

        Texture texture = AssetManager.LoadTexture(super.parentState, "spritesheet.png");
        spriteSheet = new SpriteSheet(texture, 32, 32);

        rendererComponent = new SpriteRendererComponent(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), texture, spriteSheet.GetSprite(0, 0));
        animatorComponent = new SpriteAnimatorComponent(spriteSheet, rendererComponent);
        rigidbody2D = new Rigidbody2D();
        rigidbody2D.SetFixedRotation(true);

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.radius = 0.04f;
        circleCollider.positionOffset.y = -0.05f;

        super.AddComponent(rendererComponent);
        super.AddComponent(animatorComponent);
        super.AddComponent(rigidbody2D);
        super.AddComponent(circleCollider);

        this.walkingAnimation = new SpriteAnimation(1, 65, 8, true);
    }

    private void HandleInput(float deltaTime) {
        boolean isMovingHorizontally = false;

        if(Input.IsKey(Input.KEY_RIGHT)) {
            rigidbody2D.ApplyDesiredXLinearVelocity(speed);
            walkingAnimation.isFlipped = false;
            animatorComponent.PlayIfNot(walkingAnimation);
            isMovingHorizontally = true;
        }

        if(Input.IsKey(Input.KEY_LEFT)) {
            rigidbody2D.ApplyDesiredXLinearVelocity(-speed);
            walkingAnimation.isFlipped = true;
            animatorComponent.PlayIfNot(walkingAnimation);
            isMovingHorizontally = true;
        }

        if(Input.IsKey(Input.KEY_UP)) {
            rigidbody2D.ApplyForce(0.0f, 0.1f);
        }

        if(!isMovingHorizontally) {
            animatorComponent.FreezeAnimation();
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
