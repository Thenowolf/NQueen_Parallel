import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueen
{
    public static int N = 8;
    public static int count;

    public static void print(int[][] board)
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.print(" " + board[i][j] + " ");
            System.out.println("");
        }
        System.out.println("---------------");
    }

    public static boolean isSafe(int[][] board, int row, int col)
    {
        int i, j;

        // Kontrola na levo
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        // Kontrola horní levá diagonála
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Kontrola dolní levá diagonála
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    public static boolean solveNQUtil(int[][] board, int col)
    {
        if (col >= N)
        {
            print(board);
            count++;
        }
        //return true;

        for (int i = 0; i < N; i++)
        {
            if (isSafe(board, i, col))
            {
                board[i][col] = 1;

                if (solveNQUtil(board, col + 1) == true)
                    return true;

                board[i][col] = 0; // BACKTRACK
            }
        }
        return false;
    }

    public static boolean solve(int row, int col)
    {

        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                board[i][j] = 0;
            }
        }
        if(row != -1)
        {
            board[row][0] = 1; // Aby byl první sloupec statický, tzn. vyzkouší všechna øešení jen pro pevnì danou dámu v prvním sloupci
        }

        if (solveNQUtil(board, col) == false)
        {
        	System.out.println("Reseni neexistuje");
        	System.out.println("Pocet reseni : " + count);
            return false;
        }

       //printSolution(board);
        return true;
    }

    public static boolean solveParallel()
    {
        List<Thread> tasks = new ArrayList<>();
        for (int i = 0; i < N; i++)
        {
        	final int row = i;
            Thread t = new Thread(() -> solve(row, 1));
            t.start();
            try {
				t.join();
			} catch (Exception ex) {
				System.out.print("chyba");
			}
            tasks.add(t);
        }
        return true;
    }
}