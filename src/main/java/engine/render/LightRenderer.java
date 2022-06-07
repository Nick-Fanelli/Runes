package engine.render;

import engine.state.State;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LightRenderer extends SubRenderer {

    // Vertex
    // ==============
    // Position             Relative Pos        Color
    // float, float         float, float        float, float, float, float

    public static final int BATCH_LIGHT_COUNT = 200;

    private static final Vector2f[] QUAD_REL_POSITIONS = {
            new Vector2f(-1.0f, -1.0f),
            new Vector2f(1.0f, -1.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(-1.0f, 1.0f)
    };

    private static final Vector2f[] QUAD_VERTEX_POSITIONS = {
            new Vector2f(-0.5f, -0.5f),
            new Vector2f(0.5f, -0.5f),
            new Vector2f(0.5f, 0.5f),
            new Vector2f(-0.5f, 0.5f)
    };

    private static final int BATCH_VERTEX_COUNT = BATCH_LIGHT_COUNT * 4;
    private static final int BATCH_INDEX_COUNT = BATCH_LIGHT_COUNT * 6;

    // Position
    private static final int POSITION_FLOAT_COUNT = 2;
    private static final int POSITION_BYTE_COUNT = POSITION_FLOAT_COUNT * Float.BYTES;

    // Position Rel
    private static final int POSITION_REL_FLOAT_COUNT = 2;
    private static final int POSITION_REL_BYTE_COUNT = POSITION_REL_FLOAT_COUNT * Float.BYTES;

    // Color
    private static final int COLOR_FLOAT_COUNT = 4;
    private static final int COLOR_BYTE_COUNT = COLOR_FLOAT_COUNT * Float.BYTES;

    // Offsets
    private static final int POSITION_OFFSET_BYTES = 0;
    private static final int POSITION_REL_OFFSET_BYTES = POSITION_OFFSET_BYTES + POSITION_BYTE_COUNT;
    private static final int COLOR_OFFSET_BYTES = POSITION_REL_OFFSET_BYTES + POSITION_REL_BYTE_COUNT;

    private static final int VERTEX_FLOAT_COUNT = POSITION_FLOAT_COUNT + POSITION_REL_FLOAT_COUNT + COLOR_FLOAT_COUNT;
    private static final int VERTEX_BYTE_COUNT = VERTEX_FLOAT_COUNT * Float.BYTES;

    private Shader shader;
    private int vaoID, vboID, iboID;

    private float[] vertices;
    private int subVertexCount = 0;
    private int indexCount = 0;

    public LightRenderer(Renderer renderer, State state) {
        super(renderer, state);
    }

    @Override
    public void OnCreate() {

        // Set Blend Func

        shader = new Shader("LightRenderer");
        shader.Create();

        // Create the Vertices
        vertices = new float[BATCH_VERTEX_COUNT * VERTEX_FLOAT_COUNT];

        // Create the VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create the VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        // Allocate Space in the VBO
        glBufferData(GL_ARRAY_BUFFER, VERTEX_BYTE_COUNT * BATCH_VERTEX_COUNT, GL_DYNAMIC_DRAW);

        // Setup the Vertex Attrib Pointers
        glVertexAttribPointer(0, POSITION_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, POSITION_OFFSET_BYTES);
        glVertexAttribPointer(1, POSITION_REL_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, POSITION_REL_OFFSET_BYTES);
        glVertexAttribPointer(2, COLOR_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, COLOR_OFFSET_BYTES);

        // Unbind the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Create the indices
        int[] indices = new int[BATCH_INDEX_COUNT];
        int offset = 0;

        for(int i = 0; i < BATCH_INDEX_COUNT; i += 6) {
            // First Triangle
            indices[i] = offset;
            indices[i + 1] = offset + 1;
            indices[i + 2] = offset + 2;

            // Second Triangle
            indices[i + 3] = offset + 2;
            indices[i + 4] = offset + 3;
            indices[i + 5] = offset;

            offset += 4; // Four Vertices Per Quad
        }

        // Bind the indices
        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


    }

    private void UpdateBatchVertexData() {
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void Render() {

        shader.Bind();

        shader.AddUniformMat4("uProjection", super.state.camera.GetProjectionMatrix());
        shader.AddUniformMat4("uView", super.state.camera.GetViewMatrix());

        glBindVertexArray(vaoID);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindVertexArray(0);

        glUseProgram(0);
    }

    @Override
    public void Begin() {
        subVertexCount = 0;
    }

    @Override
    public void End() {
        UpdateBatchVertexData();
        Render();
    }

    @Override
    public void OnDestroy() {
        shader.Destroy();

        glDeleteVertexArrays(vaoID);
        glDeleteBuffers(vboID);
        glDeleteBuffers(iboID);
    }

    private void AllocateQuad() {
        if(subVertexCount + (VERTEX_FLOAT_COUNT * 4) >= vertices.length) {
            End();
            Begin();
        }
    }

    private void AddVertex(Vector2f position, int vertexID, Vector4f color) {
        // Position
        vertices[subVertexCount] = position.x;
        vertices[subVertexCount + 1] = position.y;

        // Position Rel
        vertices[subVertexCount + 2] = QUAD_REL_POSITIONS[vertexID].x;
        vertices[subVertexCount + 3] = QUAD_REL_POSITIONS[vertexID].y;

        // Color
        vertices[subVertexCount + 4] = color.x;
        vertices[subVertexCount + 5] = color.y;
        vertices[subVertexCount + 6] = color.z;
        vertices[subVertexCount + 7] = color.w;

        subVertexCount += VERTEX_FLOAT_COUNT;
    }

    public void DrawLight(Vector2f position, Vector4f color) {

        AllocateQuad();

        for(int i = 0; i < 4; i++) {
            AddVertex(
                new Vector2f(position.x + QUAD_VERTEX_POSITIONS[i].x,
                             position.y + QUAD_VERTEX_POSITIONS[i].y),
                i,
                color
            );
        }

        indexCount += 6; // 6 indices per quad

    }
}
