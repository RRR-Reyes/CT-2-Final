import greenfoot.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ZombieWorld extends World {
    private List<Susceptible> activeSus;
    private List<Infected> activeInf;
    private List<IntelligentZombie> activeInz;
    private List<Removed> activeRmd;
    private int hourCount;
    private boolean keepGoing;
    private Counter susCounter, infCounter, inzCounter, rmdCounter, hourCounter;
    private GreenfootImage bg;

    public ZombieWorld(int pS, int pI, int pIZ) {
        super(12, 12, 60); // 12x12 grid, 60 pixels per cell

        Greenfoot.setSpeed(40);

        bg = new GreenfootImage("bg.png");
        setBackground(bg);

        activeSus = new ArrayList<>();
        activeInf = new ArrayList<>();
        activeInz = new ArrayList<>();
        activeRmd = new ArrayList<>();  
        keepGoing = true;
        hourCount = 0;

        // Initialize counters
        susCounter = new Counter("Susceptible: ", pS); // Reduced for simplicity
        infCounter = new Counter("Infected: ", pI);
        inzCounter = new Counter("Intelligent: ", pIZ);
        rmdCounter = new Counter("Removed: ", 0);
        hourCounter = new Counter("Hour: ", 0);

        // Position counters
        addObject(susCounter, 1, 0);
        addObject(infCounter, 3, 0);
        addObject(inzCounter, 5, 0);
        addObject(rmdCounter, 7, 0);
        addObject(hourCounter, 9, 0);

        // Initialize actors
        initializeActors(pS, pI, pIZ, 0, 1, 10);
    }

    private void initializeActors(int pS, int pI, int pIZ, int pR, int pMin, int pMax) {
        for (int s = 1; s <= pS; s++) {
            Susceptible sus = new Susceptible(s, pMin, pMax);
            activeSus.add(sus);
            addObject(sus, sus.getX(), sus.getY());
        }
        for (int i = 1; i <= pI; i++) {
            Infected inf = new Infected(i * 10, pMin, pMax);
            activeInf.add(inf);
            addObject(inf, inf.getX(), inf.getY());
        }
        for (int z = 1; z <= pIZ; z++) {
            IntelligentZombie inz = new IntelligentZombie(z * 100, pMin, pMax);
            activeInz.add(inz);
            addObject(inz, inz.getX(), inz.getY());
        }
        for (int r = 1; r <= pR; r++) {
            Removed rmd = new Removed(r * 1000, pMin, pMax, 0, 0);
            activeRmd.add(rmd);
            addObject(rmd, rmd.getX(), rmd.getY());
        }
    }

    public void act() {
        if (!keepGoing) {
            return;
        }

        hourCount++;
        hourCounter.setValue(hourCount);

        // Move all actors
        for (Susceptible s : new ArrayList<>(activeSus)) {
            if (s.isAlive()) {
                s.move();
            }
        }
        for (Infected i : new ArrayList<>(activeInf)) {
            if (i.isAlive()) {
                i.move();
            }
        }
        for (IntelligentZombie z : new ArrayList<>(activeInz)) {
            if (z.isAlive()) {
                z.move();
            }
        }

        // Go Through Susceptibles
        for (Susceptible s : new ArrayList<>(activeSus)) {
            if (!s.isAlive() || !activeSus.contains(s)) {
                continue;
            }

            // Encouter other Susceptible
            for (Susceptible os: new ArrayList<>(activeSus)){
                if (s != os && s.shareLocation(os)){
                    s.formGroup(os);
                }
            }
            // Encounter Infected
            for (Infected i : new ArrayList<>(activeInf)) {
                if (s.shareLocation(i)) {
                    s.attack(i);
                    if (i.Health <= 0) {
                        // Remove infected
                        removeObject(i);
                        activeInf.remove(i);

                        // Create removed
                        Removed rmd = new Removed(i.getID(), i.pMin, i.pMax, i.getX(), i.getY());
                        addObject(rmd, rmd.getX(), rmd.getY());
                        activeRmd.add(rmd);
                    } else if (i.Health > s.Health) {
                        // Infect susceptible
                        i.infect(s);
                        removeObject(s);
                        activeSus.remove(s);

                        // Create new infected
                        Infected newInf = new Infected(s.getID(), s.pMin, s.pMax);
                        newInf.setLocation(s.getX(), s.getY());
                        addObject(newInf, newInf.getX(), newInf.getY());
                        activeInf.add(newInf);
                        break;
                    }
                }
            }

            // Encounter IntelligentZombie
            for (IntelligentZombie z : new ArrayList<>(activeInz)) {
                if (s.shareLocation(z)) {
                    if (Greenfoot.getRandomNumber(100) < s.Health){
                        s.attack(z);
                        if (z.Health <= 0){
                            removeObject(z);
                            activeInz.remove(z);
                            
                            //Create removed
                            Removed rmd = new Removed(z.getID(), z.pMin, z.pMax, z.getX(), z.getY());
                            addObject(rmd, rmd.getX(), rmd.getY());
                            activeRmd.add(rmd);
                        } 
                    } else if (z.Health > s.Health){
                        z.infect(s);
                        removeObject(s);
                        activeSus.remove(s);

                        // Create new infected
                        Infected newInf = new Infected(s.getID(), s.pMin, s.pMax);
                        newInf.setLocation(s.getX(), s.getY());
                        addObject(newInf, newInf.getX(), newInf.getY());
                        activeInf.add(newInf);
                        break;
                    } else {
                        s.runAway();
                    }
                }
            }

            s.scavenge();
        }

        // Go Through  Infected
        for (Infected i : new ArrayList<>(activeInf)) {
            if (!i.isAlive() || !activeInf.contains(i)) {
                removeObject(i);
                activeInf.remove(i);

                // Create removed
                Removed rmd = new Removed(i.getID(), i.pMin, i.pMax, i.getX(), i.getY());
                addObject(rmd, rmd.getX(), rmd.getY());
                activeRmd.add(rmd);
                continue;
            }

            if (i.mutate() && activeInf.contains(i)) {
                // Remove infected
                removeObject(i);
                activeInf.remove(i);

                // Create intelligent zombie
                IntelligentZombie newInz = new IntelligentZombie(i.getID(), i.pMin, i.pMax);
                newInz.setLocation(i.getX(), i.getY());
                addObject(newInz, newInz.getX(), newInz.getY());
                activeInz.add(newInz);
                continue;
            }

            for (Infected oi : new ArrayList<>(activeInf)) {
                if (i != oi && i.shareLocation(oi)) {
                    i.formGroup(oi);
                }
            }

            for (IntelligentZombie oz : new ArrayList<>(activeInz)) {
                if (i.shareLocation(oz)) {
                    i.formGroup(oz);
                }
            }

            for (Susceptible s : new ArrayList<>(activeSus)) {
                if (i.shareLocation(s)) {
                    i.attack(s);
                    if (s.Health <= 0) {
                        // Remove susceptible
                        removeObject(s);
                        activeSus.remove(s);

                        // Create removed
                        Removed rmd = new Removed(s.getID(), s.pMin, s.pMax, s.getX(), s.getY());
                        addObject(rmd, rmd.getX(), rmd.getY());
                        activeRmd.add(rmd);
                    } else if (i.Health > s.Health) {
                        // Infect susceptible
                        i.infect(s);
                        removeObject(s);
                        activeSus.remove(s);

                        // Create new infected
                        Infected newInf = new Infected(s.getID(), s.pMin, s.pMax);
                        newInf.setLocation(s.getX(), s.getY());
                        addObject(newInf, newInf.getX(), newInf.getY());
                        activeInf.add(newInf);
                    }
                }
            }
        }

        // Go Through IntelligentZombies
        for (IntelligentZombie z : new ArrayList<>(activeInz)) {
            if (!z.isAlive() || !activeInz.contains(z)) {
                removeObject(z);
                activeInz.remove(z);

                // Create removed
                Removed rmd = new Removed(z.getID(), z.pMin, z.pMax, z.getX(), z.getY());
                addObject(rmd, rmd.getX(), rmd.getY());
                activeRmd.add(rmd);
                continue;
            }

            for (Infected i : new ArrayList<>(activeInf)) {
                if (z.shareLocation(i)) {
                    z.formGroup(i);
                }
            }
            for (IntelligentZombie oz : new ArrayList<>(activeInz)) {
                if (z != oz && z.shareLocation(oz)) {
                    z.formGroup(oz);
                }
            }

            for (Susceptible s: new ArrayList<>(activeSus)) {
                if (z.shareLocation(s)) {
                    if (z.checkGroupSize() < s.checkGroupSize()) {
                        if (Greenfoot.getRandomNumber(100) < z.Health) {
                            z.lure(activeSus);
                            if (Greenfoot.getRandomNumber(100) < s.Health){
                                s.runAway(); // Runs Away if Health Greater
                            } else {
                                z.attack(s);
                                if (s.Health <= 0) {
                                    // Remove susceptible
                                    removeObject(s);
                                    activeSus.remove(s);

                                    // Create removed
                                    Removed rmd = new Removed(s.getID(), s.pMin, s.pMax, s.getX(), s.getY());
                                    addObject(rmd, rmd.getX(), rmd.getY());
                                    activeRmd.add(rmd);
                                } else if (s.Health < z.Health) {
                                    // Infect susceptible
                                    z.infect(s);
                                    removeObject(s);
                                    activeSus.remove(s);

                                    // Create new infected
                                    Infected newInf = new Infected(s.getID(), s.pMin, s.pMax);
                                    newInf.setLocation(s.getX(), s.getY());
                                    addObject(newInf, newInf.getX(), newInf.getY());
                                    activeInf.add(newInf);
                                }
                            }
                        } else {
                            s.runAway();
                        }
                    } else {
                        z.attack(s);
                        if (s.Health <= 0) {
                            // Remove susceptible
                            removeObject(s);
                            activeSus.remove(s);

                            // Create removed
                            Removed rmd = new Removed(s.getID(), s.pMin, s.pMax, s.getX(), s.getY());
                            addObject(rmd, rmd.getX(), rmd.getY());
                            activeRmd.add(rmd);
                        } else if (s.Health < z.Health) {
                            // Infect susceptible
                            z.infect(s);
                            removeObject(s);
                            activeSus.remove(s);

                            // Create new infected
                            Infected newInf = new Infected(s.getID(), s.pMin, s.pMax);
                            newInf.setLocation(s.getX(), s.getY());
                            addObject(newInf, newInf.getX(), newInf.getY());
                            activeInf.add(newInf);
                        } else {
                            s.runAway();
                        }
                    }
                }
            }
        }

        // Update counters
        susCounter.setValue(activeSus.size());
        infCounter.setValue(activeInf.size());
        inzCounter.setValue(activeInz.size());
        rmdCounter.setValue(activeRmd.size());

        // Check for end
        if (activeSus.size() == 0) {
            keepGoing = false;
            Greenfoot.setWorld(new WinnerWorld(false));
            JOptionPane.showMessageDialog(null, "Total Hours: " + hourCount + "\n" + 
                "Total Days: " + (hourCount / 24) + "\n" + 
                "Final Counts: S=" + activeSus.size() + " , I=" + activeInf.size() + " | IZ=" + activeInz.size() + " | R=" + activeRmd.size());
            //System.out.println("Total Hours: " + hourCount);
            //System.out.println("Total Days: " + (hourCount / 24));
            //System.out.println("Final Counts: S=" + activeSus.size() + ", I=" + activeInf.size() + ", IZ=" + activeInz.size() + ", R=" + activeRmd.size());
        } else if (activeInf.size() == 0 && activeInz.size() == 0){
            keepGoing = false;
            Greenfoot.setWorld(new WinnerWorld(true));
            JOptionPane.showMessageDialog(null, "Total Hours: " + hourCount + "\n" + 
                "Total Days: " + (hourCount / 24) + "\n" + 
                "Final Counts: S=" + activeSus.size() + " , I=" + activeInf.size() + " , IZ=" + activeInz.size() + " , R=" + activeRmd.size());            
            //System.out.println("Total Hours: " + hourCount);
            //System.out.println("Total Days: " + (hourCount / 24));
            //System.out.println("Final Counts: S=" + activeSus.size() + ", I=" + activeInf.size() + ", IZ=" + activeInz.size() + ", R=" + activeRmd.size());
        }
    }
}
