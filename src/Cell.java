public class Cell {
    public static final String BLANK = "BLANK";
    public static final String HIT   = "HIT";
    public static final String MISS  = "MISS";

    private String state;
    private Ship   ship;

    public Cell() {
        state = BLANK;
        ship  = null;
    }

    public String getState()          { return state; }
    public void   setState(String s)  { state = s; }

    public boolean hasShip()          { return ship != null; }
    public Ship    getShip()          { return ship; }
    public void    setShip(Ship ship) { this.ship = ship; }

    public void reset() {
        state = BLANK;
        ship  = null;
    }
}