import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JOptionPane;  // For dialog input

public class Boat extends Actor
{
    private GreenfootImage boat;
    
    public Boat(){
        boat = new GreenfootImage("boat.png");
        setImage(boat);
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this)){
            try {
                // Ask for input using Swing JOptionPane
                String inputPS = JOptionPane.showInputDialog("Enter number of Susceptible:");
                String inputPI = JOptionPane.showInputDialog("Enter number of Infected:");
                String inputPIZ = JOptionPane.showInputDialog("Enter number of Intelligent Zombies:");

                // Check if user pressed Cancel
                if (inputPS == null || inputPI == null || inputPIZ == null) {
                    JOptionPane.showMessageDialog(null, "Input cancelled.");
                    return;
                }

                // Convert inputs to integers
                int pS = Integer.parseInt(inputPS);
                int pI = Integer.parseInt(inputPI);
                int pIZ = Integer.parseInt(inputPIZ);
                
                Tracker.setValues(pS,pI, pIZ);

                // Switch to the new ZombieWorld with the provided values
                Greenfoot.setWorld(new ZombieWorld(pS, pI, pIZ));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid whole numbers only.");
            }
        }
    }
}