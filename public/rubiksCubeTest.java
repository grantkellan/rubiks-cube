public class rubiksCubeTest {
    public static void main(String[] args) {
        RubiksCube cube = new RubiksCube(2, false);
        System.out.print(cube.toString());

        cube.makeMove(cube.AXIS_X, 0, 1);
        System.out.print(cube.toString());
        cube.makeMove(cube.AXIS_Y, 0, 1);

        System.out.print(cube.toString());

        cube.makeMove(cube.AXIS_Z, 0, 1);

        System.out.print(cube.toString());
        cube.makeMove(cube.AXIS_Z, 0, 3);

        System.out.print(cube.toString());

        cube.makeMove(cube.AXIS_X, 1, 1);

        System.out.print(cube.toString());
    }
}
