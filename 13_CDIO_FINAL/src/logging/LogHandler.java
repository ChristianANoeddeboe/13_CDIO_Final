package logging;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.Date;

public class LogHandler {

    public LogHandler(Logger log) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        String fileName = dateFormat.format(date) + ".txt";

        try{
            log.addHandler(new FileHandler(fileName, true));
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LogHandler(Logger log, String dir) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        String fileName = dir + "-" + dateFormat.format(date) + ".txt";

        try{
            log.addHandler(new FileHandler(fileName, true));
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
