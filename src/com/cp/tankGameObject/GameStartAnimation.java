package com.cp.tankGameObject;

public class GameStartAnimation {
	private boolean isAnimationEnd = false;
	
	private int gameNameX;
	private int gameNameY;
	/**
	 * @return the gameNameX
	 */
	public int getGameNameX() {
		return gameNameX;
	}
	/**
	 * @param gameNameX the gameNameX to set
	 */
	public void setGameNameX(int gameNameX) {
		this.gameNameX = gameNameX;
	}
	/**
	 * @return the gameNameY
	 */
	public int getGameNameY() {
		return gameNameY;
	}
	/**
	 * @param gameNameY the gameNameY to set
	 */
	public void setGameNameY(int gameNameY) {
		this.gameNameY = gameNameY;
	}
	/**
	 * @return the isAnimationEnd
	 */
	public boolean isAnimationEnd() {
		return isAnimationEnd;
	}
	/**
	 * @param isAnimationEnd the isAnimationEnd to set
	 */
	public void setAnimationEnd(boolean isAnimationEnd) {
		this.isAnimationEnd = isAnimationEnd;
	}

}
