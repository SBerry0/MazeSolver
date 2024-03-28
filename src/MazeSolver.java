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
        Stack<MazeCell> soln = new Stack<>();
        MazeCell currentCell = maze.getEndCell();
        while (currentCell != null) {
            soln.add(currentCell);
            currentCell = currentCell.getParent();
        }
        ArrayList<MazeCell> out = new ArrayList<>();
        int size = soln.size();
        for (int i = 0; i < size; i++) {
            out.add(soln.pop());
        }
        // Should be from start to end cells
        return out;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        Stack<MazeCell> possibilities = new Stack<>();
        MazeCell currentCell = null;
        possibilities.add(maze.getStartCell());
        while (!(possibilities.peek().getCol() == maze.getEndCell().getCol() && possibilities.peek().getRow() == maze.getEndCell().getRow())) {
            currentCell = possibilities.pop();
            currentCell.setExplored(true);
            if (maze.isValidCell(currentCell.getRow() - 1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() - 1, currentCell.getCol()).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow() - 1, currentCell.getCol()));
            }
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() + 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() + 1).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow(), currentCell.getCol() + 1));
            }
            if (maze.isValidCell(currentCell.getRow()+1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() + 1, currentCell.getCol()).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow() + 1, currentCell.getCol()));
            }
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() - 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() - 1).setParent(currentCell);
                possibilities.add(maze.getCell(currentCell.getRow(), currentCell.getCol() - 1));
            }
        }
        maze.getEndCell().setParent(currentCell);
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        Queue<MazeCell> options = new LinkedList<MazeCell>();
        options.add(maze.getStartCell());
        MazeCell currentCell = null;
        while (!(options.peek().getRow() == maze.getEndCell().getRow() && options.peek().getCol() == maze.getEndCell().getCol())) {
            currentCell = options.remove();
            currentCell.setExplored(true);
            if (maze.isValidCell(currentCell.getRow() - 1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() - 1, currentCell.getCol()).setParent(currentCell);
                options.add(maze.getCell(currentCell.getRow() - 1, currentCell.getCol()));
            }
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() + 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() + 1).setParent(currentCell);
                options.add(maze.getCell(currentCell.getRow(), currentCell.getCol() + 1));
            }
            if (maze.isValidCell(currentCell.getRow()+1, currentCell.getCol())) {
                maze.getCell(currentCell.getRow() + 1, currentCell.getCol()).setParent(currentCell);
                options.add(maze.getCell(currentCell.getRow() + 1, currentCell.getCol()));
            }
            if (maze.isValidCell(currentCell.getRow(), currentCell.getCol() - 1)) {
                maze.getCell(currentCell.getRow(), currentCell.getCol() - 1).setParent(currentCell);
                options.add(maze.getCell(currentCell.getRow(), currentCell.getCol() - 1));
            }
        }
        maze.getEndCell().setParent(currentCell);

        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze1.txt");

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
