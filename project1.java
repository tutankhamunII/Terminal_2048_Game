//CSC172 Project 1
//Written by Hesham Elshafey

import java.util.*;
import java.io.IOException;  
public class project1{
    static Integer[][] grid = new Integer[4][4];
    static char a = '|';
    static char b = '-';
    static char c = '*';
    static Random random = new Random();
 
    //Initialize every element of the grid array to zero.
    static void initGrid()
    {
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                grid[x][y] = 0;
            }
        }
    }
    //Finds maximum number inside the grid array.
    static int maxNumber()
    {
        int max = 0;
        for(int x = 0; x < 4; x ++)
        {
            for(int y = 0; y < 4; y++)
            {
                if(max < grid[x][y])
                {
                    max = grid[x][y]; //Reassinging the max variable to the grid element whenever the grid element is larger.
                }
            }
        }
        return max;
    }
    //Prints the maximum number and number of valid moves whenever neededs. 
    static void PrintStats(int numberOfMoves)
    {
        System.out.println("The maximum number on the board is : " + maxNumber());
        System.out.println("The number of valid moves is: " + numberOfMoves);
    }
    //Prints the whole game grid.
    static void PrintGrid()
    {
        System.out.print("\n\n");
        //For loop to print the top frame.
        for(int z = 0; z < 6; z++)
        {
            System.out.printf("%-8c",b);
        }
        System.out.println();
        //Nested for loops to print the array elements. If element is 0, print an astrike instead.
        for(int x = 0; x < 4; x++)
        {
            System.out.printf("%-8c", a);
            for(int y = 0; y < 4; y++)
            {
                if(grid[x][y] == 0)
                {
                    System.out.printf("%-8c",c);
                }
                else
                {
                    System.out.printf("%-8d",grid[x][y]);
                }
            }
            System.out.print(a);
            System.out.println();
        }
        //For loop to print the bottom frame
        for(int z = 0; z < 6; z++)
        {
            System.out.printf("%-8c", b);
        }
        System.out.println();
    }
    //Function that evaluate the move input and call appropriate move functions.
    //When this function returns false, this means that a move is invalid (no tiles can move or merge) or user input is invalid.
    //It returns true otherwise (moveDir functions return true when move is successfully executed, false otherwise).
    static boolean move(String direction) {
        System.out.println("The pressed key is: " + direction);
        if (direction.equals("w"))
        {
            return moveUp();
        }
        else if (direction.equals("s")) 
        {
            return moveDown();
        } 
        else if (direction.equals("a"))
        {
            return moveLeft();
        } 
        else if (direction.equals("d")) 
        {
            return moveRight();
        }
        return false;
      }
    //Function to add either a 2 or 4 at a random empty index of the array.
    static void addrandom()
    {
        List<int[]> emptytiles = new ArrayList<>();
        //This for loop find the grid indeices that are 0 and saves these indices in list of int arrays to be used during insertion.
        for (int i = 0; i < 4; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                if (grid[i][j] == 0) 
                {
                emptytiles.add(new int[] { i, j });
                }
            }
        }
        //checks if the list is empty == no empty tiles on the grid.
        if (emptytiles.isEmpty()) 
        {
          return;
        }
        int randomIndex = (int) (Math.random() * emptytiles.size()); // generates a random number that is equal to one of list of int arrays indeices.
        int[] tile = emptytiles.get(randomIndex);//assign an int array to the randomlly selected index array from the list in previous step.
        double randomValue = Math.random();//generate a random number between 0 and 1
        if (randomValue < 0.8) //this statements will result in 2 having 80% probabiiity of being selected compared with 4.
        {
            grid[tile[0]][tile[1]] = 2; // the tile array elements are the empty indeices of the grid array that was calculated in the previous for loop.
        } 
        else 
        {
            grid[tile[0]][tile[1]] = 4;
        }
    }
    //Function that moves all grid indices up and merge the equal indices.
    static boolean moveUp()
    {
        boolean moved = false; //Variable used to indicate if a move was made or not.
        for (int x = 0; x < 4; x++) 
        {
            for (int y = 0; y < 4; y++) 
            {
                if (grid[y][x] == 0) 
                {
                    continue;
                }
                int m = y;
                //This while loop will set the variable "m" to the fariest row-wise index in the up direction so I can reassign the grid index using this variable. 
                while (m > 0 && grid[m - 1][x] == 0) 
                {
                    m--;
                }
                //Statement that pushes indexs up.
                if (m != y) 
                {
                    moved = true;
                    grid[m][x] = grid[y][x];
                    grid[y][x] = 0;
                }
                //statement to check if indeices are equal and merge them if so.
                if (m > 0 && grid[m][x] == grid[m - 1][x]) 
                {
                    moved = true;
                    grid[m - 1][x] *= 2; // all number are multiples of 2!
                    grid[m][x] = 0;
                }
            }
        }
        return moved;
    }
    //Function to move down. It is essentially a flipped version of up function.
    static boolean moveDown()
    {
        boolean moved = false;
        for(int x = 0; x < 4; x++)
        {
            for(int y = 0; y < 4; y++)
            {
                if(grid[y][x] == 0)
                {
                    continue;
                }
                int m = y + 1;
                while (m < 4 && grid[m][x] == 0)
                {
                    m++;
                }
                if(m - 1 != y)
                {
                    moved = true;
                    grid[m - 1][x] = grid[y][x];
                    grid[y][x] = 0;                
                }
                if(m < 4 && grid[m][x] == grid[y][x])
                {
                    moved = true;
                    grid[m][x] *= 2;
                    grid[y][x] = 0;
                }
            }
        }
        return moved;     
    }
    //Function to move left, same logic like up and down funcitons but works on the other dimension of the array.
    static boolean moveLeft()
    {
        boolean moved = false;
        for (int x = 0; x < 4; x++) 
        {
            for (int y = 0; y < 4; y++) 
            {
                if (grid[x][y] != 0) 
                {
                    int m = y - 1;
                    while (m >= 0 && grid[x][m] == 0) 
                    {
                        m--;
                    }
                    if (m >= 0 && grid[x][m] == grid[x][y]) 
                    {
                        moved = true;
                        grid[x][m] *= 2;
                        grid[x][y] = 0;
                    } 
                    else if (m + 1 != y) 
                    {
                        grid[x][m + 1] = grid[x][y];
                        grid[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }
    //Function to move right, same logic, but flipped version of left.
    static boolean moveRight()
    {
        boolean moved = false;
        for (int x = 0; x < 4; x++)
        {
          for (int y = 4 - 2; y >= 0; y--) 
          {
                if (grid[x][y] != 0) 
                {
                    int m = y + 1;
                    while (m < 4 && grid[x][m] == 0) 
                    {
                        m++;
                    }
                    if (m < 4 && grid[x][m] == grid[x][y]) 
                    {
                        moved = true;
                        grid[x][m] *= 2;
                        grid[x][y] = 0;
                    }
                    else if (m - 1 != y) 
                    {
                        grid[x][m - 1] = grid[x][y];
                        grid[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
      }
    //Function that return true only if there is no empty tiles and there are no tiles that can be merged.
    //The if statement chain checks of any two adjacent tiles to be equal or not, return false means they still can be merged.
    //It also returns false right away when some tile = 0 (empty)
    static boolean isGameOver()
    {
        for (int x = 0; x < 4; x++) 
        {
            for (int y = 0; y < 4; y++) 
            {
                if (grid[x][y] == 0) 
                {
                    return false;
                }
                if (x > 0 && grid[x][y] == grid[x - 1][y]) 
                {
                    return false;
                }
                if (x < 4 - 1 && grid[x][y] == grid[x + 1][y]) 
                {
                    return false;
                }
                if (y > 0 && grid[x][y] == grid[x][y - 1]) 
                {
                    return false;
                }
                if (y < 4 - 1 && grid[x][y] == grid[x][y + 1]) 
                {
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args)throws IOException, InterruptedException   
    {
        //starts by initializing the array, add two random numebr and print the grid.
        Scanner scan = new Scanner(System.in);
        int numberOfMoves = 0;
        initGrid();
        addrandom();
        addrandom();
        PrintGrid();
        while(isGameOver() == false) //runs as long as game is not over.
        {
            System.out.print("Input your move: ");
            String userinput = scan.nextLine();
            if(userinput.equals("q"))
            {
                System.out.print("\nAre you sure you want to quit ? Right yes if so : "); //confirmation.
                if(scan.nextLine().equals("yes"))
                {
                    System.out.println("Bye!");
                    PrintStats(numberOfMoves); //print states before leaving.
                    scan.close();
                    return;
                }
                else
                {
                    System.out.println("Your game will continue!");
                    continue;
                }
            }
            else if(userinput.equals("r"))
            {
                System.out.print("\nAre you sure you want to restart the game? write yes if so : "); //confirmation
                if(scan.nextLine().equals("yes"))
                {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Clears terminal
                    System.out.println("Restarting the game!");
                    PrintStats(numberOfMoves);
                    //restart everything.
                    numberOfMoves = 0;
                    initGrid();
                    addrandom();
                    addrandom();
                    PrintGrid();
                }
                else
                {
                    System.out.println("Your game will continue!");
                }
            }
            else
            {
                if(move(userinput) == false)
                {
                    System.out.println("Wrong input or wrong move!");
                }
                else
                {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    numberOfMoves++;
                    PrintStats(numberOfMoves); 
                    addrandom();
                    PrintGrid();   
                }  
            }
             
        }
        if(maxNumber() == 2048)
        {
            System.out.println("you won !! probably using a script, cheater :D");
            scan.close();
            return; 
        }
        else
        {
            System.out.println("Game over!");
            PrintStats(numberOfMoves);
            scan.close();
            return;
        }
    }

}