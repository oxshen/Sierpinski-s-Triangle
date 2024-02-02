// Owen Shen
// Creates image of Sierpinski's triangle based on an inputted number of levels.

import java.awt.*;
import javax.swing.*;
import java.lang.Math;

class fractalsShells {
	public static void main(String[] args) {
		// This is the code used to create the image you see on the panel.

		DrawingPanel panel = new DrawingPanel(300, 300); // The Canvas
		panel.setBackground(Color.WHITE);
		Graphics g = panel.getGraphics();// Create the paintbrush

		// Everything else in main is for adding aspects to the canvas

		g.setColor(Color.BLACK);// Setting the paintbrush to the color BLACK

		// Now I need to assign the points of my equilateral triangle.
		Point bottomLeft = new Point(50, 250);
		Point topMiddle = new Point(150, 50);
		Point bottomRight = new Point(250, 250);

		drawTriangle(g, bottomLeft, topMiddle, bottomRight);
		int input = input();
		snowflake(input, g, bottomLeft, bottomRight);
		snowflake(input, g, topMiddle, bottomLeft);
		snowflake(input, g, bottomRight, topMiddle);

		// sierpinski(input, g, bottomLeft, topMiddle, bottomRight);
	}

	public static void drawTriangle(Graphics g, Point p1, Point p2, Point p3) {
		/*
		  Since the Graphics class doesn't contain a drawTriangle method,
		  I decided to create my own using the Polygon class, which will
		  basically trace the shape starting from the first point to the
		  next point and so on and so on. It will then close the shape by
		  tracing from the last point to the first point.
		 */

		// If you want to create a custom shape, edit this function to fit the
		// your design.
		Polygon p = new Polygon();
		p.addPoint(p1.x, p1.y);
		p.addPoint(p2.x, p2.y);
		p.addPoint(p3.x, p3.y);
		g.drawPolygon(p);
		// or you can use: g.drawPolygon(p);

	}

	public static Point midpoint(Point p1, Point p2) {
		// This method gives the midpoint of 2 coordinates

		int midX = (p1.x + p2.x) / 2;
		int midY = (p1.y + p2.y) / 2;
		Point mid = new Point(midX, midY);
		return mid;
	}

	public static int input() {
		int count = 0;
		count = Integer.parseInt(JOptionPane.showInputDialog("How many levels of sierpinski's triangle?")); // Creates
																											// popup for
																											// level
		return count;
	}

	public static void sierpinski(int count, Graphics g, Point p1, Point p2, Point p3) // p1 is bottom left vertex, p2
																						// top vertex. p3 bottom right
																						// vertex
	{
		if (count == 0) {
		} else { // Getting mid point from each vertex
			Point left = midpoint(p1, p2);
			Point right = midpoint(p2, p3);
			Point bottom = midpoint(p3, p1);

			drawTriangle(g, left, right, bottom);

			sierpinski(count - 1, g, left, p2, right); // Top triangle
			sierpinski(count - 1, g, p1, left, bottom); // Bottom left triangle
			sierpinski(count - 1, g, bottom, right, p3); // Bottom right triangle
		}
	}

	public static void snowflake(int count, Graphics g, Point p1, Point p2) {
		if (count == 0) {
			g.drawLine(p1.x, p1.y, p2.x, p2.y); // Draw line between given points
		} else {
			int deltaX = p2.x - p1.x;
			int deltaY = p2.y - p1.y;

			Point oneThird = new Point(p1.x + deltaX / 3, p1.y + deltaY / 3); // A point that is 1/3 of the way from p1 to p2
			Point twoThird = new Point(p1.x + deltaX * 2 / 3, p1.y + deltaY * 2 / 3); // A point that is 2/3 of the way from p1 to p2
			Point vertex = new Point( // Uses a formula to calculate that third vertex (this is the thing that took the entire weekend) 
					(int) (oneThird.x + (twoThird.x - oneThird.x) * Math.cos(Math.toRadians(60)) - (twoThird.y - oneThird.y) * Math.sin(Math.toRadians(60))),
					(int) (oneThird.y + (twoThird.x - oneThird.x) * Math.sin(Math.toRadians(60)) + (twoThird.y - oneThird.y) * Math.cos(Math.toRadians(60))));

			g.setColor(Color.WHITE);
			g.drawLine(p1.x, p1.y, p2.x, p2.y); // Erases old line
			g.setColor(Color.BLACK);

			snowflake(count - 1, g, p1, oneThird); // Recursively calls 
			snowflake(count - 1, g, oneThird, vertex);
			snowflake(count - 1, g, vertex, twoThird);
			snowflake(count - 1, g, twoThird, p2);
		}
	}
}
