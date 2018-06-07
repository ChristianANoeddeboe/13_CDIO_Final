import dto.Status;
import logging.LogHandler;
import lombok.extern.java.Log;

@Log
public class Mainy {
    public static void main(String[] args) {
//        log.info("Starting");
//        new LogHandler(log, "ase");
//        log.severe("Gonna log something");
        Object[] arr = Status.values();
        for(Object object : arr){
            System.out.println(object.toString());
        }
        System.out.println(Status.values().toString());
    }
}
