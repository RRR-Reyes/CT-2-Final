import greenfoot.*;

public class Susceptible extends Person {
    private int Weapon;
    
    public Susceptible(int pID, int pMin, int pMax) {
        super(pID, pMin, pMax);
        this.Weapon = Greenfoot.getRandomNumber(11) + 10; // 0-20
        setImage("Susceptible.png"); // Red square
    }
    
    public void act() {
        // Handled by ZombieWorld
    }
    
    public void move() {
        int xStep = Greenfoot.getRandomNumber(11) - 5; // -5 to 5
        int yStep = Greenfoot.getRandomNumber(11) - 5; // -5 to 5
        int newX = x + xStep;
        int newY = y + yStep;
        if (pMin < newX && newX < pMax) {
            x = newX;
        }
        if (pMin < newY && newY < pMax) {
            y = newY;
        }
        setLocation(x, y);
    }
    
    public void attack(Person target) {
        int baseDmg = Greenfoot.getRandomNumber(21) + 10; // 10-30
        int totalDmg = baseDmg + Weapon;
        target.takeDamage(totalDmg);
    }
    
    public void scavenge() {
        Health += Greenfoot.getRandomNumber(10) - 5; // -5-5
        if (Health > 99) {
            Health = 99;
        } else if (Health < 5){
            Health = 5;
        }
    }
    
    public void runAway() {
        move();
        Health -= Greenfoot.getRandomNumber(5) + 5; // 5-10
    }
}