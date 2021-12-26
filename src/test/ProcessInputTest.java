import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class ProcessInputTest {
    ProcessInput processInput;

    /**
     * Initialize a ProcessInput instance
     */
    @Before
    public void setUp() {
        processInput = new ProcessInput();

    }

    /**
     * verify we fail correctly in case file was not found
     */
    @Test
    public void likelyToFallAsleepRainy(){
        try {
            processInput.likelyToFallAsleep("123.txt");
        }catch(IllegalArgumentException e){
            assertThat(e.getMessage(), containsString("123.txt"));
        }
    }

    /**
     * verify normal run (sanity)
     */
    @Test
    public void likelyToFallAsleepSunny(){
        try {
            processInput.likelyToFallAsleep("Guards.txt");
        }catch(IllegalArgumentException e){
            fail();
        }
    }

    /**
     * verify all guards are added
     */
    @Test
    public void processFileSanity() {
        try {
            processInput.processFile("Guards.txt");
            int numOfGuards = processInput.getGuardsMap().size();
            assertEquals(2, numOfGuards);
        }catch(FileNotFoundException e){
            fail(e.getMessage());
        }

    }

    /**
     * verify the guards are created correctly (using their id)
     */
    @Test
    public void processFileAddedGuards() {
        try {
            processInput.processFile("Guards.txt");
            Guard g1 =  processInput.getGuardsMap().get("#10");
            Guard g2 =  processInput.getGuardsMap().get("#99");
            assertEquals("#10", g1.getId());
            assertEquals("#99", g2.getId());
        }catch(FileNotFoundException e){
            fail(e.getMessage());
        }

    }

    /**
     * verify getMaxSleep detects the correct guard
     */
    @Test
    public void getMaxSleep() {
        try {
            processInput.getMaxSleep();
            processInput.processFile("Guards.txt");
            Guard g = processInput.getMaxSleep();
            assertEquals("#10", g.getId());
        }catch(FileNotFoundException e){
            fail(e.getMessage());
        }
    }

    /**
     * verify getMaxSleep detects the correct guard with a different file input
     */
    @Test
    public void getMaxSleep1() {
        try {
            processInput.getMaxSleep();
            processInput.processFile("Guards1.txt");
            Guard g = processInput.getMaxSleep();
            assertEquals("#10", g.getId());
        }catch(FileNotFoundException e){
            fail(e.getMessage());
        }
    }
}