package gameproject.entities;

import java.awt.Color;
import java.awt.Graphics;

public class Puck {
    private int x, y;
    private int diameter = 20;
    private int dx = 2, dy = 2;
    private boolean isActive = false;
	private int xSpeed;
	private int ySpeed;

    public Puck(int x, int y) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, diameter, diameter);
    }
    public void update(int canvasWidth, int canvasHeight) {
        if (!isActive) return; 

        x += xSpeed;
        y += ySpeed;
        if (x <= 0 || x >= canvasWidth - diameter) 
        	xSpeed = -xSpeed;
        if (y <= 0 || y >= canvasHeight - diameter) 
        	ySpeed = -ySpeed;
    }

    public void setSpeed(int dx, int dy) {
        double magnitude = Math.hypot(dx, dy);
        if (magnitude == 0) return;

        this.xSpeed = (int) (dx / magnitude * 5);
        this.ySpeed = (int) (dy / magnitude * 5);
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
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
    public int getCenterX() {
        return x + diameter / 2;
    }

    public int getCenterY() {
        return y + diameter / 2;
    }

}
