package engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    private static final float WIDTH_ASPECT = 1920.0f, HEIGHT_ASPECT = 1080.0f;

    private float aspectRatio = 1600.0f / 900.0f;
    private float scale = 1f;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();

        CalculateProjectionMatrix();
        CalculateViewMatrix();
    }

    public void CalculateProjectionMatrix() {
        projectionMatrix.identity();
        projectionMatrix.ortho2D(-aspectRatio, aspectRatio, -1.0f, 1.0f);
    }

    public void CalculateViewMatrix() {
        viewMatrix.identity();
        viewMatrix.translate(new Vector3f(position.x, position.y, 0.0f)).invert();
    }

    public Vector2f GetPosition() { return this.position; }
    public void SetPosition(Vector2f position) {
        this.position = position;
        CalculateViewMatrix();
    }

    public void SetXPosition(float x) {
        if(x == this.position.x)
            return;
        this.position.x = x;
        CalculateViewMatrix();
    }

    public void SetYPosition(float y) {
        if(y == this.position.y)
            return;
        this.position.y = y;
        CalculateViewMatrix();
    }

    public Matrix4f GetProjectionMatrix() { return this.projectionMatrix; }
    public Matrix4f GetViewMatrix() { return this.viewMatrix; }

    public void UpdateAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        CalculateProjectionMatrix();
    }

    public float GetAspectRatio() { return this.aspectRatio; }
    public float GetScale() { return this.scale; }

}
