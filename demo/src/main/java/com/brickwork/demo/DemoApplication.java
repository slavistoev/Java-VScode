/*******************************************************
 
 MentorMate's DevCamp Assignment #2
 @author Slavi Stoev <slavi.stoev97@gmail.com>
 @version 1.0 01/2021
 
 *******************************************************/

package com.brickwork.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
import java.lang.RuntimeException;

/**
 * Commenting should be done sparingly and code should
 * speak for itself. Excessive commenting is considered
 * a bad practice. 
 */

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		/**
		 * Usually you would catch checked exceptions here and make user 
		 * input valid data but since "No solution"(meaning invalid input)
		 * is desired output, we won't handle them
		 */
		BrickWork bw = new BrickWork(new SimpleLayingBricks(), 
										new PrettyPrint());
		bw.setNextBrickLayer();
		bw.printLayer();
	}

}

/**
 * Validity of user input should always be checked 
 * programatically. Here an exception wrapper is used
 * for a more convenient handling. 
 */
class NoSolutionException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "-1. No solution exists. ";

	/*
	 * Version number for deserialization to verify that 
	 * that the sender and receiver of a serialized object 
	 * have loaded classes for that object that are 
	 * compatible with respect to serialization
	 */
	private static final long serialVersionUID = 1;

	public NoSolutionException() {

		super();
	}

	public NoSolutionException(String excMessage) {

		super(DEFAULT_MESSAGE + excMessage);
	}

	public NoSolutionException(String excMessage, Throwable cause) {

		super(DEFAULT_MESSAGE + excMessage, cause);
	}
}

/**
 * Our main class that handles storing and changing of
 * the brick configuration. Strategy Design Pattern
 * has been used on printing and changing the brick 
 * layer for easier extension following the Open-closed
 * SOLID principle.
 */
class BrickWork {
    
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

/**
 * Implementing Strategy Design Pattern for 
 * printing brick layer for easier extension.
 */
interface PrintingMethod {

	public void printLayer(short[][] layer);
}

/**
 * Printing brick layer with no formatting.
 */
class SimplePrint implements PrintingMethod {

	public void printLayer(short[][] layer) {

		int width = layer.length;
		int length = layer[0].length;

		for (int row = 0; row < width; ++row) {
			for (int col = 0; col < length; ++col) {

				System.out.print(layer[row][col] + " ");
			}

			System.out.println("");
		}
	}
}

/**
 * Surrounding each brick of the layer with asterisk - `*`.
 * There is a single line of symbols between two bricks.
 */
class PrettyPrint implements PrintingMethod {

	private static final char DELIMITER = '*';
	private static final char BOUNDER = ' ';

	public void printLayer(short[][] layer) {

		int width = layer.length;
		int length = layer[0].length;

		short temp;
		short indent = getNumberOfDigits(width * length / 2);

		StringBuffer currRowDivider;
		String rowDivider = "*".repeat(length * (indent + 1) + 1);

		System.out.println(rowDivider);
		for (int row = 0; row < width; ++row) {

			currRowDivider = new StringBuffer(rowDivider);
			for (int col = 0; col < length; ++col) {

				temp = layer[row][col];
				if (col > 0 && temp == layer[row][col - 1]) {
					System.out.print(BOUNDER);
				}
				else {
					System.out.print(DELIMITER);
				}

				System.out.format("%" + indent + "d", temp);

				if (row < width - 1 && temp == layer[row + 1][col]) {

					bindBricks(currRowDivider, indent, col);
				}
			}

			System.out.println(DELIMITER);
			System.out.println(currRowDivider);
		}
	}

	private short getNumberOfDigits(int number) {

		short length = 0;
		int temp = 1;

		while (temp <= number) {
		    length++;
		    temp *= 10;
		}
		return length;
	}

	/**
	 * Erases the boundary in the horizontal divider
	 * so one brick is formed.
	 */
	private void bindBricks(StringBuffer rowDivider, 
					int indent, int position) {

		int startIndex = position * (indent + 1) + 1;
		int endIndex = startIndex + indent;

		rowDivider.replace(startIndex, endIndex, 
				String.valueOf(BOUNDER).repeat(indent));
	}
}

/**
 * Implementing Strategy Design Pattern for 
 * changing brick layer for easier extension.
 */
interface LayingBricksMethod {

	public void setNextBrickLayer(short[][] layer);
}

/**
 * Easiest brick-laying method. Viewing a brick layer
 * as sets of 2x2 and always making sure "no brick in it 
 * lies exactly on a brick from the first layer". Possibilities
 * without all permutations(by rotation) are 3: 
 * 			1)
 * 			* * * * *		* * * * *
 * 			*   *    		*		*
 * 			*   * * *	=> 	* * * * *
 * 			*   *			*		*
 * 			* * * * *		* * * * * * 
 * 			2)
 * 			* * * * *		* * * * *
 * 			*		*		*	*	*
 * 			* * * * *	=> 	*   *   *
 * 			*		*		*	*	*
 * 			* * * * *		* * * * * * 
 * 			3)
 * 			* * * * *		* * * * *		* * * * *
 * 			*	*	*		*		*		*	*	*
 * 			* * * * *	=> 	* * * * *  OR 	*   *   *
 * 			*	*	*		*		*		*	*	*
 * 			* * * * *		* * * * *		* * * * * 
 * We just check for a vertical or horizontal 
 * boundaries between bricks and lay the new ones
 * perpendiculary to a boundary.
 */
class SimpleLayingBricks implements LayingBricksMethod {

	public void setNextBrickLayer(short[][] layer) {

		int width = layer.length;
		int length = layer[0].length;

		short counter = 1;
		short temp;

		for (int row = 0; row < width; row += 2) {
			for (int col = 0; col < length; col += 2) {

				temp = layer[row][col];
				layer[row][col] = counter;

				if (temp != layer[row][col + 1] &&
						layer[row + 1][col] != layer[row + 1][col + 1]) {

					layer[row][col + 1] = counter;
					counter++;
					layer[row + 1][col] = counter;
				}
				else {
					layer[row + 1][col] = counter;
					counter++;
					layer[row][col + 1] = counter;
				}
				
				layer[row + 1][col + 1] = counter;
				counter++;
			}
		}
	}
}

/**
 * For implementing complex brick-laying patterns.
 */
class ComplexLayingBricks implements LayingBricksMethod {

	public void setNextBrickLayer(short[][] layer) {

		System.out.println("Complex laying of bricks " +
								"is not implemented yet!");
	}
}

