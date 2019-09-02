<?php
    require_once('RubiksCube.php');

    $cube = new RubiksCube(2);
    echo $cube->toString();
    $cube->scramble();
    echo $cube->toString();

    $cube->solveRandom(100000);
    echo $cube->toString();
?>