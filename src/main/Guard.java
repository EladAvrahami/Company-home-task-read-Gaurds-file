
import java.util.*;

/*לעשות פה סדר */
public class Guard implements  Comparable<Guard>{
    private String id;
    private int sleepMinutes;
    private List<Pair> sleepTimeList;

    public Guard(String id) {
        this.id = id;
        this.sleepMinutes = 0;
        sleepTimeList = new ArrayList<Pair>();
    }

    public String getId() {
        return id;
    }

    public int getSleepMinutes() {
        return sleepMinutes;
    }

    /**
     * Add sleep time (start and end) to the sleep array list
     * @param startTime
     * @param endTime
     */
    public void addSleepPeriod(int startTime, int endTime){
        Pair pair = new Pair(startTime, endTime);
        sleepTimeList.add(pair);
        addSleepMinutes(endTime - startTime);
    }

    private void addSleepMinutes(int minutes){
        this.sleepMinutes += minutes;
    }

    /**
     * iterate over a sorted array list of sleep time and find the most common minute of sleep
     * @return the minute that the gourde will likely be sleeping in
     */
    //http://courses.cs.tau.ac.il/software1/JAD/html/InnerIter_Iterators.html
    // אם רוצים להגדיר את המחלקה של האיטרטור כך שלשירותיה תהיה גישה לשדות המופע הפרטיים של הרשימה המקושרת, צריך להגדיר את האיטרטור כמחלקה פנימית
    public int getLikelyMinute(){

        // sort array by start time (using the inner class CompareByStartTime)
        sleepTimeList.sort(new CompareByStartTime());
        Iterator<Pair> it = sleepTimeList.iterator();

        int maxMinute = 0;
        int maxCount = 0;
        int currentMinute = 0;
        int currentCount = 0;
        Pair lastPair =null;

        while(it.hasNext()){
            Pair currentPair = it.next();
            if(currentMinute == 0){    // first iteration
                currentMinute = currentPair.getStart();
                currentCount = 1;
                lastPair = currentPair;
            }else if(currentPair.getStart() < lastPair.getEnd()){   // if current overlaps with previous
                currentMinute = currentPair.getStart();
                currentCount++;
            }else { // current sleep time does not overlap with previous
                if(maxCount < currentCount){    // if current sleep times is greater than last max, overwrite it
                    maxCount = currentCount;
                    maxMinute = currentMinute;
                }
                currentMinute = currentPair.getStart();
                currentCount = 1;
                lastPair = currentPair;
            }
        }
        if(maxCount < currentCount){
            maxMinute = currentMinute;
        }

        return maxMinute;
    }

    /**
     *
     * @param obj
     * @return true if both this and other guard are the same
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Guard)){
            return false;
        }
        Guard other = (Guard)obj;   // cast obj from Object to main.Guard
        return hashCode() == other.hashCode();
    }

    /**
     * id is unique for each guard
     * therefore its hashcode is also unique
     * @return the hashcode of the id
     */
    @Override
    public int hashCode(){
        return id.hashCode();
    }

    /**
     *
     * @return string representation of this guard
     */
    @Override
    public String toString(){
        return "Guard " + id + " is most likely to be asleep in 00:" + getLikelyMinute();
    }


    /**
     * implementation of comparable interface;
     * a guard is greater if its total time of sleep is greater than the other guard
     * @param o - other guard
     * @return -1 if this guard slept less that the other, 0 if they slept the same time or 1 if this guard slept more time
     */
    @Override
    public int compareTo(Guard o) {
        if(sleepMinutes < o.sleepMinutes){
            return -1;
        }else if(sleepMinutes > o.sleepMinutes){
            return 1;
        }
        return 0;
    }

    /**
     * The pair class holds the sleep time:
     * start time
     * end time
     */
    private class Pair{
        private final int start;
        private final int end;

        public Pair(int start, int end){
            this.start = start;
            this.end = end;
        }
        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        @Override
        public String toString(){
            return "[" + start + ":" + end + "]";
        }
    }

    /**
     * Comparator to sort the sleepTimeList in method getLikelyMinute()
     */
    private class CompareByStartTime implements Comparator<Pair>{

        @Override
        public int compare(Pair o1, Pair o2) {
            return o1.getStart() - o2.getStart();
        }
    }
}
