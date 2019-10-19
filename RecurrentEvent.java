
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecurrentEvent extends Event implements Serializable{

    private int duration;
    private char recurrence;

    public RecurrentEvent(String title, String des, Date start, Date end, int duration, char recurrence) {
        super(title, des, start, end);
        this.duration = duration;
        this.recurrence = recurrence;
    }

    public boolean remind() {
        Date now = new Date();
        Date finalEndD = getFinalDate();
        if (now.compareTo(finalEndD) < 0) {
            return true;
        } else {
            return false;
        }
    }

    private String getPeriods() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String r = "";
        Date d = this.getStartD();
        Date e = this.getEndD();
        while (d.before(e)) {
            String s = formatter.format(d);

            r += s + " --- ";
            long mil = d.getTime();
            Date sendD = new Date(mil + (duration - 1) * 24L * 60 * 60 * 1000);
            String es = formatter.format(sendD);
            r += es + "\n";
            switch (recurrence) {

                // Date nextD = new Date()
                case 'M':
                    long monthInMS = 30L * 24 * 60 * 60 * 1000;
                    mil += monthInMS;
                    d = new Date(mil);
                    break;
                case 'W':
                    long weekInMS = 7L * 24 * 60 * 60 * 1000;
                    mil += weekInMS;
                    d = new Date(mil);
                    break;
                case 'D':
                    long dayInMS = 24L * 60 * 60 * 1000;
                    mil += dayInMS;
                    d = new Date(mil);

            }

        }

        return r;

    }

    public String toString() {
        String s = "Even#" + id + " " + title + "\n" + description + "\n";
        s += this.getPeriods();
        return s;
    }

    private Date getFinalDate() {
        Date s= this.getStartD();
        Date e = this.getEndD();
        while (s.before(e)) {

            long mil = s.getTime();
            long durantionInMS =  (duration - 1) * 24L * 60 * 60 * 1000;
            switch (recurrence) {

                case 'M':
                    long monthInMS = 30L * 24 * 60 * 60 * 1000;
                    mil += monthInMS;
                    s= new Date(mil);
                    if(!s.before(e))
                    {
                        s= new Date(mil-monthInMS+durantionInMS); 
                        return s;
                    }
                    break;
                case 'W':
                    long weekInMS = 7L * 24 * 60 * 60 * 1000;
                    mil += weekInMS;
                    s = new Date(mil);
                    if(!s.before(e))
                    {
                        s= new Date(mil-weekInMS+durantionInMS); 
                        return s;
                    }
                    
                    break;
                case 'D':
                    long dayInMS = 24L * 60 * 60 * 1000;
                    mil += dayInMS;
                    s = new Date(mil);
                    if(!s.before(e))
                    {
                        s= new Date(mil-dayInMS+durantionInMS); 
                        return s;
                    }

            }

        }
        return null;
    }
}
