package screen;

import engine.Cooldown;
import engine.Core;
import engine.Scenario;

import java.awt.event.KeyEvent;

public class ScenarioScreen extends Screen {
    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    private Scenario scenario;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public ScenarioScreen(final int width, final int height, final int fps, Scenario scenario){
        super(width, height, fps);

        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        this.scenario = scenario;
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run(){
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update(){
        super.update();

        draw();

        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()){
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                //this.returnCode = 2;
                this.isRunning = false;
                this.selectionCooldown.reset();
            }
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw(){
        drawManager.initDrawing(this);
        drawManager.drawStoryMap_Basic(this);
        drawManager.drawScenario(this, scenario);
        drawManager.completeDrawing(this);
    }
}
