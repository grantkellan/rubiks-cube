import java.util.Arrays;

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
    public int AXIS_Z = 2;

    private int NUM_SIDES = 6;
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

    public void scramble() {
        //int numMoves = rand() % 100 + 300;
//
        //for (int i = 0; i < numMoves; i++) {
        //    int axis = rand() % 3;
        //    int numRotate = rand() % 3;
        //    int layerIndex = rand() % this.cubeSize;
        //}
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
                returnString += "|_" + side[row][col] + "_";
            }
            returnString += "|";

        }
        returnString += "\n" + this.stringRepeat("_", repeatLength);
        for (int col = 0; col < cs; col++) {
            returnString += "|_" + side[cs - 1][col] + "_";
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
                returnString += "|_" + leftSide[row][col] + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + frontSide[row][col] + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + rightSide[row][col] + "_";
            }
            for (int col = 0; col < cs; col++) {
                returnString += "|_" + backSide[row][col] + "_";
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
                returnString += "|_" + side[row][col] + "_";
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
            int tempNum = this.frontSideArr[i][cs - layerIndex - 1];
            this.frontSideArr[i][cs - layerIndex - 1] = this.bottomSideArr[i][cs - layerIndex - 1];
            this.bottomSideArr[i][cs - layerIndex - 1] = this.backSideArr[cs - i - 1][layerIndex];
            this.backSideArr[cs - i - 1][layerIndex] = this.topSideArr[i][cs - layerIndex - 1];
            this.topSideArr[i][cs - layerIndex - 1] = tempNum;
        }

        if (layerIndex == 0) {
            this.rightSideArr = this.rotateArrayClockwise(this.rightSideArr);
        } else if (layerIndex == this.cubeSize - 1) {
            this.leftSideArr = this.rotateArrayCounterClockwise(this.leftSideArr);
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
            int tempNum = this.frontSideArr[i][cs - layerIndex - 1];
            this.frontSideArr[i][cs - layerIndex - 1] = this.topSideArr[i][cs - layerIndex - 1];
            this.topSideArr[i][cs - layerIndex - 1] = this.backSideArr[cs - i - 1][layerIndex];
            this.backSideArr[cs - i - 1][layerIndex] = this.bottomSideArr[i][cs - layerIndex - 1];
            this.bottomSideArr[i][cs - layerIndex - 1] = tempNum;
        }

        if (layerIndex == 0) {
            this.rightSideArr = this.rotateArrayCounterClockwise(this.rightSideArr);
        } else if (layerIndex == cs - 1) {
            this.leftSideArr = this.rotateArrayClockwise(this.leftSideArr);
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

    
    
}
