package com.brickwork.demo;

/**
 * Implementing Strategy Design Pattern for 
 * printing brick layer for easier extension.
 */
public interface PrintingMethod {

	public void printLayer(short[][] layer);
}
