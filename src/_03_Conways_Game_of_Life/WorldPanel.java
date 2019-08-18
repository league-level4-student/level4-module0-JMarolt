package _03_Conways_Game_of_Life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private int cellsPerRow;
	private int cellSize;

	private Timer timer;

	// 1. Create a 2D array of Cells. Do not initialize it.
	Cell[][] cells;

	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;

		// 2. Calculate the cell size.
		cellSize = w / cellsPerRow;
		// 3. Initialize the cell array to the appropriate size.
		cells = new Cell[cellsPerRow][cellsPerRow];
		// 3. Iterate through the array and initialize each cell.
		// Don't forget to consider the cell's dimensions when
		// passing in the location.
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				cells[i][k] = new Cell(i * cellSize, k * cellSize, cellSize);
			}
		}

	}

	public void randomizeCells() {
		// 4. Iterate through each cell and randomly set each
		// cell's isAlive memeber to true of false
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				int rand = (int) (Math.random() * 2) + 1;
				if (rand == 2) {
					cells[i][k].isAlive = false;
				} else {
					cells[i][k].isAlive = true;
				}
			}
		}
		repaint();
	}

	public void clearCells() {
		// 5. Iterate through the cells and set them all to dead.
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				cells[i][k].isAlive = false;
			}
		}
		repaint();
	}

	public void startAnimation() {
		timer.start();
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {
		// 6. Iterate through the cells and draw them all
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				cells[i][k].draw(g);
			}
		}

		// draws grid
		for (int i = 0; i < cellSize * 5; i++) {
			g.setColor(Color.BLACK);
			g.drawLine(i * cellSize, 0, i * cellSize, getHeight());
			g.drawLine(0, i * cellSize, getWidth(), i * cellSize);
		}

	}

	// advances world one step
	public void step() {
		// 7. iterate through cells and fill in the livingNeighbors array
		// . using the getLivingNeighbors method.
		int[][] livingNeighbors = new int[cellsPerRow][cellsPerRow];
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				livingNeighbors[i][k] = getLivingNeighbors(cells[i][k].getX(), cells[i][k].getY());
			}
		}

		// 8. check if each cell should live or die
		for (int i = 0; i < cells.length; i++) {
			for (int k = 0; k < cells[0].length; k++) {
				cells[i][k].liveOrDie(livingNeighbors[i][k]);
			}
		}

		repaint();
	}

	// 9. Complete the method.
	// It returns an int of 8 or less based on how many
	// living neighbors there are of the
	// cell identified by x and y

	public int getLivingNeighbors(int x, int y) {
		
		int num = 0;
		
		int ax = x/10;
		int ay = y/10;
		
		if(ax - 1 > 0 && ay - 1 > 0) {
			if(cells[ax - 1][ay - 1].isAlive == true) {
				num++;
			}
		}
		if(ay - 1 > 0) {
			if(cells[ax][ay - 1].isAlive == true) {
				num++;
			}
		}
		if(ax + 1 < 50 && ay - 1 > 0) {
			if(cells[ax + 1][ay - 1].isAlive == true) {
				num++;
			}
		}
		if(ax + 1 < 50) {
			if(cells[ax + 1][ay].isAlive == true) {
				num++;
			}
		}
		if(ax + 1 < 50 && ay + 1 < 50) {
			if(cells[ax + 1][ay + 1].isAlive == true) {
				num++;
			}
		}
		if(ay + 1 < 50) {
			if(cells[ax][ay + 1].isAlive == true) {
				num++;
			}
		}
		if(ax - 1 > 0 && ay + 1 < 50) {
			if(cells[ax - 1][ay + 1].isAlive == true) {
				num++;
			}
		}
		if(ax - 1 > 0) {
			if(cells[ax - 1][ay].isAlive == true) {
				num++;
			}
		}

		return num;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 10. Use e.getX() and e.getY() to determine
		// which cell is clicked. Then toggle
		// the isAlive variable for that cell.

		int x = e.getX();
		int y = e.getY();
		
		for(int i = 0; i < cells.length; i++) {
			for(int k = 0; k < cells[0].length; k++) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if((x >= (cells[i][k].getX()) && x < cells[i + 1][k].getX()) && (y >= (cells[i][k].getY()) && (y < cells[i][k + 1].getY()))) {
						cells[i][k].isAlive = true;
					}
				}
			}
		}
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}
}
