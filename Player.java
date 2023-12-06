public class Player
{
    // Static constants for the Player class
    public static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    public static final int NUM_SHIPS = 5;
    private static final int MAX_HITS = computeMaxHits();

    // Direction constants
    private static final int UNSET = -1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private Grid myGrid;
    private Grid opponentGrid;
    private Ship[] myShips;
    private int numShips;
    private int totalHitsTaken;
    private int totalHitsDelivered;
    
    public Player()
    {
        myGrid = new Grid();
        opponentGrid = new Grid();
        myShips = new Ship[NUM_SHIPS];
        numShips = 0;
        totalHitsTaken = 0;
        totalHitsDelivered = 0;
    }

    private static int computeMaxHits()
    {
        int sum = 0;
        for(int i = 0; i < SHIP_LENGTHS.length; i++)
        {
            sum += SHIP_LENGTHS[i];
        }
        return sum;
    }
    
    public void initializeShipsRandomly()
    {
        for(int i = 0; i < SHIP_LENGTHS.length; i++)
        {
            int length = SHIP_LENGTHS[i];
            int dir;
            int row;
            int col;
            Ship ship = new Ship(length);
            
            while(true)
            {
                dir = Randomizer.nextInt(HORIZONTAL, VERTICAL);
                row = Randomizer.nextInt(0, myGrid.numRows() - 1);
                col = Randomizer.nextInt(0, myGrid.numCols() - 1);
                ship.setDirection(dir);
                ship.setLocation(row, col);
                if(addShip(ship))
                {
                    break;
                }
            }
        }
    }
    
    // Adds a ship if it's a legal placement
    // Returns whether the ship was successfully added
    public boolean addShip(Ship s)
    {
        if(myGrid.canPlaceShip(s))
        {
            myGrid.addShip(s);
            myShips[numShips] = s;
            numShips++;
            return true;
        }
        return false;
    }
    
    public int getRandomRowGuess()
    {
        return Randomizer.nextInt(0, opponentGrid.numRows()-1);
    }
    
    public int getRandomColGuess()
    {
        return Randomizer.nextInt(0, opponentGrid.numCols()-1);
    }
    
    public boolean makeGuess(int row, int col, Player other)
    {
        if(opponentGrid.alreadyGuessed(row, col))
        {
            return false;
        }
        boolean hit = other.recordOpponentGuess(row, col);
        markGuess(row, col, hit);
        return hit;
    }
    
    private void markGuess(int row, int col, boolean val)
    {
        opponentGrid.setShip(row, col, val);
        if(val)
        {
            opponentGrid.markHit(row, col);
            totalHitsDelivered++;
        }
        else
        {
            opponentGrid.markMiss(row, col);
        }
    }
    
    /*
     * Takes in an oppnent guess for a row, col location.
     * Records the guess, and returns a boolean indicating
     * whether the guess was a hit.
     */
    private boolean recordOpponentGuess(int row, int col)
    {
        if(myGrid.alreadyGuessed(row, col))
        {
            return false;
        }
        if(myGrid.hasShip(row, col))
        {
            myGrid.markHit(row, col);
            totalHitsTaken++;
        }
        else
        {
            myGrid.markMiss(row, col);
        }
        return myGrid.hasShip(row, col);
    }
    
    public void printMyShips()
    {
        myGrid.printShips();
    }
    
    public void printOpponentGuesses()
    {
        myGrid.printStatus();
    }
    
    public void printMyGuesses()
    {
        opponentGrid.printStatus();
    }
    
    public void printHitsDelivered()
    {
        System.out.println("You only need " + (MAX_HITS - totalHitsDelivered) + " more hits to win!");
        System.out.println("--------------------------");
    }
    
    public boolean hasWon()
    {
        return totalHitsDelivered == MAX_HITS;
    }
    
    public boolean hasLost()
    {
        return totalHitsTaken == MAX_HITS;
    }
    
    public void chooseShipLocation(Ship s, int row, int col, int direction)
    {
        s.setLocation(row, col);
        s.setDirection(direction);
        myGrid.addShip(s);
        myShips[numShips] = s;
        numShips++;
    }
}
