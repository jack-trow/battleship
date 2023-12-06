import java.util.*;
public class Battleship extends ConsoleProgram
{
    private static final int MAX_COL = 10;
    private static final char MAX_ROW = 'J';

    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    
    public void run()
    {
        START:
        while(true)
        {
            System.out.print("\033\143");
            System.out.println("=======================\nWelcome to Battle Ship!\n=======================");
    
            Player human = new Player();
            Player computer = new Player();
            setUpShips(human, computer);
            try 
            {
                Thread.sleep(1000);
            } 
            catch (InterruptedException e) 
            {
            Thread.currentThread().interrupt();
            }
            
            readLine("Hit enter for your turn.");
            boolean gameOver = false;
    
            while(!gameOver)
            {
                gameOver = playRound(human, computer);
            }
            if(human.hasWon())
            {
                System.out.println("You beat the computer!");
            }
            else
            {
                System.out.println("You lost to a bot.");
                
            }
    
            System.out.println("Thanks for playing!");
            readLine("\nPress enter to start another game!");
            continue START;
        }
    }
    private void setUpShips(Player human, Player computer)
    {
        System.out.println("Placing your ships...");
        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) 
        {
        Thread.currentThread().interrupt();
        }
        System.out.println("Placing enemy ships...");
        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) 
        {
        Thread.currentThread().interrupt();
        }
        System.out.println("--------------------------");
        System.out.println("Your ships have been placed");
        System.out.println("Your board is below\n--------------------------");
        
        computer.initializeShipsRandomly();
        human.initializeShipsRandomly();
        
        human.printMyShips();
    }
    
    
    private int readCol()
    {
        while(true)
        {
            int col = readInt("Which column? (1-" + MAX_COL + ") ");
            System.out.println("--------------------------");
            if(col >= 1 && col <= MAX_COL)
            {
                return col-1;
            }
            System.out.println("Invalid column, please try again.");
        }
    }

    private int readRow()
    {
        while(true)
        {
            String row = readLine("Which row? (A-" + MAX_ROW + ") ");
            row = row.toUpperCase();
            if(row.length() > 0)
            {
                char ch = row.charAt(0);
                if(ch >= 'A' && ch <= MAX_ROW)
                {
                    return ch - 'A';
                }
            }
            System.out.println("Invalid row, please try again.");
        }
    }
    // Plays a round of battle ship, returns true if the game
    // is over, false otherwise.
    private boolean playRound(Player human, Player computer)
    {
        humanTurn(human, computer);

        if(human.hasWon())
        {
            return true;
        }
        readLine("Hit enter to continue.");
        computerTurn(human, computer);
        
        return computer.hasWon();
        
    }

    private void computerTurn(Player human, Player computer)
    {
        int row = computer.getRandomRowGuess();
        int col = computer.getRandomColGuess();
        System.out.print("\033\143");
        for(int i = 0; i < 3; i++)
        {
            System.out.print("\033\143");
            System.out.println("Computer is thinking");
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException e) 
            {
            Thread.currentThread().interrupt();
            }
            System.out.print("\033\143");
            System.out.println("Computer is thinking.");
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException e) 
            {
            Thread.currentThread().interrupt();
            }
            System.out.print("\033\143");
            System.out.println("Computer is thinking..");
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException e) 
            {
            Thread.currentThread().interrupt();
            }
            System.out.print("\033\143");
            System.out.println("Computer is thinking...");
            try 
            {
                Thread.sleep(500);
            } 
            catch (InterruptedException e) 
            {
            Thread.currentThread().interrupt();
            }
        }
        System.out.println("Computer guesses row " + (row + 1) + " and column " + (col + 1));

        boolean hit = computer.makeGuess(row, col, human);
        if(hit)
        {
            System.out.println("Computer hit your poorly placed ship.");
        }
        else
        {
            System.out.println("Computer missed every ship!");
        }
        
        computer.printMyGuesses();
    }
    
    private void humanTurn(Player human, Player computer)
    {
        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException e) 
        {
        Thread.currentThread().interrupt();
        }
        System.out.println("--------------------------");
        System.out.println("Your Guesses:");
        human.printMyGuesses();
        System.out.println("Hurry up and guess!");
        int row = readRow();
        int col = readCol();

        boolean hit = human.makeGuess(row, col, computer);
        if(hit)
        {
            System.out.println("Good job! You hit the enemy's ship!");
            human.printHitsDelivered();
        }
        else
        {
            System.out.println("You hit a ship! Just kidding.");
        }
        
        human.printMyGuesses();
    }
}
