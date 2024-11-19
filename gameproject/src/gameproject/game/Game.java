package gameproject.game;


import gameproject.entities.Puck;
import gameproject.entities.Goal;
import gameproject.entities.Mallet;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable,KeyListener {
    private Puck puck;
    private Mallet mallet1, mallet2;
    private Image backgroundImage;
    private Thread gameThread;

    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean upPressed, leftPressed, downPressed, rightPressed;
    
    private Goal goal1, goal2;


    public Game() {

        setSize(new Dimension(800, 400));

        try {
        	backgroundImage = ImageIO.read(new File("C:/Users/narco/eclipse-workspace/segundoparcial/gameproject/resources/images/mesa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        puck = new Puck(390, 190);
        mallet1 = new Mallet(70, 180, Color.BLUE);
        mallet2 = new Mallet(690, 180, Color.RED);
        goal1 = new Goal(0, getHeight() / 2 - 50, 20, 100);
        goal2 = new Goal(getWidth() - 20, getHeight() / 2 - 50, 20, 100);
        
        addKeyListener(this);
        setFocusable(true);
        
        gameThread = new Thread(this);
        gameThread.start();
        
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        puck.draw(g);
        mallet1.draw(g);
        mallet2.draw(g);
        goal1.draw(g);
        goal2.draw(g);
    }
    public void run() {
        while (true) {
            puck.update(getWidth(), getHeight());
            updateMallets();
            checkPuckCollision();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateMallets() {
    	if (wPressed && mallet1.getY() > 0)
            mallet1.setPosition(mallet1.getX(), mallet1.getY() - 5);
        if (sPressed && mallet1.getY() < getHeight() - mallet1.getDiameter())
            mallet1.setPosition(mallet1.getX(), mallet1.getY() + 5);
        if (aPressed && mallet1.getX() > 0)
            mallet1.setPosition(mallet1.getX() - 5, mallet1.getY());
        if (dPressed && mallet1.getX() < getWidth() / 2 - mallet1.getDiameter())
            mallet1.setPosition(mallet1.getX() + 5, mallet1.getY());

        if (upPressed && mallet2.getY() > 0)
            mallet2.setPosition(mallet2.getX(), mallet2.getY() - 5);
        if (downPressed && mallet2.getY() < getHeight() - mallet2.getDiameter())
            mallet2.setPosition(mallet2.getX(), mallet2.getY() + 5);
        if (leftPressed && mallet2.getX() > getWidth() / 2)
            mallet2.setPosition(mallet2.getX() - 5, mallet2.getY());
        if (rightPressed && mallet2.getX() < getWidth() - mallet2.getDiameter())
            mallet2.setPosition(mallet2.getX() + 5, mallet2.getY());
    }

    private void checkPuckCollision() {
    	if (goal1.isPuckInside(puck)) {
            System.out.println("THe player 2 has scored a goal.");
            resetPuck();
        }

        if (goal2.isPuckInside(puck)) {
            System.out.println("The Player 1 has scored a goal.");
            resetPuck();
        }
        if (Math.hypot(puck.getCenterX() - mallet1.getCenterX(), puck.getCenterY() - mallet1.getCenterY()) 
                < puck.getDiameter() / 2 + mallet1.getDiameter() / 2) {
            puck.setActive(true);
            puck.setSpeed(puck.getCenterX() - mallet1.getCenterX(), puck.getCenterY() - mallet1.getCenterY());
        }

        if (Math.hypot(puck.getCenterX() - mallet2.getCenterX(), puck.getCenterY() - mallet2.getCenterY()) 
                < puck.getDiameter() / 2 + mallet2.getDiameter() / 2) {
            puck.setActive(true);
            puck.setSpeed(puck.getCenterX() - mallet2.getCenterX(), puck.getCenterY() - mallet2.getCenterY());
        }
    }
    private void resetPuck() {
        puck.setPosition(390, 190);
        puck.setSpeed(0, 0);
        puck.setActive(false);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_D -> dPressed = true;

            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_D -> dPressed = false;

            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}