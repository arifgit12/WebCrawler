package in.arifalimondal.crawlingservice.test;

public class StringUtil {
    public static void PrintStatement(String heading, String log) {
        if (log.length()>0){
            System.out.printf("%s - %s%n", heading, log);
        }
    }
}
