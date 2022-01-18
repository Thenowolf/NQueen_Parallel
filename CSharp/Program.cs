using System;
using System.Threading;

public static class NQueen
{
    public static int N = 8;
    public static int count;

    public static void print(int[,] board)
    {
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                Console.Write(" " + board[i, j] + " ");
            Console.WriteLine();
        }
        Console.WriteLine("---------------");
    }

    public static bool isSafe(int[,] board, int row, int col)
    {
        int i, j;

        // Kontrola na levo
        for (i = 0; i < col; i++)
            if (board[row, i] == 1)
                return false;

        // Kontrola horní levá diagonála
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i, j] == 1)
                return false;

        // Kontrola dolní levá diagonála
        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i, j] == 1)
                return false;

        return true;
    }

    public static bool solveNQUtil(int[,] board, int col)
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
                board[i, col] = 1;

                if (solveNQUtil(board, col + 1) == true)
                    return true;

                board[i, col] = 0; // BACKTRACK
            }
        }
        return false;
    }

    public static bool solve(int row, int col)
    {

        int[,] board = new int[N, N];
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                board[i, j] = 0;
            }
        }
        if(row != -1)
        {
            board[row,0] = 1; // Aby byl první sloupec statický, tzn. vyzkouší všechna řešení jen pro pevně danou dámu v prvním sloupci
        }

        if (solveNQUtil(board, col) == false)
        {
            Console.WriteLine("Reseni neexistuje");
            Console.WriteLine("Pocet reseni : " + count);
            return false;
        }

       //printSolution(board);
        return true;
    }

    public static bool solveParallel()
    {
        List<Thread> tasks = new List<Thread>();
        for (int i = 0; i < N; i++)
        {
            Thread t = new Thread(() => solve(i, 1));
            t.Start();
            t.Join();
            tasks.Add(t);
        }
        return true;
    }

    public static void Main(String[] args)
    {
        // Sériové řešení pro na sobě nezávislý algoritmus
        var watch = System.Diagnostics.Stopwatch.StartNew();
        for (int i = 0; i < N; i++)
        {
            NQueen.solve(i, 1);
        }
        watch.Stop();
        Console.WriteLine("čas měření : " + watch.ElapsedMilliseconds);

        NQueen.count = 0;
        // Paralelní řešení pro na sobě nezávislý algoritmus
        watch.Restart();
        NQueen.solveParallel();
        watch.Stop();
        Console.WriteLine("čas měření : " + watch.ElapsedMilliseconds);
        //NQueen.solveNQ(-1, 0);
    }
}