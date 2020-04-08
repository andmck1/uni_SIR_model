import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

class visualisation extends Frame {
	private BufferedImage foreground;
	private BufferedImage background;
	private int width;
	private int height;

	visualisation(int width, int height) {

		this.width = width;
		this.height = height;

		foreground = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setVisible(true);
		setSize(width, height + getInsets().top);
		addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {System.exit(0);}});
	}

	void set(int column, int row, Color color) {
		background.setRGB(column, row, color.getRGB());
	}

	void draw() {
		foreground.setData(background.getData());
		getGraphics().drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
	}

	void visualise(int[][] grid) {

		int W = this.width;
		int H = this.height;

		for (int col = 0; col < W; col++) {
			for (int row = 0; row < H; row++) {
				if (grid[col][row] == 0) {
					set(col, row, Color.ORANGE);
				}
				else if (grid[col][row] == 1) {
					set(col, row, Color.RED);
				}
				else {
					set(col, row, Color.BLUE);
				}				
			}
		}

		draw();
	}

	public void paint(Graphics graphics) {
		graphics.drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
	}
}