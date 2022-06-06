package engine.physics2d;

import engine.physics2d.colliders.BoxCollider2D;
import engine.physics2d.colliders.CircleCollider;
import engine.state.GameObject;
import engine.state.Transform;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;

public class Physics2D {

    public static final float PHYSICS_TIMESTEP = 1.0f / 60.0f;
    public static final int VELOCITY_ITERATIONS = 8; // 8
    public static final int POSITION_ITERATIONS = 3; // 3

    private final Vec2 gravity = new Vec2(0, -10.0f);
    private final World physicsWorld = new World(gravity);

    private float physicsTime = 0.0f;

    public void AddGameObject(GameObject gameObject) {
        Rigidbody2D rb = gameObject.GetComponent(Rigidbody2D.class);

        if(rb != null && rb.GetRawBody() == null) {
            Transform transform = gameObject.transform;

            // Define physics body
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(transform.position.x, transform.position.y);
            bodyDef.angle = (float) Math.toRadians(transform.rotation);
            bodyDef.angularDamping = rb.angularDamping;
            bodyDef.linearDamping = rb.linearDamping;
            bodyDef.fixedRotation = rb.IsFixedRotation();
            bodyDef.bullet = rb.IsContinuousCollision();
            bodyDef.type = rb.bodyType;
            bodyDef.userData = gameObject;

            BoxCollider2D boxCollider = gameObject.GetComponent(BoxCollider2D.class);
            CircleCollider circleCollider = gameObject.GetComponent(CircleCollider.class);

            Body body = physicsWorld.createBody(bodyDef);

            if(boxCollider != null) { // Contains Box Collider
                // Create Polygon Shape
                PolygonShape shape = new PolygonShape();

                Vector2f halfSize = new Vector2f(boxCollider.scale.x / 2.0f, boxCollider.scale.y / 2.0f).mul(0.5f);
                Vector2f offset = boxCollider.positionOffset;

                shape.setAsBox(halfSize.x, halfSize.y, new Vec2(offset.x, offset.y), 0);

                FixtureDef fixtureDef = new FixtureDef();

                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.0f;
                fixtureDef.userData = gameObject;

                body.createFixture(fixtureDef);
            }

            if(circleCollider != null) {
                CircleShape shape = new CircleShape();
                shape.setRadius(circleCollider.radius);
                shape.m_p.set(circleCollider.positionOffset.x, circleCollider.positionOffset.y);

                FixtureDef fixtureDef = new FixtureDef();

                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.0f;
                fixtureDef.userData = gameObject;

                body.createFixture(fixtureDef);
            }

            rb.SetRawBody(body);
        }
    }

    public void OnUpdate(float deltaTime) {
        physicsTime += deltaTime;

        if(physicsTime >= 0) {
            physicsTime -= PHYSICS_TIMESTEP;
            physicsWorld.step(PHYSICS_TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

}
