package main;

import java.io.FileNotFoundException;

/**
 * @Author( name = "Elad-Avrahami", date = "12/23/2021" )
 * choose any valid Guards activity file and put him in line 12
 */
public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        ProcessInput p = new ProcessInput();
        p.likelyToFallAsleep("Guards.txt");//enter activity file
    }
}
