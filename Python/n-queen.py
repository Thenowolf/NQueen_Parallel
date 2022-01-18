import threading
import time
from datetime import datetime
from typing import Counter

global N
N = 8
count = 0
 
def printSolution(board):
    for i in range(N):
        for j in range(N):
            print (board[i][j], end= " "),
            if j == (N-1):
                print()
 
def isSafe(board, row, col):
 
    # Kontrola na levo
    for i in range(col):
        if board[row][i] == 1:
            return False
 
    # Kontrola horní levá diagonála
    for i, j in zip(range(row, -1, -1), range(col, -1, -1)):
        if board[i][j] == 1:
            return False
 
    # Kontrola dolní levá diagonála
    for i, j in zip(range(row, N, 1), range(col, -1, -1)):
        if board[i][j] == 1:
            return False
 
    return True
 
def solveNQUtil(board, col):
    if col >= N:
        printSolution(board)
        print("----------")
        globals()['count'] = count + 1
 
    for i in range(N):
 
        if isSafe(board, i, col):
            board[i][col] = 1

            if solveNQUtil(board, col + 1) == True:
                return True

            board[i][col] = 0
 
    return False
 
def solve(row, col):
    board = [['0' for x in range(N)] for y in range(N)]
    if row != -1:
        board[row][0] = 1
 
    if solveNQUtil(board, col) == False:
        print("Reseni neexistuje")
        print("Pocet reseni : " + str(count))
        return False
    
 

def solveParallel():
    threads = []
    for i in range(N):    
        t = threading.Thread(target=solve, args=(i, 1))
        t.start()
        t.join()
        threads.append(t)
 

#solve(-1,0)
#Sériové řešení pro na sobě nezávislý algoritmus
start = datetime.now()
for i in range(N):
    solve(i,1)
end = datetime.now()
print("čas měření : " + str(end-start))

count = 0

# Paralelní řešení pro na sobě nezávislý algoritmus
start = datetime.now()
solveParallel()
end = datetime.now()
print("čas měření : " + str(end-start))