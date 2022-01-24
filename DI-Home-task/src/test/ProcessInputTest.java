package test;

import main.Guard;
import main.ProcessInput;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class ProcessInputTest {
    ProcessInput processInput;
    @Before
    public void setUp() {
        processInput = new ProcessInput();

    }

    @Test
    public void likelyToFallAsleepRainy(){
        try {
            processInput.likelyToFallAsleep("123.txt");
        }catch(IllegalArgumentException e){
            //than... do this - assertThat
            assertThat(e.getMessage(), containsString("123.txt"));
        }
    }

    /*לוודא שהקובץ שהוכנס הוא קובץ השומרים הרצוי*/
    @Test
    public void likelyToFallAsleepSunny(){
        try {
            processInput.likelyToFallAsleep("Guards.txt");
        }catch(IllegalArgumentException e){
            fail();/*https://www.google.com/search?q=assert+fail+junit&sxsrf=AOaemvIwyeX4PBNTmRv-fczdtGsoK5M-UQ%3A1642367518176&ei=HorkYfuECpmO9u8PgpmS2A0&ved=0ahUKEwj7i4vkl7f1AhUZh_0HHYKMBNsQ4dUDCA4&uact=5&oq=assert+fail+junit&gs_lcp=Cgdnd3Mtd2l6EAMyBQgAEMsBMgUIABDLATIFCAAQywEyBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjoHCAAQRxCwAzoHCAAQsAMQQzoJCAAQsAMQChBDOgQIABAKSgQIQRgASgQIRhgAULoGWLUWYNkbaAFwAngAgAF_iAHhBZIBAzAuNpgBAKABAcgBCMABAQ&sclient=gws-wiz*/
        }
    }

    /*לוודא שאמורים להיות בסהכ שני שומרים בקובץ זה */
    @Test
    public void processFileSanity() {
        try {
            processInput.processFile("Guards.txt");
            int numOfGuards = processInput.getGuardsMap().size();
            assertEquals(2, numOfGuards);
        }catch(FileNotFoundException e){
            fail(e.getMessage());/*הצג שגיאה אם לאו */
        }

    }

    /*לוודא מהו מס הזוי של השומרים שהתקבלו */
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

    /*לוודא ששומר 10 הוא זה שישן הכי הרבה זמן */
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

    /*אותו דבר כמו למעלה פשוט ניסיון על קובץ אחר */
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