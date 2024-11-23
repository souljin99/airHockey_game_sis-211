package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import game.components.Disk;
import game.components.Table;
import game.components.base.BaseDrawer;
import game.components.utils.GameConstans;

public class Game implements BaseDrawer {
    ArrayList<Disk> disks = new ArrayList<>(); // Mantener el uso de la lista de bolas
    Table table;
    Disk mallet1, mallet2, puck; // Referencias a las bolas específicas
    Boolean puckActive = false;    // Activa al colisionar con otra bola

    // Variables de control para las bolas izquierda y derecha
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private boolean wPressed, sPressed, aPressed, dPressed;

    public Game(Canvas canvas) {
        table = new Table();

        // Crear las bolas
        mallet1 = new Disk(50.0, table.getBoundHeight() / 2.0, 12);
        mallet2 = new Disk(table.getBoundWidth() - 50.0, table.getBoundHeight() / 2.0, 12);
        puck = new Disk(table.getBoundWidth() / 2.0, table.getBoundHeight() / 2.0, 12);

        // Configurar las propiedades de las bolas
        mallet1.setColor(Color.BLUE);
        mallet2.setColor(Color.RED);
        puck.setColor(Color.BLACK);

        mallet1.setBounds(table.getBoundX(), table.getBoundY(), table.getBoundWidth(), table.getBoundHeight());
        mallet2.setBounds(table.getBoundX(), table.getBoundY(), table.getBoundWidth(), table.getBoundHeight());
        puck.setBounds(table.getBoundX(), table.getBoundY(), table.getBoundWidth(), table.getBoundHeight());

        // Agregar bolas a la lista para mantener consistencia
        disks.add(mallet1);
        disks.add(mallet2);
        disks.add(puck);

        // Configurar entrada del teclado
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e);
            }
        });
        canvas.setFocusable(true);
    }

    @Override
    public void update() {
        // Actualizar las posiciones de las bolas controladas por el jugador
        updatePlayerControls();

        // Actualizar las bolas
        for (Disk disk : disks) {
            disk.update();
        }

        // Activar la bola central si colisiona con otra
        if (!puckActive) {
            detectAndActivateCenterBall();
        }

        // Detectar colisiones entre todas las bolas
        detectCollisions();
    }

    @Override
    public void draw(Graphics graphics) {
        // Dibujar la mesa
        table.draw(graphics);

        // Dibujar las bolas
        for (Disk disk : disks) {
            disk.draw(graphics);
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Controles para la bola izquierda (WASD)
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_D -> dPressed = true;

            // Controles para la bola derecha (Flechas)
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Controles para la bola izquierda (WASD)
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;

            // Controles para la bola derecha (Flechas)
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
    }

    private void updatePlayerControls() {
        // Movimiento para la bola izquierda
        if (wPressed) mallet1.setSpeedY(-5.0);
        if (sPressed) mallet1.setSpeedY(5.0);
        if (aPressed) mallet1.setSpeedX(-5.0);
        if (dPressed) mallet1.setSpeedX(5.0);

        // Movimiento para la bola derecha
        if (upPressed) mallet2.setSpeedY(-5.0);
        if (downPressed) mallet2.setSpeedY(5.0);
        if (leftPressed) mallet2.setSpeedX(-5.0);
        if (rightPressed) mallet2.setSpeedX(5.0);
    }

    private void detectAndActivateCenterBall() {
        for (Disk disk : disks) {
            if (disk != puck) {
                Double distance = Math.sqrt(Math.pow(puck.getX() - disk.getX(), 2)
                        + Math.pow(puck.getY() - disk.getY(), 2));
                if (distance <= puck.getRadius() + disk.getRadius()) {
                    puckActive = true;
                    break;
                }
            }
        }
    }

    private void detectCollisions() {
        for (Disk disk : disks) {
            for (Disk otherDisk : disks) {
                if (disk != otherDisk) {
                    Double distance = Math.sqrt(Math.pow(disk.getX() - otherDisk.getX(), 2)
                            + Math.pow(disk.getY() - otherDisk.getY(), 2));
                    if (distance <= disk.getRadius() + otherDisk.getRadius()) {
                        solveColision(disk, otherDisk);
                    }
                }
            }
        }
    }

    private void solveColision(Disk disk, Disk otherDisk) {
        // Resolver colisión entre bolas
        Double xDistance = otherDisk.getX() - disk.getX();
        Double yDistance = otherDisk.getY() - disk.getY();
        Double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        Double normalX = xDistance / distance;
        Double normalY = yDistance / distance;

        Double overlap = 0.5 * (disk.getRadius() + otherDisk.getRadius() - distance);
        disk.setX(disk.getX() - overlap * normalX);
        disk.setY(disk.getY() - overlap * normalY);
        otherDisk.setX(otherDisk.getX() + overlap * normalX);
        otherDisk.setY(otherDisk.getY() + overlap * normalY);
    }
}
