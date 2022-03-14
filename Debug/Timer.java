package Debug;

import java.lang.System;

public class Timer {

    public static class AvrgTimes{
        private long[] intervalLength;
        private long count;

        public AvrgTimes(int intervalCount){
            intervalLength = new long[intervalCount];
        }

        public void AddResults(long[] results){
            for(int i = 0; i < intervalLength.length; i++){
                intervalLength[i] += results[i];
            }
            count++;
        }

        public String getResults(){
            String result = "";
            for(int i = 0; i < intervalLength.length; i++){
                result += String.valueOf(i) + ". Interval: " + (intervalLength[i] / count) + "\n";
            }
            return result;
        }
    }


    private int currentInterval;
    private long[] intervalTimes;

    public void restartTimer() { 
        currentInterval = 0;//reset interval
        saveInterval();
    }
    public void saveInterval(){
        intervalTimes[currentInterval] = System.currentTimeMillis();
        currentInterval++;
    }

    public Timer(int intervalCount){ 
        intervalTimes = new long[intervalCount+1];
        restartTimer();
    }


    public long[] getResults(){
        long[] results = new long[intervalTimes.length - 1];
        for(int i = 0; i < results.length; i++){
            results[i] = intervalTimes[i+1] - intervalTimes[i];
        }
        return results;
    }
}
