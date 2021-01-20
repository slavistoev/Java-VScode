package com.brickwork.demo;

import java.util.Scanner;

/**
 * Our main class that handles storing and changing of
 * the brick configuration. Strategy Design Pattern
 * has been used on printing and changing the brick 
 * layer for easier extension following the Open-closed
 * SOLID principle.
 */
public class BrickWork {
    
    private static final short MIN_DIMENSION_SIZE = 2;
	private static final short MAX_DIMENSION_SIZE = 100;

	private short[][] brickLayer;
	
	private PrintingMethod printingMethod;
	private LayingBricksMethod layingBricksMethod;
    
	public BrickWork(LayingBricksMethod layingBricksMethod, 
						PrintingMethod printingMethod) {
		
		this.setLayingBricksMethod(layingBricksMethod);;
		this.setPrintingMethod(printingMethod);;
        setBrickLayer();
	}

	public void setLayingBricksMethod(LayingBricksMethod layingBricksMethod) {

		this.layingBricksMethod = layingBricksMethod;
	}

	public void setPrintingMethod(PrintingMethod printingMethod) {

		this.printingMethod = printingMethod;
	}
	
	/**
	 * Handling and validating user input.
	 */
    public void setBrickLayer() {
		
		Scanner in = new Scanner(System.in);
		int width = getNumber(in);
		int length = getNumber(in);
		
        if (!checkDimension(width) || !checkDimension(length)
				|| width > length) {

			in.close();
			throw new NoSolutionException(
				"Incorrect dimensions input! Make sure 2 <= N <= 100," +
				" 2 <= M <= 100, N and M are even, N <= M!");
		}
				
		this.brickLayer = new short[width][length];
		
		/**
		 * Brick input is tested for correct array size and brick size
		 * Brick input is not tested for correct brick numbers since
		 * we're only using them here structurally to represent bricks.
		 * e.g. [-5 -5] is still a correct brick.
		 */
		for (int row = 0; row < width; ++row) {
			for (int col = 0; col < length; ++col) {

				this.brickLayer[row][col] = getNumber(in);
			}
		}
		
		/*
		 * Not checking for longer or shorter input. This is impossible 
		 * without a delimeter like a symbol for end of input. Checking 
		 * columns is possible by taking a whole row as a string and 
		 * then splitting it, but don't know how rows could be checked.
		 */
		
		in.close();
		
		checkBricks();
	}
			
	public void setNextBrickLayer() {

		this.layingBricksMethod.setNextBrickLayer(this.brickLayer);
	}
	
	private boolean checkDimension(int dimension) {

		return dimension >= MIN_DIMENSION_SIZE && 
			dimension <= MAX_DIMENSION_SIZE && 
			dimension % 2 == 0;
	}
	
	/**
	 * Validating no bricks span three rows or columns.
	 */
	private void checkBricks() {

		short temp;
		int width = this.brickLayer.length;
		int length = this.brickLayer[0].length;
		
		for (int row = 0; row < width; ++row) {
			for (int col = 1; col < length - 1; ++col) {
				
				temp = this.brickLayer[row][col];
				
				if (this.brickLayer[row][col - 1] == temp &&
						this.brickLayer[row][col + 1] == temp) {
					
					throw new NoSolutionException("Bricks " +
								 "cannot span three or more columns!");
				}
			}
		}
		
		for (int row = 1; row < width - 1; ++row) {
			for (int col = 0; col < length; ++col) {
				
				temp = this.brickLayer[row][col];
				
				if (this.brickLayer[row - 1][col] == temp &&
						this.brickLayer[row + 1][col] == temp) {
					
					throw new NoSolutionException("Bricks " +
									"cannot span three or more rows!");
				}
			}
		}
	}

	private short getNumber(Scanner in) {

		if (in.hasNextShort()) {

			return in.nextShort();
		}

		in.close();
		throw new NoSolutionException("Invalid input! " +
							"Make sure you enter valid numbers!");
	}
	
	public void printLayer() {

		this.printingMethod.printLayer(this.brickLayer);
	}
}
