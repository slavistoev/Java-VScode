/*******************************************************
 
 MentorMate's DevCamp Assignment #2
 @author Slavi Stoev <slavi.stoev97@gmail.com>
 @version 1.0 01/2021
 
 *******************************************************/

package com.brickwork.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

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