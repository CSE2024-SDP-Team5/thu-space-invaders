package screen;

import engine.Frame;
import engine.InputManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StageScreenTest {

    @Test
    void update() {
        StageScreen ss = new StageScreen(448, 520, 60, 5);


        assertEquals(1, ss.getStageStatus());
        assertTrue(ss.is_progress());

        StageScreen ss1 = new StageScreen(448, 520, 60, 1);
        assertFalse(ss1.is_progress());

    }
}