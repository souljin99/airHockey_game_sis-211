package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import game.components.utils.GameConstans;
import loaders.Assets;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements Runnable {
	private Canvas canvas;
	private Game game;
	private BufferStrategy bufferStrategy;
	private Boolean isRunning;
	private Thread thread;
	private Graphics graphics;
	private Integer AVERAGE_FRAMES=0;
	private double delta;
	private Integer FPS = 60;
    private double targetTime = 1000000000 / FPS;
	
	public MainWindow() {
		setTitle("Hockey de aire");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
        setVisible(true);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(GameConstans.WIDTH.getValue(), GameConstans.HEIGHT.getValue()));
        canvas.setMaximumSize(new Dimension(GameConstans.WIDTH.getValue(), GameConstans.HEIGHT.getValue()));
        canvas.setMinimumSize(new Dimension(GameConstans.WIDTH.getValue(), GameConstans.HEIGHT.getValue()));
        canvas.setLocation(new Point(0, 0));
        canvas.setFocusable(true);
        game = new Game(canvas);
        this.add(canvas);
	}
	public void start() {
		canvas.createBufferStrategy(3);
		bufferStrategy = canvas.getBufferStrategy();
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	public void stop() {
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	private void draw() {
		bufferStrategy = canvas.getBufferStrategy();
		if (bufferStrategy==null) {
			canvas.createBufferStrategy(3);
			return;
		}
		graphics =bufferStrategy.getDrawGraphics();
		graphics.clearRect(0, 0, GameConstans.WIDTH.getValue(), GameConstans.HEIGHT.getValue());
		game.draw(graphics);
		graphics.setColor(Color.BLACK);
		graphics.drawString("FPS: " + AVERAGE_FRAMES, 10, 10);
		graphics.dispose();
		bufferStrategy.show();
	}
	
	private void update() {
		game.update();
	}
	
	private void loadAssets() {
		Assets.init();
	}
	@Override
	public void run() {
		long now = 0;
		long lastTime = System.nanoTime();
		delta = 0;
		Integer frames = 0;
		long time = 0;
		loadAssets();
		while(isRunning) {
			now = System.nanoTime();
			delta += (now - lastTime) / targetTime;
			time += (now - lastTime);
			lastTime = now;
			if(delta >= 1) {
				update();
				draw();
				delta--;
				frames++;
			}
			if(time >= 1000000000) {
				AVERAGE_FRAMES = frames;
				frames = 0;
				time = 0;
			}
		}
		stop();
	}
	
}
