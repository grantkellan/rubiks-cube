<?php
class RubiksCube {
    $RED_NUM = 1;
    $ORANGE_NUM = 2;
    $BLUE_NUM = 3;
    $GREEN_NUM = 4;
    $YELLOW_NUM = 5;
    $WHITE_NUM = 6;

    $AXIS_X = 1;
    $AXIS_Y = 2;
    $AXIS_Z = 3;

    public function __construct($cubeSize=3, $scrambled = true) {
        $this->cubeSize = max(2, $cubeSize);

        $this->frontSide = $this->constructSide($this->RED_NUM);
        $this->backSide = $this->constructSide($this->ORANGE_NUM);
        $this->leftSide = $this->constructSide($this->BLUE_NUM);
        $this->rightSide = $this->constructSide($this->GREEN_NUM);
        $this->topSide = $this->constructSide($this->YELLOW_NUM);
        $this->bottomSide = $this->constructSide($this->WHITE_NUM);
    }

    /**
     * Returns a 2d array of size NxN where N = $this->cubeSize
     * @param int $colorNumber the value of each element in the array
     */
    private function constructSide($colorNumber=1) {
        $row = array_fill(0, $this->cubeSize, $colorNumber);
        return array_fill(0, $this->cubeSize, $row);
    }

    /**
     * Returns whether or not the Rubik's Cube is in a finished state.
     */
    public function isSolved() {
        $isSolved = $this->sideIsSolved($this->frontSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->backSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->leftSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->rightSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->topSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->bottomSide);

        return $isSolved;
    }

    /**
     * Returns whether or not a side is all the same color.
     */
    private function sideIsSolved($side) {
        $colorNumber = -1;
        for ($currRow = 0; $currRow < $this->cubeSize; $currRow++) {
            for ($currCol = 0; $currCol < $this->cubeSize; $currCol++) {
                if ($colorNumber == -1) {
                    $colorNumber = $side[$currRow][$currCol];
                }
                
                $currColorNumber = $side[$currRow][$currCol];
                if ($currColorNumber !== $colorNumber) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This function rotates the entire cube to the left
     */
    public function rotateCubeLeft() {
        $tempSide = $this->frontSide;
        $this->frontSide = $this->rightSide;
        $this->rightSide = $this->backSide;
        $this->backSide = $this->leftSide;
        $this->leftSide = $tempSide;

        $this->bottomSide = $this->rotateArrayCounterClockwise($this->bottomSide);
        $this->topSide = $this->rotateArrayClockwise($this->topSide);
    }

    /**
     * This function rotates the entire cube to the right
     */
    public function rotateCubeRight() {
        $tempSide = $this->frontSide;
        $this->frontSide = $this->leftSide;
        $this->leftSide = $this->backSide;
        $this->backSide = $this->rightSide;
        $this->rightSide = $tempSide;

        $this->bottomSide = $this->rotateArrayClockwise($this->bottomSide);
        $this->topSide = $this->rotateArrayCounterClockwise($this->topSide);
    }

    /**
     * This function rotates the entire cube up so you can see the bottom
     */
    public function rotateCubeUp() {
        $tempSide = $this->frontSide;

        $this->frontSide = $this->bottomSide;
        $this->bottomSide = $this->backSide;
        $this->backSide = $this->topSide;
        $this->topSide = $tempSide;

        $this->bottomSide = $this->rotateArray180($this->bottomSide);
        $this->backSide = $this->rotateArray180($this->backSide);
        $this->leftSide = $this->rotateArrayCounterClockwise($this->leftSide);
        $this->rightSide = $this->rotateArrayClockwise($this->rightSide);
    }

    /**
     * This function rotates the entire cube down so you can see the top
     */
    public function rotateCubeDown() {
        $tempSide = $this->frontSide;

        $this->frontSide = $this->topSide;
        $this->topSide = $this->backSide;
        $this->backSide = $this->topSide;
        $this->bottomSide = $tempSide;

        $this->topSide = $this->rotateArray180($this->topSide);
        $this->backSide = $this->rotateArray180($this->backSide);
        $this->leftSide = $this->rotateArrayCounterClockwise($this->leftSide);
        $this->rightSide = $this->rotateArrayClockwise($this->rightSide);
    }

    /**
     * Helper function that rotates an entire 2d array once clockwise.
     */
    private function rotateArrayClockwise($side) {
        $row = array_fill(0, $this->cubeSize, 0);
        $newSide = array_fill(0, $this->cubeSize, $row);

        for ($currRow = 0; $currRow < $this->cubeSize; $currRow++) {
            for ($currCol = 0; $currCol < $this->cubeSize; $currCol++) {
                $prevRow = $this->cubeSize - $currCol - 1;
                $prevCol = $currRow;

                $newSide[$currRow][$currCol] = $side[$prevRow][$prevCol];
            }
        }

        return $newSide;
    }

    /**
     * Helper function that rotates an entire 2d array once counter-clockwise.
     */
    private function rotateArrayCounterClockwise($side) {
        $row = array_fill(0, $this->cubeSize, 0);
        $newSide = array_fill(0, $this->cubeSize, $row);

        for ($currRow = 0; $currRow < $this->cubeSize; $currRow++) {
            for ($currCol = 0; $currCol < $this->cubeSize; $currCol++) {
                $prevRow = $currCol;
                $prevCol = $this->cubeSize - $currRow - 1;

                $newSide[$currRow][$currCol] = $side[$prevRow][$prevCol];
            }
        }

        return $newSide;
    }

    /**
     * Helper function that rotates an entire 2d array 180 degrees
     */
    private function rotateArray180($side) {
        $row = array_fill(0, $this->cubeSize, 0);
        $newSide = array_fill(0, $this->cubeSize, $row);

        for ($currRow = 0; $currRow < $this->cubeSize; $currRow++) {
            for ($currCol = 0; $currCol < $this->cubeSize; $currCol++) {
                $prevRow = $this->cubeSize - $currRow - 1;
                $prevCol = $this->cubeSize - $currCol - 1;

                $newSide[$currRow][$currCol] = $side[$prevRow][$prevCol];
            }
        }

        return $newSide;
    }

    /**
     * The next group of functions serves as the actual "moves" you can make to alter
     * the state of the game. If the names/functions of these are confusing, please see
     * "cubeMap.txt" for a simple visualization.
     */

    /**
     * Rotates an x layer to the right once
     * @param int $index The index of the layer you want to rotate where 0 is the top layer
     * and ($this->cubeSize - 1) is the bottom layer
     */
    public function rotateXRight($index) {
        $tempRow = $this->frontSide[$index];
        $this->frontSide[$index] = $this->leftSide[$index];
        $this->leftSide[$index] = $this->backSide[$index];
        $this->backSide[$index] = $this->rightSide[$index];
        $this->rightSide[$index] = $tempRow;

        if ($index == 0) {
            $this->topSide = $this->rotateArrayCounterClockwise($this->topSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->bottomSide = $this->rotateArrayClockwise($this->bottomSide);
        }
    }

    /**
     * Rotates an x layer to the left once
     * @param int $index The index of the layer you want to rotate where 0 is the top layer
     * and ($this->cubeSize - 1) is the bottom layer
     */
    public function rotateXLeft($index) {
        $tempRow = $this->frontSide[$index];
        $this->frontSide[$index] = $this->rightSide[$index];
        $this->rightSide[$index] = $this->backSide[$index];
        $this->backSide[$index] = $this->leftSIde[$index];
        $this->leftSide[$index] = $tempRow;

        if ($index == 0) {
            $this->topSide = $this->rotateArrayClockwise($this->topSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->bottomSide = $this->rotateArrayCounterClockwise($this->bottomSide);
        }
    }
    
    /**
     * Rotates a y layer up once
     * @param int $index The index of the layer you want to rotate where 0 is the right layer
     * and ($this->cubeSize - 1) is the left layer
     */
    public function rotateYUp($index) {
        $cs = $this->cubeSize

        for ($i = 0; $i < $this->cubeSize; $i++) {
            $tempNum = $this->frontSide[$i][$cs - $index - 1];
            $this->frontSide[$i][$cs - $index - 1] = $this->bottomSide[$i][$cs - $index - 1];
            $this->bottomSide[$i][$cs - $index - 1] = $this->backSide[$cs - $i - 1][$index];
            $this->backSide[$cs - $i - 1][$index]; = $this->topSide[$i][$cs - $index - 1];
            $this->topSide[$i][$cs - $index - 1] = $tempNum;
        }

        if ($index == 0) {
            $this->rightSide = $this->rotateArrayClockwise($this->rightSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->leftSide = $this->rotateArrayCounterClockwise($this->leftSide);
        }
    }

    /**
     * Rotates a y layer down once
     * @param int $index The index of the layer you want to rotate where 0 is the right layer
     * and ($this->cubeSize - 1) is the left layer
     */
    public function rotateYDown($index) {
        $cs = $this->cubeSize

        for ($i = 0; $i < $this->cubeSize; $i++) {
            $tempNum = $this->frontSide[$i][$cs - $index - 1];
            $this->frontSide[$i][$cs - $index - 1] = $this->topSide[$i][$cs - $index - 1];
            $this->topSide[$i][$cs - $index - 1] = $this->backSide[$cs - $i - 1][$index];
            $this->backSide[$cs - $i - 1][$index] = $this->bottomSide[$i][$cs - $index - 1];
            $this->bottomSide[$i][$cs - $index - 1] = $tempNum;
        }

        if ($index == 0) {
            $this->rightSide = $this->rotateArrayCounterClockwise($this->rightSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->leftSide = $this->rotateArrayClockwise($this->leftSide);
        }
    }

    /**
     * Rotates a z layer up once
     * @param int $index The index of the layer you want to rotate where 0 is the front layer
     * and ($this->cubeSize - 1) is the back layer
     */
    public function rotateZUp($index) {
        $cs = $this->cubeSize

        for ($i = 0; $i < $this->cubeSize; $i++) {
            $tempNum = $this->rightSide[$i][$index];
            $this->rightSide[$i][$index] = $this->bottomSide[$index][$cs - $i - 1];
            $this->bottomSide[$index][$cs - $i - 1] = $this->leftSide[$i][$cs - $index - 1];
            $this->leftSide[$i][$cs - $index - 1] = $this->topSide[$cs - $index - 1][$i];
            $this->topSide[$cs - $index - 1][$i] = $tempNum;
        }

        if ($index == 0) {
            $this->frontSide = $this->rotateArrayCounterClockwise($this->frontSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->backSide = $this->rotateArrayClockwise($this->backSide);
        }
    }

    /**
     * Rotates a z layer down once
     * @param int $index The index of the layer you want to rotate where 0 is the front layer
     * and ($this->cubeSize - 1) is the back layer
     */
    public function rotateZDown($index) {
        $cs = $this->cubeSize

        for ($i = 0; $i < $this->cubeSize; $i++) {
            $tempNum = $this->rightSide[$i][$index];
            $this->rightSide[$i][$index] = $this->topSide[$cs - $index - 1][$i];
            $this->topSide[$cs - $index - 1][$i] = $this->leftSide[$i][$cs - $index - 1];
            $this->leftSide[$i][$cs - $index - 1] = $this->bottomSide[$index][$cs - $i - 1];
            $this->bottomSide[$index][$cs - $i - 1] = $tempNum
        }

        if ($index == 0) {
            $this->frontSide = $this->rotateArrayClockwise($this->frontSide);
        } else if ($index == $this->cubeSize - 1) {
            $this->backSide = $this->rotateArrayCounterClockwise($this->backSide);
        }
    }

    /**
     * This is a generic move function that lets you rotate any layer
     * @param int $index The index of the layer you want to rotate where 0 is the front layer
     * and ($this->cubeSize - 1) is the back layer
     */
    public function makeMove($axis, $layerIndex, $numRotate) {
        if ($axis == $this->AXIS_X) {
            if ($numRotate == 1) {
                $this->rotateXRight($layerIndex);
            } else if ($numRotate == 2) {
                $this->rotateXRight($layerIndex);
                $this->rotateXRight($layerIndex);
            } else {
                $this->rotateXLeft($layerIndex);
            }
        } else if ($axis == $this->AXIS_Y) {
            if ($numRotate == 1) {
                $this->rotateYUp($layerIndex);
            } else if ($numRotate == 2) {
                $this->rotateYUp($layerIndex);
                $this->rotateYUp($layerIndex);
            } else {
                $this->rotateYDown($layerIndex);
            }
        } else {
            if ($numRotate == 1) {
                $this->rotateZUp($layerIndex);
            } else if ($numRotate == 2) {
                $this->rotateZUp($layerIndex);
                $this->rotateZUp($layerIndex);
            } else {
                $this->rotateZDown($layerIndex);
            }
        }
    }

    public function scramble() {
        $numMoves = rand(100, 200);

        for ($i = 0; $i < $numMoves; $i++) {
            $numRotate = rand(1,3);
            $axis = rand(1,3);
            $layerIndex = rand(0, $this->cubeSize - 1);

            $this->makeMove($axis, $layerIndex, $numRotate);
        }
    }

    public function printCube() {
        $printLayer0 = "             _____________"
        $printLayer1 = "             |___|___|___|"
        $printLayer2 = "             |___|___|___|"
        $printLayer3 = "_____________|___|___|___|__________________________"
        $printLayer4 = "|___|___|___||___|___|___||___|___|___||___|___|___|"
        $printLayer5 = "|___|___|___||___|___|___||___|___|___||___|___|___|"
        $printLayer6 = "|___|___|___||___|___|___||___|___|___||___|___|___|"
        $printLayer7 = "             |___|___|___|"
        $printLayer8 = "             |___|___|___|"
        $printLayer9 = "             |___|___|___|"
    }
}
?>