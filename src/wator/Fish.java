package wator;

import java.awt.Color;

/**
 * @author David Matuszek
 */
public class Fish extends Denizen {
    
    /**
     * Constructs a Fish at a given (row, column) location. 
     * @param row The row to contain the Fish.
     * @param column The column to contain the Fish.
     */
     public Fish(int row, int column) {
        super(row, column);
        timeToGestation = Parameters.fishGestationPeriod; //removed timetostarvation
    }
    
    @Override
    public Color getColor() {
        return Color.blue;
    }

    /* (non-Javadoc)
     * @see wator.Denizen#canMove(wator.Ocean, wator.Direction)
     */
    @Override
    public boolean canMove(Ocean ocean, Direction direction) {
        if (justMoved) {
            justMoved = false;
            return false;
        }
        Denizen neighbor = ocean.get(myRow, myColumn, direction);
        return neighbor == WATER;
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#moveAndMaybeGiveBirth(wator.Ocean, wator.Direction)
     */
    @Override
    public void moveAndMaybeGiveBirth(Ocean ocean, Direction direction) {
        if (timeToGestation <= 0) {
            giveBirth(ocean, myRow, myColumn);
            this.timeToGestation = Parameters.fishGestationPeriod;
        } else {
            ocean.set(myRow, myColumn, WATER);
        }
        myRow = (myRow + direction.dy) % ocean.getNRows();
        myColumn = (myColumn + direction.dx) % ocean.getNColumns();
        ocean.set(myRow, myColumn, this);
        justMoved = true;
        System.out.println(this + " just moved.");
    }

    /* (non-Javadoc)
     * @see wator.Denizen#giveBirth(wator.Ocean, int, int)
     */
    @Override
    public Fish giveBirth(Ocean ocean, int row, int column) {
        Fish babyFish = new Fish(row, column);
        ocean.set(row, column, babyFish);
        return babyFish;
    }
    
    /* (non-Javadoc)
     * @see wator.Denizen#toString()
     */
    @Override
    public String toString() {
        return "Fish at (" + myRow + ", " + myColumn + ")";
    }
}
