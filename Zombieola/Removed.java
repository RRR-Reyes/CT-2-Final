import greenfoot.*;

public class Removed extends Person {
    public Removed(int pID, int pMin, int pMax, int x, int y) {
        super(pID, pMin, pMax);
        this.Status = false;
        this.Health = 0;
        this.x = x;
        this.y = y;
        this.setLocation(x, y);
        setImage("removeds.png"); 
    }
    
    public void act() {
        // Do nothing
    }
    
    public void move() {
        // Do nothing
    }
    
    public void attack(Person target) {
        // Do nothing
    }
}