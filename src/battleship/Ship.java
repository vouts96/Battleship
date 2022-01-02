package battleship;

import javafx.scene.paint.Color;

public class Ship {
	private int shipId;
	private String shipType;
	private Color shipColor;
	private int shipLength;
	private int shotPoints;
	private int sinkBonusPoints;
	
	private int shotsTaken;
	
	public Ship(int shipNum) {
		switch(shipNum) {
			case 1:
				this.shipId = 1;
				this.shipType = "Carrier";
				this.shipColor = Color.BLUE;
				this.shipLength = 5;
				this.shotPoints = 350;
				this.sinkBonusPoints = 1000;
				
				this.shotsTaken = 0;
				break;
			case 2:
				this.shipId = 2;
				this.shipType = "BattleShip";
				this.shipColor = Color.GRAY;
				this.shipLength = 4;
				this.shotPoints = 250;
				this.sinkBonusPoints = 500;
				
				this.shotsTaken = 0;
				break;
			case 3:
				this.shipId = 3;
				this.shipType = "Cruiser";
				this.shipColor = Color.ORANGE;
				this.shipLength = 3;
				this.shotPoints = 100;
				this.sinkBonusPoints = 250;
				
				this.shotsTaken = 0;
				break;
			case 4:
				this.shipId = 4;
				this.shipType = "Submarine";
				this.shipColor = Color.YELLOW;
				this.shipLength = 3;
				this.shotPoints = 100;
				this.sinkBonusPoints = 0;
				
				this.shotsTaken = 0;
				break;
			case 5:
				this.shipId = 5;
				this.shipType = "Destroyer";
				this.shipColor = Color.GREEN;
				this.shipLength = 2;
				this.shotPoints = 50;
				this.sinkBonusPoints = 0;
				
				this.shotsTaken = 0;
				break;
		}
	}
	
	public String getShipType(Ship ship) {
		return ship.shipType;
	}
	
	public Color getShipColor(Ship ship) {
		return ship.shipColor;
	}
	
	public int getShipLength(Ship ship) {
		return ship.shipLength;
	}
	
	public int getShotPoints(Ship ship) {
		return ship.shotPoints;
	}
	
	public int getSinkBonusPoints(Ship ship) {
		return ship.sinkBonusPoints;
	}
	
	public int getShipId(Ship ship) {
		return ship.shipId;
	}
	
	public void increaseShotsTaken() {
		this.shotsTaken++;
	}
	
	public int getShotsTaken() {
		return this.shotsTaken;
	}
	
	
	
	public boolean isUntouched() {
		if(this.shotsTaken == 0) return true;
		return false;
	}
	
	public boolean isActive() {
		if(this.shotsTaken < this.shipLength) return true;
		return false;
	}
	
	public boolean isSinked() {
		if(this.shotsTaken == this.shipLength) return true;
		return false;
	}
}
