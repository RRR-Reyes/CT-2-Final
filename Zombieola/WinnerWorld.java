import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WinnerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WinnerWorld extends World
{
    private GreenfootImage sbg;
    private GreenfootImage zbg;
    private Replay replay;
    /**
     * Constructor for objects of class WinnerWorld.
     * 
     */
    public WinnerWorld(boolean i)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(720, 720, 1); 
        sbg = new GreenfootImage("sbg.png");
        zbg = new GreenfootImage("zbg.png");
        
        replay = new Replay();
        addObject(replay, 449,477);
        
        if (i == true){
            setBackground(sbg);
        } else {
            setBackground(zbg);
        }
    }
}
