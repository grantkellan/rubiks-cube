<?php
class RubiksCube {
    $RED_NUM = 1;
    $ORANGE_NUM = 2;
    $BLUE_NUM = 3;
    $GREEN_NUM = 4;
    $YELLOW_NUM = 5;
    $WHITE_NUM = 6;

    function __construct($cubeSize=3, $scrambled = true) {
        $this->cubeSize = max(2, $cubeSize);

        $this->frontSide = $this->constructSide($this->RED_NUM);
        $this->backSide = $this->constructSide($this->ORANGE_NUM);
        $this->leftSide = $this->constructSide($this->BLUE_NUM);
        $this->rightSide = $this->constructSide($this->GREEN_NUM);
        $this->topSide = $this->constructSide($this->YELLOW_NUM);
        $this->bottomSide = $this->constructSide($this->WHITE_NUM);
    }

    private function constructSide($colorNumber=1) {
        $row = array_fill(0, $this->cubeSize, $colorNumber);
        return array_fill(0, $this->cubeSize, $row);
    }

    function isSolved() {
        $isSolved = $this->sideIsSolved($this->frontSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->backSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->leftSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->rightSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->topSide);
        $isSolved = $isSolved && $this->sideIsSolved($this->bottomSide);

        return $isSolved;
    }

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

    function rotateCubeLeft() {
        $tempSide = $this->frontSide;
        $this->frontSide = $this->rightSide;
        $this->rightSide = $this->backSide;
        $this->backSide = $this->leftSide;
        $this->leftSide = $tempSide;

        $this->bottomSide = $this->rotateSideCounterClockwise($this->bottomSide);
        $this->topSide = $this->rotateSideClockwise($this->topSide);
    }

    function rotateCubeRight() {
        $tempSide = $this->frontSide;
        $this->frontSide = $this->leftSide;
        $this->leftSide = $this->backSide;
        $this->backSide = $this->rightSide;
        $this->rightSide = $tempSide;

        $this->bottomSide = $this->rotateSideClockwise($this->bottomSide);
        $this->topSide = $this->rotateSideCounterClockwise($this->topSide);
    }

    function rotateCubeUp() {
        $tempSide = $this->frontSide;

        $this->frontSide = $this->bottomSide;
        $this->bottomSide = $this->backSide;
        $this->backSide = $this->topSide;
        $this->topSide = $tempSide;

        $this->bottomSide = $this->rotateSide180($this->bottomSide);
        $this->backSide = $this->rotateSide180($this->backSide);
        $this->leftSide = $this->rotateSideCounterClockwise($this->leftSide);
        $this->rightSide = $this->rotateSideClockwise($this->rightSide);
    }

    function rotateCubeDown() {
        $tempSide = $this->frontSide;

        $this->frontSide = $this->topSide;
        $this->topSide = $this->backSide;
        $this->backSide = $this->topSide;
        $this->bottomSide = $tempSide;

        $this->topSide = $this->rotateSide180($this->topSide);
        $this->backSide = $this->rotateSide180($this->backSide);
        $this->leftSide = $this->rotateSideCounterClockwise($this->leftSide);
        $this->rightSide = $this->rotateSideClockwise($this->rightSide);
    }

    private function rotateSideClockwise($side) {
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

    private function rotateSideCounterClockwise($side) {
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

    private function rotateSide180($side) {
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
}
?>