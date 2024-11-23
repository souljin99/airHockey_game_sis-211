package game.components.utils;

public enum GameConstans {
	PUCK_INIT_POSITION_X(400),
	PUCK_INIT_POSITION_Y(300),
	MAZE_INIT_POSITION_X(20),
	MAZE_INIT_POSITION_Y(300),
	
	WIDTH(800),
	HEIGHT(600);
	
	private Integer value;
	
	GameConstans(Integer value) {
        this.value = value;
    }
	public Integer getValue() {
        return value;
    }
}
