import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MenuWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuWorld extends World
{
    private GreenfootImage bg;
    private Boat boat;
    /**
     * Constructor for objects of class MenuWorld.
     * 
     */
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(720, 720, 1); 
        Greenfoot.setSpeed(30);
        bg = new GreenfootImage("mbg.jpg");
        setBackground(bg);
        
        boat = new Boat();
        addObject(boat, 482, 488);
        
        //482, 488
    }
}
