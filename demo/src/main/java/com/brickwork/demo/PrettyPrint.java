package com.brickwork.demo;

/**
 * Surrounding each brick of the layer with asterisk - `*`.
 * There is a single line of symbols between two bricks.
 */
public class PrettyPrint implements PrintingMethod {

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
