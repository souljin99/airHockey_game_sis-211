package gameproject.entities;


import java.awt.Color;
import java.awt.Graphics;

public class Goal {
    private int x, y;
    private int width, height;

    public Goal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public boolean isPuckInside(Puck puck) {
        int puckCenterX = puck.getX() + puck.getDiameter() / 2;
        int puckCenterY = puck.getY() + puck.getDiameter() / 2;

        return puckCenterX >= x && puckCenterX <= x + width &&
               puckCenterY >= y && puckCenterY <= y + height;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
