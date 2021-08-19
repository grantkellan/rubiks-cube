import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class RubiksCube {
    private int NUM_RED = 0;
    private int NUM_ORANGE = 1;
    private int NUM_BLUE = 2;
    private int NUM_GREEN = 3;
    private int NUM_YELLOW = 4;
    private int NUM_WHITE = 5;
    private int MIN_COLOR_INT = 0;
    private int MAX_COLOR_INT = 5;

    public int AXIS_X = 0;
    public int AXIS_Y = 1;
    public int AXIS_Z = 1;

    private int NUM_SIDES = 6;

    private int SCRAMBLE_RAND_MIN = 100;
    private int SCRAMBLE_RAND_MAX = 300;
    private int cubeSize;


    // 2D Arrays that represent each side
    private int[][] frontSideArr;
    private int[][] backSideArr;

    private int[][] leftSideArr;
    private int[][] rightSideArr;

    private int[][] topSideArr;
    private int[][] bottomSideArr;

    /**
    * Creates new solved cube of cubeSize 3
    *
    * @return         RubiksCube object
    */
    public RubiksCube() {
        this(3, false);
    }

    /**
    * Creates new cube of cubeSize size and lets you either create a solved cube or a random one
    *
    * @param cubeSize (int) the size of the cube
    * @param scramble (boolean) whether you want the cube to be randomized or not
    * @return         RubiksCube object
    */
    public RubiksCube(int cubeSize, boolean scramble) {
        if (cubeSize <= 0) {
            cubeSize = 3;
        }
        this.cubeSize = cubeSize;
        int numSides = this.NUM_SIDES;

        int[][][] gameStateArr = new int[6][cubeSize][cubeSize];

        for (int color = 0; color < numSides; color++) {
            for (int row = 0; row < cubeSize; row++) {
                Arrays.fill(gameStateArr[color][row], color);
                for (int col = 0; col < cubeSize; col++) {
                    gameStateArr[color][row][col] = color;
                }
            }
        }

        this.frontSideArr = gameStateArr[0];
        this.backSideArr = gameStateArr[1];
        this.leftSideArr = gameStateArr[2];
        this.rightSideArr = gameStateArr[3];
        this.topSideArr = gameStateArr[4];
        this.bottomSideArr = gameStateArr[5];

        if (scramble) {
            this.scramble();
        }
    }

    /**
    * Creates new cube of cubeSize size and with predetermined gameState
    *
    * @param cubeSize     (int) the size of the cube
    * @param gameStateArr (int[6][cubeSize][cubeSize]) 3d int arr of where each color should be. Must be
                          of correct size or it will throw an exception
    * @throws Exception   if gameStateArr gives an invalid gameState (wrong arr sizes or wrong num of colors)
    * @return         RubiksCube object
    */
    public RubiksCube(int cubeSize, int[][][] gameStateArr) throws Exception {
        this.cubeSize = cubeSize;
        if (!this.validateGameState(gameStateArr)) {
            throw new Exception("Game state given is not valid");
        }

        this.frontSideArr = gameStateArr[0];
        this.backSideArr = gameStateArr[1];
        this.leftSideArr = gameStateArr[2];
        this.rightSideArr = gameStateArr[3];
        this.topSideArr = gameStateArr[4];
        this.bottomSideArr = gameStateArr[5];
    }

    /**
    * Determines whether a gameState is valid or not
    *
    * @param gameStateArr (int[][][]) 3d arr holding info on state of game
    * @return boolean     true if valid, false if not
    */
    private boolean validateGameState(int[][][] gameStateArr) {
        int cs = this.cubeSize;
        int expectedColorCount = cs * cs;
        int[] colorCounter = new int[6];

        // If there aren't six faces reuturn false
        if (gameStateArr.length != this.NUM_SIDES) {
            return false;
        }

        for (int side = 0; side < this.NUM_SIDES; side++) {
            // if the cs doesn't match the arr lenght of rows and col, return false
            if (gameStateArr[side].length != cs) {
                return false;
            }
            for (int row = 0; row < cs; row++) {
                if (gameStateArr[side][row].length != cs) {
                    return false;
                }
                for (int col = 0; col < cs; col++) {
                    int colorInt = gameStateArr[side][row][col];

                    // if a color is invalid, return false;
                    if (colorInt < this.MIN_COLOR_INT || colorInt > this.MAX_COLOR_INT) {
                        return false;
                    }
                    colorCounter[colorInt]++;

                }
            }
        }

        // if there aren't enough or too many of one color, return false
        for (int colorCountIndex = 0; colorCountIndex < colorCounter.length; colorCountIndex++) {
            if (colorCounter[colorCountIndex] != expectedColorCount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the Rubik's Cube is in a finished state.
     */
    public boolean isSolved() {
        boolean isSolved = this.sideIsSolved(this.frontSideArr);
        isSolved = isSolved && this.sideIsSolved(this.backSideArr);
        isSolved = isSolved && this.sideIsSolved(this.leftSideArr);
        isSolved = isSolved && this.sideIsSolved(this.rightSideArr);
        isSolved = isSolved && this.sideIsSolved(this.topSideArr);
        isSolved = isSolved && this.sideIsSolved(this.bottomSideArr);

        return isSolved;
    }

    /**
     * Returns whether or not a side is all the same color.
     */
    private boolean sideIsSolved(int[][] side) {
        int colorNumber = side[0][0];
        for (int row = 0; row < this.cubeSize; row++) {
            for (int col = 0; col < this.cubeSize; col++) {
                
                int currColorNumber = side[row][col];
                if (currColorNumber != colorNumber) {
                    return false;
                }
            }
        }
        return true;
    }

    public void scramble() {
        Random rn = new Random();
        int range = this.SCRAMBLE_RAND_MAX - this.SCRAMBLE_RAND_MIN + 1;
        int numMoves = rn.nextInt(range) + this.SCRAMBLE_RAND_MIN;
//
        for (int i = 0; i < numMoves; i++) {
            int axis = rn.nextInt(3);
            int layerIndex = rn.nextInt(this.cubeSize);
            int numRotate = rn.nextInt(3) + 1;

            //axis = 0;
            //layerIndex = 0;
            //numRotate = 3;

            this.makeMove(axis, layerIndex, numRotate);
        }
    }

    public String toString() {
        String cubeString = "";

        cubeString += this.getTopString();
        cubeString += this.getMiddleString();
        cubeString += this.getBottomString();

        return cubeString;
    }

    public String getTopString() {
        String returnString = "";

        int cs = this.cubeSize;
        int[][] side = this.topSideArr;
        int repeatLength = cs * 4;

        returnString += this.stringRepeat(" ", repeatLength);
        returnString += this.stringRepeat("_", repeatLength + 1);
        
        for (int row = 0; row < cs - 1; row++) {
            returnString += "\n" + this.stringRepeat(" ", repeatLength);
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(side[row][col]) + "_";
            }
            returnString += "|";

        }
        returnString += "\n" + this.stringRepeat("_", repeatLength);
        for (int col = 0; col < cs; col++) {
            returnString += "|_" + this.getColorChar(side[cs - 1][col]) + "_";
        }
        returnString += "|";
        returnString += this.stringRepeat("_", repeatLength * 2);
        return returnString;
    }

    public String getMiddleString() {
        String returnString = "";

        int cs = this.cubeSize;
        int[][] leftSide = this.leftSideArr;
        int[][] frontSide = this.frontSideArr;
        int[][] rightSide = this.rightSideArr;
        int[][] backSide = this.backSideArr;
        
        for (int row = 0; row < cs; row++) {
            returnString += "\n";
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(leftSide[row][col]) + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(frontSide[row][col]) + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(rightSide[row][col]) + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(backSide[row][col]) + "_";
            }
            returnString += "|";

        }
        return returnString;
    }

    public String getBottomString() {
        String returnString = "";

        int cs = this.cubeSize;
        int[][] side = this.bottomSideArr;
        int repeatLength = cs * 4;
        
        for (int row = 0; row < cs; row++) {
            returnString += "\n" + this.stringRepeat(" ", repeatLength);
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + this.getColorChar(side[row][col]) + "_";
            }
            returnString += "|";

        }
        returnString += "\n";
        return returnString;
    }

    private String stringRepeat(String s, int n) {
        String returnString = "";
        for (int i = 0; i < n; i++) {
            returnString += s;
        }

        return returnString;
    }

    /**
     * Helper function that rotates an entire 2d array once clockwise.
     */
    private int[][] rotateArrayClockwise(int[][] side) {
        int cs = this.cubeSize;
        int[][] newSide = new int[cs][cs];

        for (int row = 0; row < cs; row++) {
            for (int col = 0; col < cs; col++) {
                int prevRow = cs - col - 1;
                int prevCol = row;

                newSide[row][col] = side[prevRow][prevCol];
            }
        }

        return newSide;
    }

    /**
     * Helper function that rotates an entire 2d array once counter-clockwise.
     */
    private int[][] rotateArrayCounterClockwise(int[][] side) {
        int cs = this.cubeSize;
        int[][] newSide = new int[cs][cs];

        for (int row = 0; row < cs; row++) {
            for (int currCol = 0; currCol < cs; currCol++) {
                int prevRow = currCol;
                int prevCol = cs - row - 1;

                newSide[row][currCol] = side[prevRow][prevCol];
            }
        }

        return newSide;
    }

    /**
     * The next group of functions serves as the actual "moves" you can make to alter
     * the state of the game. If the names/functions of these are confusing, please see
     * "cubeMap.txt" for a simple visualization.
     */

    /**
     * This is a generic move function that lets you rotate any layer any direction
     * @param int index The index of the layer you want to rotate where 0 is the front layer
     * and (this.cubeSize - 1) is the back layer
     */
    public void makeMove(int axis, int layerIndex, int numRotate) {
        if (axis == this.AXIS_X) {
            if (numRotate == 1) {
                this.rotateXRight(layerIndex);
            } else if (numRotate == 2) {
                this.rotateXRight(layerIndex);
                this.rotateXRight(layerIndex);
            } else {
                this.rotateXLeft(layerIndex);
            }
        } else if (axis == this.AXIS_Y) {
            if (numRotate == 1) {
                this.rotateYUp(layerIndex);
            } else if (numRotate == 2) {
                this.rotateYUp(layerIndex);
                this.rotateYUp(layerIndex);
            } else {
                this.rotateYDown(layerIndex);
            }
        } else {
            if (numRotate == 1) {
                this.rotateZUp(layerIndex);
            } else if (numRotate == 2) {
                this.rotateZUp(layerIndex);
                this.rotateZUp(layerIndex);
            } else {
                this.rotateZDown(layerIndex);
            }
        }
    }

    

    /**
     * Rotates an x layer to the right once
     * @param int index The index of the layer you want to rotate where 0 is the top layer
     * and (this.cubeSize - 1) is the bottom layer
     */
    private void rotateXRight(int layerIndex) {
        int[] tempRow = this.frontSideArr[layerIndex];
        this.frontSideArr[layerIndex] = this.leftSideArr[layerIndex];
        this.leftSideArr[layerIndex] = this.backSideArr[layerIndex];
        this.backSideArr[layerIndex] = this.rightSideArr[layerIndex];
        this.rightSideArr[layerIndex] = tempRow;

        if (layerIndex == 0) {
            this.topSideArr = this.rotateArrayCounterClockwise(this.topSideArr);
        } else if (layerIndex == this.cubeSize - 1) {
            this.bottomSideArr = this.rotateArrayClockwise(this.bottomSideArr);
        }
    }

    /**
     * Rotates an x layer to the left once
     * @param int index The index of the layer you want to rotate where 0 is the top layer
     * and (this.cubeSize - 1) is the bottom layer
     */
    private void rotateXLeft(int layerIndex) {
        int[] tempRow = this.frontSideArr[layerIndex];
        this.frontSideArr[layerIndex] = this.rightSideArr[layerIndex];
        this.rightSideArr[layerIndex] = this.backSideArr[layerIndex];
        this.backSideArr[layerIndex] = this.leftSideArr[layerIndex];
        this.leftSideArr[layerIndex] = tempRow;

        if (layerIndex == 0) {
            this.topSideArr = this.rotateArrayClockwise(this.topSideArr);
        } else if (layerIndex == this.cubeSize - 1) {
            this.bottomSideArr = this.rotateArrayCounterClockwise(this.bottomSideArr);
        }
    }
    
    /**
     * Rotates a y layer up once
     * @param int index The index of the layer you want to rotate where 0 is the right layer
     * and (this.cubeSize - 1) is the left layer
     */
    private void rotateYUp(int layerIndex) {
        int cs = this.cubeSize;

        for (int i = 0; i < cs; i++) {
            int tempNum = this.frontSideArr[i][layerIndex];
            this.frontSideArr[i][layerIndex] = this.bottomSideArr[i][layerIndex];
            this.bottomSideArr[i][layerIndex] = this.backSideArr[cs - i - 1][cs - layerIndex - 1];
            this.backSideArr[cs - i - 1][cs - layerIndex - 1] = this.topSideArr[i][layerIndex];
            this.topSideArr[i][layerIndex] = tempNum;
        }

        if (layerIndex == 0) {
            this.leftSideArr = this.rotateArrayCounterClockwise(this.leftSideArr);
        } else if (layerIndex == this.cubeSize - 1) {
            this.rightSideArr = this.rotateArrayClockwise(this.rightSideArr);
        }
    }

    /**
     * Rotates a y layer down once
     * @param int index The index of the layer you want to rotate where 0 is the right layer
     * and (this.cubeSize - 1) is the left layer
     */
    private void rotateYDown(int layerIndex) {
        int cs = this.cubeSize;

        for (int i = 0; i < cs; i++) {
            int tempNum = this.frontSideArr[i][layerIndex];
            this.frontSideArr[i][layerIndex] = this.topSideArr[i][layerIndex];
            this.topSideArr[i][layerIndex] = this.backSideArr[cs - i - 1][cs - layerIndex - 1];
            this.backSideArr[cs - i - 1][cs - layerIndex - 1] = this.bottomSideArr[i][layerIndex];
            this.bottomSideArr[i][layerIndex] = tempNum;
        }

        if (layerIndex == 0) {
            this.leftSideArr = this.rotateArrayClockwise(this.leftSideArr);
        } else if (layerIndex == cs - 1) {
            this.rightSideArr = this.rotateArrayCounterClockwise(this.rightSideArr);
        }
    }

    /**
     * Rotates a z layer up once
     * @param int index The index of the layer you want to rotate where 0 is the front layer
     * and (this.cubeSize - 1) is the back layer
     */
    private void rotateZUp(int layerIndex) {
        int cs = this.cubeSize;

        for (int i = 0; i < cs; i++) {
            int tempNum = this.rightSideArr[i][layerIndex];
            this.rightSideArr[i][layerIndex] = this.bottomSideArr[layerIndex][cs - i - 1];
            this.bottomSideArr[layerIndex][cs - i - 1] = this.leftSideArr[cs - i - 1][cs - layerIndex - 1];
            this.leftSideArr[cs - i - 1][cs - layerIndex - 1] = this.topSideArr[cs - layerIndex - 1][i];
            this.topSideArr[cs - layerIndex - 1][i] = tempNum;
        }

        if (layerIndex == 0) {
            this.frontSideArr = this.rotateArrayCounterClockwise(this.frontSideArr);
        } else if (layerIndex == cs - 1) {
            this.backSideArr = this.rotateArrayClockwise(this.backSideArr);
        }
    }

    /**
     * Rotates a z layer down once
     * @param int index The index of the layer you want to rotate where 0 is the front layer
     * and (this.cubeSize - 1) is the back layer
     */
    public void rotateZDown(int layerIndex) {
        int cs = this.cubeSize;

        for (int i = 0; i < cs; i++) {
            int tempNum = this.rightSideArr[i][layerIndex];
            this.rightSideArr[i][layerIndex] = this.topSideArr[cs - layerIndex - 1][i];
            this.topSideArr[cs - layerIndex - 1][i] = this.leftSideArr[cs - i - 1][cs - layerIndex - 1];
            this.leftSideArr[cs - i - 1][cs - layerIndex - 1] = this.bottomSideArr[layerIndex][cs - i - 1];
            this.bottomSideArr[layerIndex][cs - i - 1] = tempNum;
        }

        if (layerIndex == 0) {
            this.frontSideArr = this.rotateArrayClockwise(this.frontSideArr);
        } else if (layerIndex == this.cubeSize - 1) {
            this.backSideArr = this.rotateArrayCounterClockwise(this.backSideArr);
        }
    }
/**End Movement Methods */
/********************************************************************* */

/**
 * Returns string representation of Color int Constants
 * @param int colorNum The constant number given to the 
 * color that you want the String for
 */
private String getColorString(int colorNumber) {
    if (colorNumber == this.NUM_RED) {
        return "Red";
    } else if (colorNumber == this.NUM_BLUE) {
        return "Blue";
    } else if (colorNumber == this.NUM_GREEN) {
        return "Green";
    } else if (colorNumber == this.NUM_ORANGE) {
        return "Orange";
    } else if (colorNumber == this.NUM_YELLOW) {
        return "Yellow";
    } else { // if (colorNumber == this.NUM_WHITE) {
        return "White";
    }
}

/**
 * Returns char representation of Color int Constants
 * @param int colorNum The constant number given to the 
 * color that you want the char for
 */
private char getColorChar(int colorNumber) {
    if (colorNumber == this.NUM_RED) {
        return 'R';
    } else if (colorNumber == this.NUM_BLUE) {
        return 'B';
    } else if (colorNumber == this.NUM_GREEN) {
        return 'G';
    } else if (colorNumber == this.NUM_ORANGE) {
        return 'O';
    } else if (colorNumber == this.NUM_YELLOW) {
        return 'Y';
    } else { // if (colorNumber == this.NUM_WHITE) {
        return 'W';
    } 
}

// @TODO: Implement move memory
/**
 * Attempts to solve the cube with moveLimit number of random moves 
 * @param int moveLimit Max number of moves before giving up on the
 * "algorithm"
 * @return String[] array of moves that will solve the Cube. 
 */
public void solveRandom(int moveLimit) {
    int numMoves = 0;
    Random rn = new Random();
    while (!this.isSolved() && numMoves < moveLimit) {
        int axis = rn.nextInt(3);
        int index = rn.nextInt(this.cubeSize);
        int numTurns = rn.nextInt(3) + 1;

        this.makeMove(axis, index, numTurns);
        numMoves++;
    }
    if (this.isSolved()) {
        System.out.println("Solved in " + numMoves + " moves");
    } else {
        System.out.println("Not Solved after " + numMoves + " moves");
    }
}

/**
 * Attempts to solve the cube using a Depth First Search.
 * The algorithm will search as deep as depthLimit
 * @param int depthLimit Max depth for the algorithm to search
 * @return String[] array of moves that will solve the Cube. 
 */
public Stack<int[]> solveDFS(int depthLimit, boolean leaveCubeSolved) {
    Stack<int[]> moveStack = new Stack<int[]>();

    if (this.solveDFSRecursive(depthLimit, moveStack, -1, -1, leaveCubeSolved)) {
        Stack<int[]> reverseStack = new Stack<int[]>();

        // print moves
        while (!moveStack.isEmpty()) {
            reverseStack.push(moveStack.pop());
        }
        while (!reverseStack.isEmpty()) {
            int[] move = reverseStack.pop();
            moveStack.push(move);

            int axis = move[0];
            int layer = move[1];
            int numTurns = move[2];

            String printString = "turn the layer " + layer;
            printString += " on the axis " + axis;
            printString += " " + numTurns + " number of turns.";

            System.out.println(printString);
        }
        return moveStack;
    } else {
        System.out.println("No solutions found");;
        return new Stack<int[]>();
    }
}

private boolean solveDFSRecursive(int depthLimit, Stack<int[]> moveStack, int lastAxis,
                                  int lastLayer, boolean leaveCubeSolved) {
    if (this.isSolved()) {
        if (!leaveCubeSolved) {
            Stack<int[]> reverseStack = new Stack<int[]>();

            // undo moves
            while (!moveStack.isEmpty()) {
                int[] move = moveStack.pop();
                reverseStack.push(move);
                int axis = move[0];
                int layer = move[1];
                int numTurns = 4 - move[2];
                this.makeMove(axis, layer, numTurns);
            }

            // rebuild the stack
            while (!reverseStack.isEmpty()) {
                moveStack.push(reverseStack.pop());
            }
        }
        return true;
    }

    if (moveStack.size() > depthLimit) {
        return false;
    }

    for (int numTurns = 1; numTurns <= 3; numTurns++) {
        int undoNumTurns = 4 - numTurns;
        for (int layer = 0; layer < this.cubeSize; layer++) {
            for (int axis = 0; axis < 3; axis++) {
                if (axis != lastAxis || layer != lastLayer) {
                    int[] newMoveArr = new int[3];
                    newMoveArr[0] = axis;
                    newMoveArr[1] = layer;
                    newMoveArr[2] = numTurns;

                    moveStack.push(newMoveArr);
                    this.makeMove(axis, layer, numTurns);
                    //System.out.println(this.toString());
                    if (this.solveDFSRecursive(depthLimit, moveStack, axis, layer, leaveCubeSolved)) {
                        return true;
                    }
                    moveStack.pop();
                    this.makeMove(axis, layer, undoNumTurns);
                }
            }
        }
    }
    return false;
}
    
    
}
