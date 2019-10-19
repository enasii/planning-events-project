
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Event implements Serializable {

    protected int id;
    protected String title;
    protected String description;
    private Date startD;
    private Date endD; 
   

    public Event(String title, String des, Date start, Date end) throws IllegalArgumentException {
        this.title = title;
        description = des;
        startD=start ;
        endD=end;
        if (endD.before(startD)) {
            throw new IllegalArgumentException();
        } 
    }

  public  Event(Event e) {
   this.title = e.title;
        description = e.description;
        startD = new Date(e.startD.getTime()) ;
        endD= new Date(e.getEndD().getTime());
        if (endD.before(startD)) {
            throw new IllegalArgumentException();
        } 
    }
    
    public void setID(int id)
    {
        this.id=id;
    }

    public int getID() {
        return id;
    }
    
    
    public boolean remind() {
        Date now = new Date();
        if(now.compareTo(endD)<0)
            return true;
        else
            return false;
            
        
    }

    public Date getStartD() {
        return startD;
    }

    public Date getEndD() {
        return endD;
    }

   
    
    
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String startDF = formatter.format(startD);
        String endDF = formatter.format(endD);
        String s = "Event# " + id + ": " + title + "\nd" + description;
        s+= "\n"+startDF;
        s+= " - "+endDF;
        return s;
    }
}
