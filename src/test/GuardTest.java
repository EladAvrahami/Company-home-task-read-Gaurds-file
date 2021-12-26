import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//@RunWith(JUnit4.class)
public class GuardTest {
    Guard g1;
    Guard g2;

    /**
     * initialize two guard instance
     */
    @Before
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

    /**
     * verify getLikelyMinute for guard 1
     */
    @Test
    public void getLikelyMinute1() {
        int minute = g1.getLikelyMinute();
        assertEquals(24, minute);
    }

    /**
     * verify getLikelyMinute for guard 2
     */
    @Test
    public void getLikelyMinute2() {
        int minute = g2.getLikelyMinute();
        assertEquals(45, minute);
    }

    /**
     * verify equals is implemented correctly
     */
    @Test
    public void testEquals() {
        assertEquals(false, g1.equals(g2));
    }

    /**
     * verify compareTo interface is implemented correctly
     */
    @Test
    public void compareTo() {
        int cmp = g1.compareTo(g2);
        assertEquals(1, cmp);
    }
}