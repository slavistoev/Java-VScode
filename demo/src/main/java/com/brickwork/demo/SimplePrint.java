package com.brickwork.demo;

/**
 * Printing brick layer with no formatting.
 */
public class SimplePrint implements PrintingMethod {

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
