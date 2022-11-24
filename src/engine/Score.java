package engine;

import java.util.List;

/**
 * Implements a high score record.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Score implements Comparable<Score> {

	/** Stage */
	private int stage;
	/** Score points. */
	private int score;
	/** Killed Enemies */
	private int killed;
	/** Shooted Bullets */
	private int bullets;
	/** Accuracy Score. */
	private float accuracy;

	/**
	 * Constructor.
	 *
	 * @param stage
	 *            Player Stage.
	 * @param score
	 *            Player scores.
	 * @param killed
	 *            Killed Enemies.
	 * @param bullets
	 *            Shooted Bullets.
	 * @param accuracy
	 *            Accuracy Score.
	 */
	public Score(final int stage, final int score, final int killed, final int bullets, final float accuracy) {
		this.stage = stage;
		this.score = score;
		this.killed = killed;
		this.bullets = bullets;
		this.accuracy = accuracy;
	}

	/**
	 * Getter for the stage.
	 *
	 * @return Stage.
	 */
	public final int getStage() { return this.stage; }

	/**
	 * Getter for the player's score.
	 *
	 * @return High score.
	 */
	public final int getScore() {
		return this.score;
	}


	/**
	 * Getter for the Killed Enemies.
	 *
	 * @return Killed Enemies.
	 */
	public final int getKilled() { return this.killed; }

	/**
	 * Getter for the Bullets.
	 *
	 * @return Bullets.
	 */
	public final int getBullets() { return this.bullets; }

	/**
	 * Getter for the Accuracy.
	 *
	 * @return Accuracy.
	 */
	public final float getAccuracy() { return this.accuracy; }

	/**
	 * Orders the scores descending by score.
	 *
	 * @param score
	 *            Score to compare the current one with.
	 * @return Comparison between the two scores. Positive if the current one is
	 *         smaller, positive if its bigger, zero if its the same.
	 *         정렬 1순위 : 스테이지 (stage), 2순위 : 점수 (score)
	 */
	@Override
	public final int compareTo(final Score score) {

		int comparison_score = this.score < score.getScore() ? 1 : this.score > score.getScore() ? -1 : 0;
		int comparison_stage = this.stage < score.getStage() ? -1 : this.stage > score.getStage() ? 1 : comparison_score;

		return comparison_stage;
	}

	/**
	 * Put score in selected stage using this method.
	 * @param highScores
	 */
	public static final List<Score> UpdateScore(final List<Score> highScores, Score newScore) {
		for(int i = 0; i < highScores.size(); i++) {
			if(highScores.get(i).getStage() == newScore.getStage()) {
				if(highScores.get(i).getScore() < newScore.getScore()) {
					highScores.set(i, newScore);
					return highScores;
				} else {
					return null;
				}
			}
		}
		return null;
	}
}