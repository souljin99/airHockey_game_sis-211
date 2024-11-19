package gameproject.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Mallet {
    private int x, y;
    private final int diameter = 40;
    private Color color;

    public Mallet(int x, int y, Color color) {
    	this.color = color;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
    public int getX() { 
    	return x;
    }
    public int getY() {
    	return y; 
    }
    public void setX(int x) { 
    	this.x = x; 
    }
    public void setY(int y) { 
    	this.y = y;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
	public int getDiameter() {
		return diameter;
	}
	public int getCenterX() {
	    return x + diameter / 2;
	}

	public int getCenterY() {
	    return y + diameter / 2;
	}

}
