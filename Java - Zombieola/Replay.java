import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Replay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Replay extends Actor
{
    public Replay(){
        GreenfootImage image = new  GreenfootImage(270, 120);
        image.setColor(Color.RED);
        image.fill();
        image.setTransparency(0);
        setImage(image);
    }

    /**
     * Act - do whatever the Replay wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)){
            Greenfoot.setWorld(new ZombieWorld(Tracker.getS(), Tracker.getI(), Tracker.getIZ()));
        }
    }
}
