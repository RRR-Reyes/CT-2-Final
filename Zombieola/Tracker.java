/**
 * Write a description of class Tracker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tracker {
    // instance variables
    private static int S;
    private static int I;
    private static int IZ;

    // Constructor to initialize the variables
    public  static void Tracker(int s, int i, int iz) {
        S = s;
        I = i;
        IZ = iz;
    }

    public static int getS() {
        return S;
    }

    public static int getI() {
        return I;
    }

    public static int getIZ() {
        return IZ;
    }
    
    public static void setValues(int s, int i, int iz) {
        S = s;
        I = i;
        IZ = iz;
    }
}

