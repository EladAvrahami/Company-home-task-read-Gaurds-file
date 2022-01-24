package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* TODO-order at the end+delete Hebrew comm */
public class Guard implements  Comparable<Guard>{/*https://beginnersbook.com/2017/08/comparable-interface-in-java-with-example/*/
    private String id;
    private int sleepMinutes;
    private List<Pair> pairs;/*רשימה בשם צמדים מורכבת מאובייקטים המכילים זמן התעוררות וזמן הירדמות של שומרים */

    public Guard(String id) {
        this.id = id;
        this.sleepMinutes = 0;
        pairs = new ArrayList<Pair>();
    }

    public String getId() {
        return id;
    }

    public int getSleepMinutes() {
        return sleepMinutes;
    }

    /**
     * find sleeping duration
     * @param startTime
     * @param endTime
     */
    public void addSleepPeriod(int startTime, int endTime){
        Pair pair = new Pair(startTime, endTime);
        pairs.add(pair);
        addSleepMinutes(endTime - startTime);
    }

    /*מוסיף לאותו שומר ען שני נק הזמן שברשימה את סהכ הדקות שישן*/
    private void addSleepMinutes(int minutes){
        this.sleepMinutes += minutes;
    }

    /**
     * @return the minute of the guard that sleep the most
     * will probably be sleeping at.
     */
    //http://courses.cs.tau.ac.il/software1/JAD/html/InnerIter_Iterators.html
    // אם רוצים להגדיר את המחלקה של האיטרטור כך שלשירותיה תהיה גישה לשדות המופע הפרטיים של הרשימה המקושרת, צריך להגדיר את האיטרטור כמחלקה פנימית
    public int getLikelyMinute(){

        pairs.sort(new CompareByStartTime());
        Iterator<Pair> it = pairs.iterator();/*אשתמש באיטרטור על מנת לבצע סוג של שכפול לאיברים שבמערך ולהכניס לרשימה על מנת לדעת האם קיים ערך נוסף לאחר מעבר על אובייקט מסויים בה  */

        int maxxMinute = 0;
        int maxCount = 0;
        int currentMinute = 0;
        int currentCount = 0;
        Pair lastPair =null;
        while(it.hasNext()){
            Pair currentPair = it.next();
            if(currentMinute == 0){    // first iteration
                currentMinute = currentPair.getStart();/*דקה נוכחית שווה לדקה התחלתית בצמד הזמנים פייר */
                currentCount = 1;
                lastPair = currentPair;/*הצמד זמנים האחרון שבדקתי שווה לצמד הזמנים העכשווי*/


            }else if(currentPair.getStart() < lastPair.getEnd()){
                currentMinute = currentPair.getStart();
                currentCount++;/*תגדיל ספירה נוכחית ב1 */
            }else {
                if(maxCount < currentCount){
                    maxCount = currentCount;
                    maxxMinute = currentMinute;
                }
                currentMinute = currentPair.getStart();
                currentCount = 1;
                lastPair = currentPair;
            }
        }
        if(maxCount < currentCount){
            maxxMinute = currentMinute;
        }

        return maxxMinute;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Guard)){
            return false;
        }
        Guard other = (Guard)obj;   // cast obj from Object to main.main.Guard
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
     * @return
     */
    @Override
    public String toString(){
        return "main.Guard " + id + " is most likely to be asleep in 00:" + getLikelyMinute();
    }


    /**
     *
     * @param o new gourd object
     * @return false -if sleeping time that already set before for another guard is smaller than the new guard sleeping time
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
     *
     */
    private class Pair{
        private final int start;
        private final int end;
        private Pair next;
        private int likelyMinute;

        public Pair(int start, int end){
            this.start = start;
            this.end = end;
        }
        /*getters*/
        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }


        public Pair getNext() {
            return next;
        }

        public void setNext(Pair next) {
            this.next = next;
            this.likelyMinute = end;
        }

        @Override
        public String toString(){
            return "[" + start + ":" + end + "]";
        }
    }

    private class CompareByStartTime implements Comparator<Pair> {

        @Override
        public int compare(Pair o1, Pair o2) {
            return o1.getStart() - o2.getStart();
        }
    }
}
