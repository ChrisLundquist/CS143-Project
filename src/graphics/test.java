package graphics;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Timestamp time1 = null;
        long l1 = time1.getTime();
        Timestamp time2 = null;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long l2 = time2.getTime();
        
        double difference = (l2 - l1)/1000;
        System.out.println(difference);
    }
}
