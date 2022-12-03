package engine;

public class Scenario {
    public static String speaker_list[] = new String[] { "Narration", "Gwan-woo", "Aliens", "Boss" };
    private int speaker;
    private String eng_line;
    private String kor_line;

    public Scenario() {

    }

    public Scenario(int speaker) {
        this.speaker = speaker;
    }

    public int getSpeaker() {
        return speaker;
    }

    public String getEnglishLine() {
        return eng_line;
    }

    public String getKoreanLine() {
        return kor_line;
    }

    public void setSpeaker(int speaker) {
        this.speaker = speaker;
    }

    public void setEnglishLine(String eng_line) {
        this.eng_line = eng_line;
    }

    public void setKoreanLine(String kor_line) {
        this.kor_line = kor_line;
    }
}
