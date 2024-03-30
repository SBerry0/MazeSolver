/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// MazeSolver by Sohum Berry

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Create Stack to reverse order easily
        Stack<MazeCell> soln = new Stack<>();
        MazeCell currentCell = maze.getEndCell();
        // Add all of the parents of the final cell until there aren't any (meets the start cell)
        while (currentCell != null) {
            soln.add(currentCell);
            currentCell = currentCell.getParent();
        }
        ArrayList<MazeCell> out = new ArrayList<>();
        // Store size to make sure it doesn't change while the loop is running
        int size = soln.size();
        for (int i = 0; i < size; i++) {
            out.add(soln.pop());
        }
        // Return ArrayList of cells that are on the path of the solution
        return out;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Create stack for LIFO ordering of the cells
        // So that other options only get searched after the rest of a path is found
        Stack<MazeCell> possibilities = new Stack<>();
        MazeCell currentCell = null;
        possibilities.add(maze.getStartCell());
        // While the next possibility isn't the final destination....
        while (!(possibilities.peek().getCol() == maze.getEndCell().getCol()
                && possibilities.peek().getRow() == maze.getEndCell().getRow())) {
            currentCell = possibilities.pop();
            // Mark the cell as explored
            currentCell.setExplored(true);
            // North
            if (maze.isValidCell(currentCell.getRow() - 1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() - 1, currentCell.getCol()).setParent(currentCell);
                possibilities.push(maze.getCell(currentCell.getRow() - 1, currentCell.getCol()));
            }
            // East
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() + 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() + 1).setParent(currentCell);
                possibilities.push(maze.getCell(currentCell.getRow(), currentCell.getCol() + 1));
            }
            // South
            if (maze.isValidCell(currentCell.getRow()+1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() + 1, currentCell.getCol()).setParent(currentCell);
                possibilities.push(maze.getCell(currentCell.getRow() + 1, currentCell.getCol()));
            }
            // West
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() - 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() - 1).setParent(currentCell);
                possibilities.push(maze.getCell(currentCell.getRow(), currentCell.getCol() - 1));
            }
        }
        maze.getEndCell().setParent(currentCell);
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Create a queue so that the options for other cells are found "simultaneously"
        Queue<MazeCell> possibilities = new LinkedList<>();
        MazeCell currentCell = null;
        possibilities.add(maze.getStartCell());
        // While the next possibility isn't the final destination....
        while (!(possibilities.peek().getCol() == maze.getEndCell().getCol()
                && possibilities.peek().getRow() == maze.getEndCell().getRow())) {
            currentCell = possibilities.remove();
            // Mark the cell as explored
            currentCell.setExplored(true);
            // North
            if (maze.isValidCell(currentCell.getRow() - 1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() - 1, currentCell.getCol()).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow() - 1, currentCell.getCol()));
            }
            // East
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() + 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() + 1).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow(), currentCell.getCol() + 1));
            }
            // South
            if (maze.isValidCell(currentCell.getRow()+1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() + 1, currentCell.getCol()).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow() + 1, currentCell.getCol()));
            }
            // West
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() - 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() - 1).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow(), currentCell.getCol() - 1));
            }
        }
        // Do it all one more time to link the final cell to the solutions
        currentCell = possibilities.remove();
        currentCell.setExplored(true);
        // North
        if (maze.isValidCell(currentCell.getRow() - 1, currentCell.getCol())) {
            maze.getCell(currentCell.getRow() - 1, currentCell.getCol()).setParent(currentCell);
        }
        // East
        if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() + 1)) {
            maze.getCell(currentCell.getRow(), currentCell.getCol() + 1).setParent(currentCell);
        }
        // South
        if (maze.isValidCell(currentCell.getRow()+1, currentCell.getCol())) {
            maze.getCell(currentCell.getRow() + 1, currentCell.getCol()).setParent(currentCell);
        }
        // West
        if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() - 1)) {
            maze.getCell(currentCell.getRow(), currentCell.getCol() - 1).setParent(currentCell);
        }
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
