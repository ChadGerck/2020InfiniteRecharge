package org.usfirst.frc.team7327.robot.subsystems;

public class PowerCellTarget {
	PixyPacket PowerCell;
	double Width;
	double Height;
	double Area;
	double X;
	double angle;
	double distance;
	
	
	final double TARGET_X = 160;
	
	final double DEGREES_PER_PIXEL = 75.0 / 320.0;
	
	public PowerCellTarget(PixyPacket p1) {
		PowerCell = p1;

		// if (block1 == null && block2 != null)
		// 	block1 = block2;
		// else if (block1 != null && block2 == null)
		// 	block2 = block1;
		doMath();
	}
	
	// TODO: add some private functions to do math and stuff to convert data in blocks to distance and angle.
	private void doMath(){
		Width = (PowerCell.Width);
		Height = (PowerCell.Height);
		Area = Height * Width;
		X = (PowerCell.X) / 2;
		angle = (X - TARGET_X) * DEGREES_PER_PIXEL;
		distance = 106.83987509669 * Math.pow(.96060468112129, Height);
	}

	public double distance() {
		// return distance needed to drive to hang gear.
		return distance;
	}
	
	public double angle() {
		// return angle needed to turn robot to line up with gear peg
		return angle; //X distance in pixels converted to degrees
	}
	public String toString() {
		return "" +
	"Width: " + Width + 
	" Height: " + Height +
	" Area: " + Area +
	" X: " + X +
	" dpp: " + DEGREES_PER_PIXEL +
	" angle: " + angle +
	" distance: " + distance;
	}
}