package com.brickwork.demo;

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
public class SimpleLayingBricks implements LayingBricksMethod {

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
