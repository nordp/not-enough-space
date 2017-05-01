package edu.chalmers.notenoughspace.util;

public class StringFormatUtil {

    /**
     * Converts a given number of seconds into standard
     * digital clock format: mm:ss:hh.
     * @param seconds The number of seconds to convert. If negative the time 00:00:00 is returned.
     */
    public static String toTimeFormat(float seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        int m = (int) seconds / 60;
        int s = (int) seconds % 60;
        int h = (int) ((seconds*100) % 100);

        String mm = toTwoDigitsFormat(m);
        String ss = toTwoDigitsFormat(s);
        String hh = toTwoDigitsFormat(h);

        return mm + ":" + ss + ":" + hh;
    }

    private static String toTwoDigitsFormat(int number) {
        if (number < 10) {
            return "0" + number;
        }
        return "" + number;
    }
}
