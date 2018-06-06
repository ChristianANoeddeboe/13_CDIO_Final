package ase;
import java.io.*;
import java.time.LocalDateTime;

public class Logger {

	private File log;
	private FileWriter writer;

    /**
     * When initializing a logger, a new file for the log is created.
     * If the file already exists, a number will be appended based on how many files already exists of the given name.
     */
    Logger() {
        String filename = LocalDateTime.now().getYear()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getDayOfMonth()+".txt";
        log = new File(filename);
        int i = 2;
        //Checks if a file of the name already exists. If it does, append next not used number to the filename.
        while(log.isFile()) {
            log = new File(filename+"_"+i);
            i++;
        }
        try {
            writer = new FileWriter(log,true);
        } catch (IOException e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
    }

    /**
     * Used to write a line to the log.
     * @param data The String to be written.
     */
    public void writeToLog(String data) {
        String output;
        //Formats the string and assigns a timestamp to it.
        output = String.format("%02d", LocalDateTime.now().getHour())+":"+String.format("%02d", LocalDateTime.now().getMinute())+":"+String.format("%02d", LocalDateTime.now().getSecond())+": "+data;
        System.out.println(output);
        try {
            writer.write(output+"\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Could not write to file.");
            e.printStackTrace();
        }
    }
}