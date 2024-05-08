package bee.corp.bcorder.utility;

public class TimeFormatter {
    public static String FormatMilliseconds(long millis) {
        String result = MillisToHours(millis) + ":" + MillisToMinutes(millis) + ":" + MillisToSeconds(millis);
        if(MillisToHours(millis) < 10 && MillisToMinutes(millis) < 10 && MillisToSeconds(millis) < 10) {
            result = "0" + MillisToHours(millis) + ":0" + MillisToMinutes(millis) + ":0" + MillisToSeconds(millis);
        } else if(MillisToHours(millis) < 10 && MillisToMinutes(millis) < 10) {
            result = "0" + MillisToHours(millis) + ":0" + MillisToMinutes(millis) + ":" + MillisToSeconds(millis);
        } else if(MillisToHours(millis) < 10) {
            result = "0" + MillisToHours(millis) + ":" + MillisToMinutes(millis) + ":" + MillisToSeconds(millis);
        }
        return result;
    }
    private static long MillisToSeconds(long millis) {
        return millis/1000;
    }
    private static long MillisToMinutes(long millis) {
        return millis/60000;
    }
    private static long MillisToHours(long millis) {
        return millis/3600000;
    }
}
