package edu.chalmers.notenoughspace.util;

public class StringFormatUtil {

    /**
     * Converts
     * @param seconds
     * @return
     */
    public static String toTimeFormat(float seconds) {
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
