package main;

import printing.GridPrinter;

public class Test {
    public static void main(String[] args) {
	int numRows = Integer.parseInt(args[0]);
	int numCols = Integer.parseInt(args[1]);
	int cellHeight = Integer.parseInt(args[2]);
	int cellWidth = Integer.parseInt(args[3]);

	GridPrinter printer = new GridPrinter(numRows, numCols, cellHeight, cellWidth);

	printer.prepareScreen();
	printer.printDividers();

	try {
	    printer.printCell(0, 0, "testwrapexcess");
	    Thread.sleep(500);
	    printer.print("between cells");
	    Thread.sleep(500);
	    printer.printCell(3, 3, "diag");
	    Thread.sleep(500);
	    printer.print(" second");
	    Thread.sleep(500);
	    printer.printCell(7, 0, "left");
	    Thread.sleep(500);
	    printer.clearBelow();
	    Thread.sleep(500);
	    printer.printCell(0, 7, "top");
	    Thread.sleep(500);
	    printer.printCell(8, 12, "rand");
	    Thread.sleep(500);
	    printer.print("after clear ");
	    Thread.sleep(500);
	    printer.printCell(3, 3, "overwite");
	    Thread.sleep(500);
	    printer.print("and again");
	    Thread.sleep(500);
	    printer.printCell(0, 0, "new");
	    Thread.sleep(500);
	    printer.print("\u001b[1B");
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
