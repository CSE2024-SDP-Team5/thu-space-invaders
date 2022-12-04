package engine;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @org.junit.jupiter.api.Test
    void getSpeaker() {
        int speaker = 0;
        Scenario scenario = new Scenario(speaker);
        assertEquals(scenario.getSpeaker(), 0);
    }

    @org.junit.jupiter.api.Test
    void getEnglishLine() {
        String english_line = "hello, world!";
        Scenario scenario = new Scenario();
        scenario.setEnglishLine(english_line);
        assertEquals(scenario.getEnglishLine(), english_line);
    }

    @org.junit.jupiter.api.Test
    void getKoreanLine() {
        String korean_line = "안녕, 세상!";
        Scenario scenario = new Scenario();
        scenario.setKoreanLine(korean_line);
        assertEquals(scenario.getKoreanLine(), korean_line);
    }

    @org.junit.jupiter.api.Test
    void setSpeaker() {
        int speaker = 0;
        Scenario scenario = new Scenario(speaker);
        assertEquals(scenario.getSpeaker(), 0);
    }

    @org.junit.jupiter.api.Test
    void setEnglishLine() {
        String english_line = "hello, world!";
        Scenario scenario = new Scenario();
        scenario.setEnglishLine(english_line);
        assertEquals(scenario.getEnglishLine(), english_line);
    }

    @org.junit.jupiter.api.Test
    void setKoreanLine() {
        String korean_line = "안녕, 세상!";
        Scenario scenario = new Scenario();
        scenario.setKoreanLine(korean_line);
        assertEquals(scenario.getKoreanLine(), korean_line);
    }
}