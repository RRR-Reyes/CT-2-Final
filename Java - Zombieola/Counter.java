import greenfoot.*; // Import Greenfoot libraries

public class Counter extends Actor {
    private String word;
    private int value;
    
    public Counter(String word, int value) {
        this.word = word;
        this.value = value;
        updateImage();
    }
    
    public void setValue(int value) {
        this.value = value;
        updateImage();
    }
    
    private void updateImage() {
        GreenfootImage image = new GreenfootImage(word + value, 20, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(image);
    }
}