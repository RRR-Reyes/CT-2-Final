import greenfoot.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Person extends Actor {
    public int ID;
    public int Age;
    public int Health;
    public Set<Integer> Group;
    public boolean Status;
    public int x, y;
    public int pMin, pMax;
    
    public Person(int pID, int pMin, int pMax) {
        this.ID = pID;
        this.Age = Greenfoot.getRandomNumber(48) + 18; // 18-65
        this.Health = 100 - Age; // Based on Age
        this.Group = new HashSet<>();
        this.Group.add(pID);
        this.Status = true;
        this.pMin = pMin;
        this.pMax = pMax;
        this.x = Greenfoot.getRandomNumber(pMax-pMin) + pMin;
        this.y = Greenfoot.getRandomNumber(pMax-pMin) + pMin;
        setLocation(x, y);
    }
    
    
    public abstract void move();
    
    public abstract void attack(Person target);
    
    public void formGroup(Person other) {
        Set<Integer> combined = new HashSet<>(Group);
        combined.addAll(other.Group);
        this.Group = combined;
        other.Group = combined;
    }
    
    public void takeDamage(int dmg) {
        Health -= dmg;
        if (Health <= 0) {
            Status = false;
        }
    }
    
    public boolean isAlive() {
        return Status;
    }
    
    public boolean shareLocation(Person other) {
        return x == other.x && y == other.y;
    }
    
    public int checkGroupSize() {
        return Group.size();
    }
    
    public int getID() {
        return ID;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}