package engine.core;

import engine.core.display.Display;
import engine.core.display.DisplayPreferences;
import engine.state.State;
import engine.state.StateManager;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Application {

    private final String applicationName;

    private Display display;
    private Input input;
    private StateManager stateManager;

    private int currentFps = -1;

    public Application(String applicationName) {
        this.applicationName = applicationName;
    }

    public void StartApplication(State entryState) {
        // Setup the State Manager
        this.stateManager = new StateManager();

        // Setup the Display
        DisplayPreferences displayPreferences = new DisplayPreferences();
        displayPreferences.displayName = this.applicationName;

        this.display = new Display(displayPreferences);
        this.display.InitializeDisplay();
        this.display.AddWindowResizeEventListener(event -> {
            this.stateManager.OnWindowResize(event);
        });
        final long windowPtr = this.display.GetWindowPtr();

        // Setup and Bind Input
        this.input = new Input();

        glfwSetKeyCallback(windowPtr, this.input::KeyCallback);
        glfwSetMouseButtonCallback(windowPtr, this.input::MouseButtonCallback);
        glfwSetCursorPosCallback(windowPtr, this.input::MousePositionCallback);
        glfwSetScrollCallback(windowPtr, this.input::MouseScrollCallback);

        StartGameLoop(entryState);
    }

    private void StartGameLoop(State entryState) {

        StateManager.SetCurrentState(entryState);

        // Set the entry scene
        final long windowPtr = this.display.GetWindowPtr();

        float endTime, startTime = (float) glfwGetTime();
        float deltaTime = 0.0f;
        float accumulativeDeltaTime = 0.0f;

        int frameCount = 0;

        // Game Loop
        while(!glfwWindowShouldClose(windowPtr)) {

            glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if(deltaTime >= 0) {
                this.stateManager.OnUpdate(deltaTime);
                this.input.Update();
            }

            glfwSwapBuffers(windowPtr);
            glfwPollEvents();

            endTime = (float) glfwGetTime();
            deltaTime = endTime - startTime;
            startTime = endTime;

            accumulativeDeltaTime += deltaTime;
            if(accumulativeDeltaTime > 1.0f) {
                currentFps = frameCount;
                frameCount = 0;
                accumulativeDeltaTime = 0.0f;
            }

            frameCount++;
        }

        // Destruction
        CleanUp();
    }

    private void CleanUp() {
        this.stateManager.OnDestroy();
        this.display.DestroyDisplay();
    }

    public int GetCurrentFps() { return this.currentFps; }

}
