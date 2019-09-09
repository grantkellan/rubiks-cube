<?php
    require_once('RubiksCube.php');

    $cubeOptions1 = ["cubeSize" => 2, "scrambled"=>true];
    $cubeOptions2 = ["cubeSize" => 3, "scrambled"=>true];

    $cubeOptions3 = ["cubeSize" => 2, "scrambled"=>false];
    $cubeOptions3 = ["cubeSize" => 3, "scrambled"=>false];

    $front = [[3, 3], [3, 3]];
    $back = [[4, 4], [4, 4]];

    $left = [[5, 2], [2, 2]];
    $right = [[1, 5], [1, 1]];

    $top = [[1, 2], [5, 5]];
    $bottom = [[6, 6], [6, 6]];

    $cubeOptions5 = [];
    $cubeOptions5["frontSide"] = $front;
    $cubeOptions5["backSide"] = $back;
    $cubeOptions5["leftSide"] = $left;
    $cubeOptions5["rightSide"] = $right;
    $cubeOptions5["topSide"] = $top;
    $cubeOptions5["bottomSide"] = $bottom;

    $cube = new RubiksCube($cubeOptions5);
    echo $cube->toString();

    $cube->solveDFS(5, false);
    echo $cube->toString();
?>