package com.brickwork.demo;

/**
 * Implementing Strategy Design Pattern for 
 * changing brick layer for easier extension.
 */
public interface LayingBricksMethod {

	public void setNextBrickLayer(short[][] layer);
}
