package loaders;

import java.awt.image.BufferedImage;

public class Assets {
	public static BufferedImage table;
	public static void init() {
		table = new LoaderImages().loadImage("C:\\Users\\narco\\eclipse-workspace\\segundoparcial\\airHockey\\resources\\textures\\mesa.png");
	}
}
