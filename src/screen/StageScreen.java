package screen;

import engine.*;

import java.awt.event.KeyEvent;

/**
 * Implements the Map Stage screen.
 * Stage Screen Skeleton Code
 */

public class StageScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    /**
     * status of the selected stage
     */
    private int stage_status = 1;

    int prog_stage;


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
    public StageScreen(final int width, final int height, final int fps, int p_stage){
        super(width, height, fps);

        prog_stage = p_stage;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
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
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                if (stage_status == 4 || stage_status == 7) stage_status--;

                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                if (stage_status < prog_stage && (stage_status == 3 || stage_status == 6)) stage_status++;

                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                if (stage_status < prog_stage && (stage_status < 3 || stage_status == 7)) stage_status++;
                else if (stage_status == 5 || stage_status == 6) stage_status--;

                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                if (stage_status == 2 || stage_status == 3 || stage_status == 8) stage_status--;
                else if (stage_status < prog_stage && (stage_status == 4 || stage_status == 5)) stage_status++;

                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                this.returnCode = 1;
                this.isRunning = false;
                this.selectionCooldown.reset();

            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                this.returnCode = 101;
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
        drawManager.drawStoryMap(this, stage_status, prog_stage);
        drawManager.completeDrawing(this);
    }

}