package engine;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.*;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import engine.DrawManager.SpriteType;

/**
 * Manages files used in the application.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class FileManager {

	/**
	 * Singleton instance of the class.
	 */
	private static FileManager instance;
	/**
	 * Application logger.
	 */
	private static Logger logger;
	/**
	 * Max number of high scores.
	 */
	private static final int MAX_SCORES = 8;

	/**
	 * private constructor.
	 */
	private FileManager() {
		logger = Core.getLogger();
	}

	/**
	 * Returns shared instance of FileManager.
	 *
	 * @return Shared instance of FileManager.
	 */
	protected static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	/**
	 * Loads sprites from disk.
	 *
	 * @param spriteMap Mapping of sprite type and empty boolean matrix that will
	 *                  contain the image.
	 * @throws IOException In case of loading problems.
	 */
	public void loadSprite(final Map<SpriteType, boolean[][]> spriteMap)
			throws IOException {
		InputStream inputStream = null;

		try {
			inputStream = DrawManager.class.getClassLoader()
					.getResourceAsStream("graphics");
			char c;

			// Sprite loading.
			for (Map.Entry<SpriteType, boolean[][]> sprite : spriteMap
					.entrySet()) {
				for (int i = 0; i < sprite.getValue().length; i++)
					for (int j = 0; j < sprite.getValue()[i].length; j++) {
						do
							c = (char) inputStream.read();
						while (c != '0' && c != '1');

						if (c == '1')
							sprite.getValue()[i][j] = true;
						else
							sprite.getValue()[i][j] = false;
					}
				logger.fine("Sprite " + sprite.getKey() + " loaded.");
			}
			if (inputStream != null)
				inputStream.close();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	/**
	 * Loads a font of a given size.
	 *
	 * @param size Point size of the font.
	 * @return New font.
	 * @throws IOException         In case of loading problems.
	 * @throws FontFormatException In case of incorrect font format.
	 */
	public Font loadFont(final float size) throws IOException,
			FontFormatException {
		InputStream inputStream = null;
		Font font;

		try {
			// Font loading.
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("font.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(
					size);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return font;
	}

	/**
	 * Returns the application default scores if there is no user high scores
	 * file.
	 *
	 * @return Default high scores.
	 * @throws IOException In case of loading problems.
	 */
	private List<Score> loadDefaultHighScores() {
		List<Score> highScores = new ArrayList<Score>();

		int num_of_stage = 8;

		for(int i = 1; i < num_of_stage+1; i++) {
			highScores.add(new Score(i ,  0, 0, 0, 0));
		}

		return highScores;
	}

	/**
	 * Loads high scores from file, and returns a sorted list of pairs score -
	 * value.
	 *
	 * @return Sorted list of scores - players.
	 * @throws IOException In case of loading problems.
	 */
	public List<Score> loadHighScores() throws IOException {

		List<Score> highScores = new ArrayList<Score>();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);
			inputStream = new FileInputStream(scoresFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading high score in each stage.");

			Score highScore = null;

			int padding = 1;
			int num_of_stage = 1 + padding;

			while (true) {
				String stage = bufferedReader.readLine();
				String score = bufferedReader.readLine();
				String killed = bufferedReader.readLine();
				String bullets = bufferedReader.readLine();
				String accuracy = bufferedReader.readLine();

				if((stage == null) || (score == null) || (killed == null) || (bullets == null) || (accuracy == null)) break;

				highScore = new Score(Integer.parseInt(stage), Integer.parseInt(score), Integer.parseInt(killed), Integer.parseInt(bullets), Float.parseFloat(accuracy));
				highScores.add(highScore);

//				if(Integer.parseInt(stage) != num_of_stage++) {
//					throw new InputMismatchException();
//				}

			}

//			if(num_of_stage - 1 - padding != MAX_SCORES) throw new InputMismatchException();

		} catch (FileNotFoundException e) {
			// loads default if there's no user scores.
			logger.info("Loading default high scores.");
			highScores = loadDefaultHighScores();
			saveHighScores(highScores);

		}

		finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		//Collections.sort(highScores);
		return highScores;
	}

	/**
	 * Saves user high scores to disk.
	 *
	 * @param highScores High scores to save.
	 * @throws IOException In case of loading problems.
	 */
	public void saveHighScores(final List<Score> highScores)
			throws IOException {
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);

			if (!scoresFile.exists())
				scoresFile.createNewFile();

			outputStream = new FileOutputStream(scoresFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));

			logger.info("Saving user high scores.");

			// Saves 10 or less scores.
			int savedCount = 0;

			for (Score score : highScores) {
				if (savedCount >= MAX_SCORES)
					break;
				bufferedWriter.write(Integer.toString(score.getStage()));    // stage
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getScore()));    // score
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getKilled()));    // killed
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getBullets()));    // bullets
				bufferedWriter.newLine();
				bufferedWriter.write(Float.toString(score.getAccuracy()));    // accuracy
				bufferedWriter.newLine();
				savedCount++;
			}
		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}


	public BufferedImage loadImage(String name) throws IOException {
		InputStream inputStream = null;
		BufferedImage ret;
		try {
			// Font loading.
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream(name);
			ret = ImageIO.read(inputStream);

		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return ret;
	}

	/**
	 * Get scenario list for selected stage
	 * @param stage
	 * @return List of Scenario that have the information of korean lines, english lines, and speaker
	 */
	public Scenario[] loadScenario(int chapter, int stage) throws IOException {
		Scenario[] scenarios = null;
		InputStream inputStream = null;

		try {
			// Scenario loading.
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("scenario" + chapter + ".txt");

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			boolean bStage = false;

			while((line = bufferedReader.readLine()) != null) {
				// if the first line with star is same to chapter that we find
				if(line.contains("*")) {
					if(line.substring(1).equals(Integer.toString(stage))) {
						bStage = true;
						line = bufferedReader.readLine();
						int length = Integer.parseInt(line);
						scenarios = new Scenario[length];
						for(int i = 0; i < length; i++) {
							scenarios[i] = new Scenario();
						}
					} else {
						bStage = false;
					}
				} else {
					// if bStage is true, you can load the script of selected stage.
					if(bStage) {
						String datas[] = line.split(":");
						String index_datas[] = datas[0].split("/");
						String line_data = datas[1];

						int index = Integer.parseInt(index_datas[0]) - 1;
						int speaker = Integer.parseInt(index_datas[1]);
						String lang = index_datas[2];

						scenarios[index].setSpeaker(speaker);
						if(lang.equals("kor")) {
							scenarios[index].setKoreanLine(line_data);
						} else if(lang.equals("eng")) {
							scenarios[index].setEnglishLine(line_data);
						}
					}
				}
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return scenarios;
	}
}