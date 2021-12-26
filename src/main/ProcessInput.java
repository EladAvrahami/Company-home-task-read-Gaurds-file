import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProcessInput {
    private static final int INVALID_MINUTE = -1;
    private Map<String, Guard> guardsMap = new HashMap<String, Guard>();

    /**
     * Entry point for this class.
     * receives a guard shift log file and outputs to the console the most likely time of sleep
     * @param reportFile
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
     * process the shift log file line by line
     * builds a guard objects and add all sleep (start, end) entries to it
     * @param reportFile
     * @throws FileNotFoundException
     */
    public void processFile(String reportFile) throws FileNotFoundException {
        File txt = new File(reportFile);
        Scanner scanner = new Scanner(txt);
        Guard guard = null; // current guard
        int startMinutes = INVALID_MINUTE;
        int endMinutes = INVALID_MINUTE;

        // [1518-11-01 00:00] main.Guard #10 begins shift
        // [1518-11-01 00:05] falls asleep
        // [1518-11-01 00:25] wakes up

        /**
         * using the word guard to find the id of new guard that just started shift
         *
         */
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] wordsInLIne = line.split(" ");//split the line to separated string after each space
            if (line.contains("Guard")) {   // main.Guard detected by "main.Guard" word on file
                String id = wordsInLIne[3]; //format of each line is valid
                if(!guardsMap.containsKey(id)){ // guard does not exists
                    guard = new Guard(id);//************מה זה אומר ?**********************************
                    guardsMap.put(id, guard);
                }else{
                    guard = guardsMap.get(id);
                }
            }
            else if(line.contains("asleep")){
                String strStartMinutes = wordsInLIne[1].substring(3, 5);//הוצאת הדקות מהאיבר השני במערך (השעה)//https://www.javatpoint.com/substring
                try {
                    startMinutes = Integer.parseInt(strStartMinutes);//https://www.javatpoint.com/java-integer-parseint-method //ממיר את הסטרינג שנשאר המציין דקות למס
                }catch(NumberFormatException e){
                    System.out.println("invalid start minute record detected");
                    System.out.println(line);
                }
            }else if(line.contains("wakes")){
                String strEndMinutes = wordsInLIne[1].substring(3, 5);
                try {
                    endMinutes = Integer.parseInt(strEndMinutes);
                    if(startMinutes > INVALID_MINUTE){
                        guard.addSleepPeriod(startMinutes, endMinutes);
                    }
                }catch(NumberFormatException e){//************************************לא תופס את החריגה
                    System.out.println("invalid end minute record detected");
                    System.out.println(line);
                }
            }
        }

        scanner.close();
    }


    /**
     * go over each guard and find the one who sleeps the most during his shift
     * @return
     */
    public Guard getMaxSleep(){//******************* איך השיטה הזאת עובדת
        Guard guard = null;
        for(Map.Entry<String, Guard> entry : guardsMap.entrySet()){//https://www.geeksforgeeks.org/map-entry-interface-java-example/ // // By gaining access to the entry of the Map we can easily manipulate them
            if(guard == null){
                guard = entry.getValue();
            }else{
                if(entry.getValue().compareTo(guard) > 0){
                    guard = entry.getValue();
                }
            }
        }

        return guard;
    }
    Map<String, Guard> getGuardsMap(){
        return guardsMap;
    }
}
