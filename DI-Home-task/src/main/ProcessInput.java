package main;

import main.Guard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProcessInput {
    private static final int INVALID_MINUTE = -1;
    private Map<String, Guard> guardsMap = new HashMap<String, Guard>();// שיכיל בתוכו key -id value-the guard obj

    /**
     *
     * @param reportFile the file i want to read from
     */
    public void likelyToFallAsleep(String reportFile){
        try {
            processFile(reportFile);
            Guard mostLikelyGuard = getMaxSleep();
            System.out.println(mostLikelyGuard + " is most likely to be asleep in 00:" + mostLikelyGuard.getLikelyMinute());
        }catch(FileNotFoundException e){
            throw new IllegalArgumentException("File " + reportFile + " was not found");
        }

    }

    /**
     *
     * @param reportFile
     * @throws FileNotFoundException
     */
    public void processFile(String reportFile) throws FileNotFoundException {
        File txt = new File(reportFile);
        Scanner scanner = new Scanner(txt);
        Guard guard = null; // current guard
        int startMinutes = INVALID_MINUTE;
        int endMinutes = INVALID_MINUTE;

        // [1518-11-01 00:00] main.main.Guard #10 begins shift
        // [1518-11-01 00:05] falls asleep
        // [1518-11-01 00:25] wakes up

        /**
         * using the word "guard" to find the id of new guard that just started shift
         * by split each line in the valid format of the doc.
         * Check if guard already been seen by searching at the hashmap by his unique id if not
         * create a new guard obj at put it in hashmap
         * Else if exists put your finger on the guard obj that already on the list
         * and make the next actions on him.
         */
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] wordsInLIne = line.split(" ");//split the line to separated string after each space
            if (line.contains("main.Guard")) {   // main.main.Guard detected by "main.main.Guard" word on file
                String id = wordsInLIne[3]; //format of each line is valid
                if(!guardsMap.containsKey(id)){ // guard does not exists
                    guard = new Guard(id);
                    guardsMap.put(id, guard);
                }else{
                    guard = guardsMap.get(id);
                }
            }

            /**
             * using the word "asleep" to find the line with start sleeping time
             * substring the second String that contain the the start sleeping time and make it to Int
             */
            else if(line.contains("asleep")){
                String strStartMinutes = wordsInLIne[1].substring(3, 5);//הוצאת הדקות מהאיבר השני במערך (השעה)//https://www.javatpoint.com/substring
                try {
                    startMinutes = Integer.parseInt(strStartMinutes);//https://www.javatpoint.com/java-integer-parseint-method //ממיר את הסטרינג שנשאר המציין דקות למס
                }catch(NumberFormatException e){
                    System.out.println("invalid start minute record detected");
                    System.out.println(line);
                }

                /**
                 * using the word "wakes" to find the line with start waking time
                 * substring the second String that contain the the start waking time and make it to Int
                 */
            }else if(line.contains("wakes")){
                String strEndMinutes = wordsInLIne[1].substring(3, 5);
                try {
                    endMinutes = Integer.parseInt(strEndMinutes);
                    if(startMinutes > INVALID_MINUTE){
                        guard.addSleepPeriod(startMinutes, endMinutes);/*מציאת משך שינה בהתאם לזמן התעוררות -שינה*/
                    }
                }catch(NumberFormatException e){
                    System.out.println("invalid end minute record detected");
                    System.out.println(line);
                }
            }
        }

        scanner.close();
    }

    /**
     *
     * @return guard obj with the most sleeping time
     */
    public Guard getMaxSleep(){
        Guard guard = null;
        for(Map.Entry<String, Guard> entry : guardsMap.entrySet()){//https://www.geeksforgeeks.org/map-entry-interface-java-example/ // // By gaining access to the entry of the Map we can easily manipulate them
            if(guard == null){//first entry of guard obj
                guard = entry.getValue();
            }else{
                if(entry.getValue().compareTo(guard) > 0){/*מחשב */
                    guard = entry.getValue();/*getValue-משמש כדי להביא את הערך שהועבר כפרמטר    https://www.geeksforgeeks.org/java-tuples-getvalue-method/   */
                }
            }
        }

        return guard;
    }
    public Map<String, Guard> getGuardsMap(){
        return guardsMap;
    }
}
