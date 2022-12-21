public class MatrixMultiplicationThread extends Thread {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int row;
    private int col;

    public MatrixMultiplicationThread(int[][] matrixA, int[][] matrixB, int[][] result, int row, int col) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.row = row;
        this.col = col;
    }

    @Override
    public void run() {
        result[row][col] = 0;
        for (int i = 0; i < matrixA[0].length; i++) {
            result[row][col] += matrixA[row][i] * matrixB[i][col];
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int[][] matrixA = {{1, 2, 3}, {4, 5, 6}};
        int[][] matrixB = {{7, 8}, {9, 10}, {11, 12}};
        int[][] result = new int[matrixA.length][matrixB[0].length];

        MatrixMultiplicationThread[][] tasks = new MatrixMultiplicationThread[matrixA.length][matrixB[0].length];
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[i].length; j++) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("------------------");
        
        for (int i = 0; i < matrixB.length; i++) {
            for (int j = 0; j < matrixB[i].length; j++) {
                System.out.print(matrixB[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("------------------");
        
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                tasks[i][j] = new MatrixMultiplicationThread(matrixA, matrixB, result, i, j);
                tasks[i][j].start();
            }
        }

        // Wait for all the threads to complete
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                try {
                    tasks[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Print the result
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        
        long endTime = System.nanoTime();
        double elapsedTime = endTime - startTime;
        double elapsedTimeInSecodns = (double) elapsedTime / 1_000_000_000;
        System.out.println("Execute time = " + elapsedTimeInSecodns);
    }
}
