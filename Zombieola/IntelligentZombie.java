import greenfoot.*;

public class IntelligentZombie extends Infected {
    private int Weapon;
    
    public IntelligentZombie(int pID, int pMin, int pMax) {
        super(pID, pMin, pMax);
        this.Weapon = Greenfoot.getRandomNumber(11) + 20; // 20-30
        this.mutated = true;
        setImage("IntelligentZombie.png"); 
    }
    
    public void act() {
        // Handled by ZombieWorld
    }
    
    public void move() {
        int xStep = Greenfoot.getRandomNumber(5) - 2; // -2 to 2
        int yStep = Greenfoot.getRandomNumber(5) - 2; // -2 to 2
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
        int baseDmg = Greenfoot.getRandomNumber(21) + 30; // 30-50
        int totalDmg = baseDmg + Weapon;
        target.takeDamage(totalDmg);
    }
    
    public void lure(java.util.List<Susceptible> sus) {
        for (Susceptible s : sus) {
            if (shareLocation(s)) {
                if (anyInGroup(s, sus)) {
                    
                        for (Susceptible groupMember : sus) {
                            int chance = Greenfoot.getRandomNumber(100) + 1; // 1-100
                            if (chance <= Health) {
                                if (groupMember.Group.contains(s.ID)) {
                                    groupMember.x = x;
                                    groupMember.y = y;
                                    groupMember.setLocation(x, y);
                                }
                            }
                        }
                }
            }
        }
    }
    
    private boolean anyInGroup(Susceptible s, java.util.List<Susceptible> sus) {
        for (Susceptible other : sus) {
            if (other != s && other.Group.contains(s.ID)) {
                return true;
            }
        }
        return false;
    }
}