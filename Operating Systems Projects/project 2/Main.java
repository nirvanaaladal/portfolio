package proj_2_2;

// Java program to multiply two square matrices.

import java.io.*;
import java.util.Scanner;

public class Main {

    // Function to print Matrix
    static void printMatrix(int M[][],
                            int rowSize,
                            int colSize)
    {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++)
                System.out.print(M[i][j] + " ");

            System.out.println();
        }
    }

    // Function to multiply
    // two matrices A[][] and B[][]
    static void multiplyMatrix(
            int row1, int col1, int A[][],
            int row2, int col2, int B[][])
    {
        int i, j, k;

        // Print the matrices A and B
        System.out.println("\nMatrix A:");
        printMatrix(A, row1, col1);
        System.out.println("\nMatrix B:");
        printMatrix(B, row2, col2);

        // Check if multiplication is Possible
        if (row2 != col1) {

            System.out.println(
                    "\nMultiplication Not Possible");
            return;
        }

        // Matrix to store the result
        // The product matrix will
        // be of size row1 x col2
        int C[][] = new int[row1][col2];

        // Multiply the two matrices
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }

        // Print the result
        System.out.println("\nResultant Matrix:");
        printMatrix(C, row1, col2);
    }

    public static void fillingMatrix(Scanner scan, int num[][], int rows, int columns)
    {
        System.out.println("Please enter elements in the 2 matrices you defined above : ");
        for(int a = 0; a < rows; a++)
        {
            for(int b = 0; b < columns; b++)
            {
                num[a][b] = scan.nextInt();
            }
        }
    }

    // Driver code
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter number of matrix rows for matrix 1: ");
        int row = sc.nextInt();
        System.out.println("Please enter number of matrix columns for matrix 1 : ");
        int col = sc.nextInt();
        int[][] numbers = new int[row][col];

       // Scanner sc = new Scanner(System.in);
        System.out.println("Please enter number of matrix rows for matrix 2: ");
        int row2matrix = sc.nextInt();
        System.out.println("Please enter number of matrix columns for matrix 2 : ");
        int col2matrix = sc.nextInt();
        int[][] number2 = new int[row2matrix][col2matrix];
//assigning matrix values


        fillingMatrix(sc, numbers, row, col);
        fillingMatrix(sc, number2, row2matrix, col2matrix);
        //end assigning values








        int row1 = row2matrix, col1 = col2matrix, row2 = row, col2 = col;

//        int A[][] = { { 1, 1, 1 },
//                { 2, 2, 2 },
//                { 3, 3, 3 },
//                { 4, 4, 4 } };

//        int B[][] = { { 1, 1, 1, 1 },
//               { 2, 2, 2, 2 },
//               { 3, 3, 3, 3 } };

        int B[][] = numbers;
        int A[][] = number2;

        multiplyMatrix(row1, col1, A,
                row2, col2, B);
    }
}
