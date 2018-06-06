import logging.LogHandler;
import lombok.extern.java.Log;

@Log
public class Mainy {
    public  static  void  main (String[] args){
        log.info("Starting");
        new LogHandler(log, "ase");
        log.severe("Gonna log something");
    }
}
