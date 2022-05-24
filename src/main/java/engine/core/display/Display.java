package engine.core.display;

import engine.core.eventsystem.EventListener;
import engine.core.eventsystem.EventSystem;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private final DisplayPreferences displayPreferences;

    private final EventSystem<WindowResizeEvent> windowResizeEventEventSystem = new EventSystem<>();

    private long windowPtr;

    public Display(DisplayPreferences displayPreferences) {
        this.displayPreferences = displayPreferences;
    }

    public void InitializeDisplay() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, displayPreferences.isResizable ? GLFW_TRUE : GLFW_FALSE);

        this.windowPtr = glfwCreateWindow(displayPreferences.startingWidth, displayPreferences.startingHeight,
                displayPreferences.displayName, NULL, NULL);

        if(this.windowPtr == NULL)
            throw new RuntimeException("Failed to create the GLFW Window");

        // Bind Key Callback
        // TODO: Run through display input system
        glfwSetKeyCallback(windowPtr, (windowPtr, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(windowPtr, true);
        });

        glfwSetWindowSizeCallback(windowPtr, (windowPtr, width, height) -> {
            glViewport(0, 0, width, height); // Resize Viewport

            windowResizeEventEventSystem.CallEvent(new WindowResizeEvent((float) width / (float) height));
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int pointer
            IntBuffer pHeight = stack.mallocInt(1); // int pointer

            glfwGetWindowSize(windowPtr, pWidth, pHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidMode != null;

            glfwSetWindowPos(windowPtr,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }

        int[] width = new int[1];
        int[] height = new int[1];

        glfwGetFramebufferSize(windowPtr, width, height);

        glfwMakeContextCurrent(windowPtr);

        glfwSwapInterval(displayPreferences.vSyncEnabled ? 1 : 0); // Enable V-Sync

        glfwWindowHint(GLFW_SAMPLES, 4);

        GL.createCapabilities();

        glViewport(0, 0, displayPreferences.startingWidth, displayPreferences.startingHeight);

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_MULTISAMPLE);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glfwShowWindow(windowPtr);
        glfwFocusWindow(windowPtr);
    }

    public void DestroyDisplay() {
        glfwDestroyWindow(windowPtr);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void AddWindowResizeEventListener(EventListener<WindowResizeEvent> eventListener) {
        windowResizeEventEventSystem.AddEventListener(eventListener);
    }

    public long GetWindowPtr() { return this.windowPtr; }
}
