package game.components.utils;

import java.util.ArrayList;

import game.components.Disk;

public class InitGame {
	static private ArrayList<ArrayList<Disk>> splitArray(ArrayList<Disk> disks) {
		ArrayList<ArrayList<Disk>> result = new ArrayList<>();
		Integer startIndex = 0;
		Integer groupInteger = 1;
		while (startIndex < disks.size() && groupInteger < 6){
			Integer endIndex = Math.min(startIndex + groupInteger, disks.size());
			ArrayList<Disk> group = new ArrayList<>(disks.subList(startIndex, endIndex));
            result.add(group);
            startIndex = endIndex;
            groupInteger++;
		}
		return result;
	}
	static public void orderPosition(ArrayList<Disk> balls, Integer x, Integer y) {
        ArrayList<ArrayList<Disk>> result = InitGame.splitArray(balls);
        for (ArrayList<Disk> group : result) {
            Integer positionX = x;
            Integer positionY = y;
            orderLine(group, positionX, positionY);
            x += 24;
        }
    }
    static private void orderLine(ArrayList<Disk> group, Integer x, Integer y) {
        for (Disk ball : group) {
            ball.setX(x.doubleValue());
            ball.setY(y.doubleValue());
            y -= 12;
        }
    }
}
