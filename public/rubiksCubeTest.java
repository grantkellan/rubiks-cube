public class rubiksCubeTest {
    public static void main(String[] args) throws Exception {
        int cs = 2;

        int[][] frontSide = new int[][] {{4, 5}, {5, 0}};
        int[][] backSide = new int[][] {{5, 3}, {0, 4}};;

        int[][] leftSide = new int[][] {{4, 1}, {2, 2}};;
        int[][] rightSide = new int[][] {{0, 3}, {2, 5}};;

        int[][] topSide = new int[][] {{0, 1}, {3, 2}};;
        int[][] bottomSide = new int[][] {{1, 4}, {1, 3}};;

        int[][][] gameState = new int[][][] {frontSide, backSide, leftSide, rightSide, topSide, bottomSide};

        RubiksCube cube = new RubiksCube(2, gameState);

        //RubiksCube cube = new RubiksCube(2, true);
        System.out.print(cube.toString());
        cube.solveDFS(6, false);
        System.out.print(cube.toString());
    }
}
