package proj_2_2;

// Java program for finding maximum
// and minimum in a matrix.

import java.util.Scanner;

class t
{
    static final int MAX = 100;

    // Finds maximum and minimum
    // in arr[0..n-1][0..n-1]
    // using pair wise comparisons
    static void maxMin(int arr[][], int n)
    {
        int min = +2147483647;
        int max = -2147483648;

        // Traverses rows one by one
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j <= n/2; j++)
            {
                // Compare elements from beginning
                // and end of current row
                if (arr[i][j] > arr[i][n - j - 1])
                {
                    if (min > arr[i][n - j - 1])
                        min = arr[i][n - j - 1];
                    if (max< arr[i][j])
                        max = arr[i][j];
                }
                else
                {
                    if (min > arr[i][j])
                        min = arr[i][j];
                    if (max< arr[i][n - j - 1])
                        max = arr[i][n - j - 1];
                }
            }
        }
        System.out.print("Maximum = "+max+
                ", Minimum = "+min);
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

    // Driver program
    public static void main (String[] args)
    {
        //Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter number of matrix rows for your matrix: ");
        int row = sc.nextInt();
        System.out.println("Please enter number of matrix columns for your matrix  : ");
        int col = sc.nextInt();
        int[][] numbers = new int[row][col];

        fillingMatrix(sc, numbers, row, col);
        int arr[][] = numbers;
        maxMin(arr, 3);
    }
}

