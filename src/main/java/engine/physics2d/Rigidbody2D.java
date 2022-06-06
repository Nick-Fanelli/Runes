package engine.physics2d;

import engine.state.component.Component;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

public class Rigidbody2D extends Component {

    public BodyType bodyType = BodyType.DYNAMIC;

    public float angularDamping = 0.8f;
    public float linearDamping = 0.9f;
    public float mass = 0.0f;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private Vector2f velocity = new Vector2f();

    private Body rawBody = null;

    @Override
    public void OnUpdate(float deltaTime) {
        if(rawBody != null) {
            super.parentObject.transform.position.set(
                    rawBody.getPosition().x,
                    rawBody.getPosition().y
            );

            super.parentObject.transform.rotation = (float) Math.toDegrees(rawBody.getAngle());
        }
    }

    public void ApplyForce(float xForce, float yForce) {
        rawBody.applyForceToCenter(new Vec2(xForce, yForce));
    }

    public void ApplyDesiredXLinearVelocity(float linearVelocity) {
        Vec2 currentVelocity = rawBody.getLinearVelocity();

        float deltaVelocity = linearVelocity - currentVelocity.x;
        float impulse = rawBody.getMass() * deltaVelocity;

        rawBody.applyLinearImpulse(new Vec2(impulse, 0), rawBody.getWorldCenter());
    }

    public boolean IsFixedRotation() { return fixedRotation; }
    public void SetFixedRotation(boolean fixedRotation) { this.fixedRotation = fixedRotation; }

    public boolean IsContinuousCollision() { return continuousCollision; }
    public void SetContinuousCollision(boolean continuousCollision) { this.continuousCollision = continuousCollision; }

    public Vector2f GetVelocity() { return velocity; }
    public void SetVelocity(Vector2f velocity) { this.velocity = velocity; }

    public Body GetRawBody() { return this.rawBody; }
    public void SetRawBody(Body rawBody) { this.rawBody = rawBody; }
}
