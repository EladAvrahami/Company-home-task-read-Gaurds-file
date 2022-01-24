package test;

import main.Guard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/*
* how to test links:
* https://www.youtube.com/watch?v=UqtorhWcjPM
* https://www.youtube.com/watch?v=Geq60OVyBPg
* */
//@RunWith(JUnit4.class)
public class GuardTest {
    Guard g1;
    Guard g2;
    @Before/*נכניס את הערכים שאמורים להתקבל לפי קובץ */
    public void setup(){
        g1 = new Guard("#10");
        g1.addSleepPeriod(5, 25);
        g1.addSleepPeriod(30, 55);
        g1.addSleepPeriod(24, 29);
        g2 = new Guard("#99");
        g2.addSleepPeriod(40, 50);
        g2.addSleepPeriod(36, 46);
        g2.addSleepPeriod(45, 55);
    }
    @Test
    public void getSleepMinutes() {

    }

    @Test
    public void getLikelyMinute1() {
        int minute = g1.getLikelyMinute();
        assertEquals(24, minute);//assertEquals- בדיקה ששני הביטויים שווים  https://www.youtube.com/watch?v=Geq60OVyBPg- דקה 17:20
    }

    @Test
    public void getLikelyMinute2() {
        int minute = g2.getLikelyMinute();
        assertEquals(45, minute);
    }

    /*בדיקה שלא אותו שומר לפי איידי*/
    @Test
    public void testEquals() {
        assertEquals(false, g1.equals(g2));
    }//יציאה מנק הנחה שלא מדובר באותו שומר

    //בדיקה שהערכים של שומר 10 אכן גדולים יותר 1 מהערכים של שומר 99
    @Test
    public void compareTo() {
        int cmp = g1.compareTo(g2);
        assertEquals(1, cmp);
    }


}
