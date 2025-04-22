import greenfoot.*;

public class Infected extends Person {
    protected boolean mutated;
    
    public Infected(int pID, int pMin, int pMax) {
        super(pID, pMin, pMax);
        this.mutated = false;
        setImage("Infected.png"); // Orange square
    }
    
    public void act() {
        // Handled by ZombieWorld
    }
    
    public void move() {
        int xStep = Greenfoot.getRandomNumber(3) - 1; // -1 to 1
        int yStep = Greenfoot.getRandomNumber(3) - 1; // -1 to 1
        int newX = x + xStep;
        int newY = y + yStep;
        if (pMin <= newX && newX <= pMax) {
            x = newX;
        }
        if (pMin <= newY && newY <= pMax) {
            y = newY;
        }
        setLocation(x, y);
    }
    
    public void attack(Person target) {
        int baseDmg = Greenfoot.getRandomNumber(21) + 20; // 20-40
        target.takeDamage(baseDmg);
    }
    
    public void infect(Person target) {
        target.Status = false;
        Health += Greenfoot.getRandomNumber(16) + 5; // 5-20
    }
    
    public boolean mutate() {
        int chance = Greenfoot.getRandomNumber(101); // 0-100
        if (chance > 98) { //2% chance
            mutated = true;
        }
        return mutated;
    }
}