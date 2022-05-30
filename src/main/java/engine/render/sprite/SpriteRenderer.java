package engine.render.sprite;

import engine.render.Renderer;
import engine.render.Shader;
import engine.render.SubRenderer;
import engine.render.Texture;
import engine.state.State;
import engine.state.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class SpriteRenderer extends SubRenderer {

    // Vertex
    // ==============
    // Position         Color                           Texture ID  Texture Coords
    // float, float     float, float, float, float      float       float, float

    public static final int BATCH_SPRITE_COUNT = 20000;

    private static final Vector2f[] QUAD_VERTEX_POSITIONS = {
            new Vector2f(-0.5f, -0.5f),
            new Vector2f(0.5f, -0.5f),
            new Vector2f(0.5f, 0.5f),
            new Vector2f(-0.5f, 0.5f)
    };

    private static final Vector2f[] QUAD_TEXTURE_COORDS = {
            new Vector2f(0, 0),
            new Vector2f(1, 0),
            new Vector2f(1, 1),
            new Vector2f(0, 1)
    };

    private static final int BATCH_VERTEX_COUNT = BATCH_SPRITE_COUNT * 4;
    private static final int BATCH_INDEX_COUNT = BATCH_SPRITE_COUNT * 6;

    private static final int POSITION_FLOAT_COUNT = 2;
    private static final int POSITION_BYTE_COUNT = POSITION_FLOAT_COUNT * Float.BYTES;

    private static final int COLOR_FLOAT_COUNT = 4;
    private static final int COLOR_BYTE_COUNT = COLOR_FLOAT_COUNT * Float.BYTES;

    private static final int TEXTURE_ID_FLOAT_COUNT = 1;
    private static final int TEXTURE_ID_BYTE_COUNT = TEXTURE_ID_FLOAT_COUNT * Float.BYTES;

    private static final int TEXTURE_COORDS_FLOAT_COUNT = 2;
    private static final int TEXTURE_COORDS_BYTE_COUNT = TEXTURE_COORDS_FLOAT_COUNT * Float.BYTES;

    private static final int POSITION_OFFSET_BYTES = 0;
    private static final int COLOR_OFFSET_BYTES = POSITION_OFFSET_BYTES + POSITION_BYTE_COUNT;
    private static final int TEXTURE_ID_OFFSET_BYTES = COLOR_OFFSET_BYTES + COLOR_BYTE_COUNT;
    private static final int TEXTURE_COORDS_OFFSET_BYTES = TEXTURE_ID_OFFSET_BYTES + TEXTURE_ID_BYTE_COUNT;

    private static final int VERTEX_FLOAT_COUNT = POSITION_FLOAT_COUNT + COLOR_FLOAT_COUNT + TEXTURE_ID_FLOAT_COUNT + TEXTURE_COORDS_FLOAT_COUNT;
    private static final int VERTEX_BYTE_COUNT = VERTEX_FLOAT_COUNT * Float.BYTES;

    private Shader shader;
    private int vaoID, vboID, iboID;

    private float[] vertices;
    private int subVertexCount = 0;
    private int indexCount = 0;

    private int[] textures;
    private int[] textureSlots;
    private int textureCount = 1;

    public SpriteRenderer(Renderer renderer, State state) { super(renderer, state); }

    @Override
    public void OnCreate() {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("MAX_TEXTURE_COUNT", Integer.toString(super.renderer.GetGUPMaxTextureSlots()));

        shader = new Shader("SpriteRenderer", replacements);
        shader.Create();

        // Create the vertices
        vertices = new float[BATCH_VERTEX_COUNT * VERTEX_FLOAT_COUNT];

        // Allocate the texture slots
        textures = new int[super.renderer.GetGUPMaxTextureSlots()];

        textureSlots = new int[super.renderer.GetGUPMaxTextureSlots()];
        for(int i = 0; i < this.textureSlots.length; i++) {
            textureSlots[i] = i;
        }

        // Create the VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create the VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        // Allocate the space in the VBO
        glBufferData(GL_ARRAY_BUFFER, VERTEX_BYTE_COUNT * BATCH_VERTEX_COUNT, GL_DYNAMIC_DRAW);

        // Setup the Vertex Attrib Pointers
        glVertexAttribPointer(0, POSITION_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, POSITION_OFFSET_BYTES);
        glVertexAttribPointer(1, COLOR_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, COLOR_OFFSET_BYTES);
        glVertexAttribPointer(2, TEXTURE_ID_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, TEXTURE_ID_OFFSET_BYTES);
        glVertexAttribPointer(3, TEXTURE_COORDS_FLOAT_COUNT, GL_FLOAT, false, VERTEX_BYTE_COUNT, TEXTURE_COORDS_OFFSET_BYTES);

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
        shader.AddUniformIntArray("uTextures", this.textureSlots);

        for(int i = 1; i < textureCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, textures[i]);
        }

        glBindVertexArray(vaoID);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);

        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);

        glBindVertexArray(0);

        glUseProgram(0); // Unbind Shader
    }

    private void AllocateQuad() {
        if(subVertexCount + (VERTEX_FLOAT_COUNT * 4) >= vertices.length) {
            End();
            Begin();
        }
    }

    private void AllocateTexture() {
        if(textureCount >= super.renderer.GetGUPMaxTextureSlots()) {
            End();
            Begin();
        }
    }

    @Override
    public void Begin() {
        subVertexCount = 0;
        textureCount = 1;
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

    private void AddVertex(Vector2f position, Vector4f color, Vector2f textureCoords, int textureID) {
        // Position
        vertices[subVertexCount] = position.x;
        vertices[subVertexCount + 1] = position.y;

        // Color
        vertices[subVertexCount + 2] = color.x;
        vertices[subVertexCount + 3] = color.y;
        vertices[subVertexCount + 4] = color.z;
        vertices[subVertexCount + 5] = color.w;

        // Texture ID
        vertices[subVertexCount + 6] = textureID;

        // Texture Coords
        vertices[subVertexCount + 7] = textureCoords.x;
        vertices[subVertexCount + 8] = textureCoords.y;

        subVertexCount += VERTEX_FLOAT_COUNT;
    }

    public void DrawQuad(Transform transform, Vector4f color, Texture texture, SpriteSheet.Sprite sprite) {

        AllocateQuad();

        int textureIndex = 0;
        boolean shouldAllocateTexture = true;

        if(texture != null) {
            for (int i = 0; i < textureCount; i++) {
                if (textures[i] == texture.GetTextureID()) {
                    textureIndex = i;
                    shouldAllocateTexture = false;
                    break;
                }
            }

            if (shouldAllocateTexture) {
                AllocateTexture();

                textureIndex = textureCount;
                textures[textureCount] = texture.GetTextureID();
                textureCount++;
            }
        }

        for(int i = 0; i < 4; i++) {
            AddVertex(
                    new Vector2f(transform.position.x + (QUAD_VERTEX_POSITIONS[i].x * transform.scale.x),
                                 transform.position.y + (QUAD_VERTEX_POSITIONS[i].y * transform.scale.y)),
                    color,
                    sprite == null ? QUAD_TEXTURE_COORDS[i] : sprite.textureCoords()[i],
                    textureIndex
            );
        }

        indexCount += 6; // 6 indices per quad

    }
}
