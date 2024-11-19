package gameproject.game;


import javax.swing.JFrame;

public class AirHockeyGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Air Hockey Game");
        Game canvas = new Game();
        
        frame.add(canvas);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

