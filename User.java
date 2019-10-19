
import java.io.*;

public class User implements Serializable {//start class
//attr

    private String fName;
    private String lName;
    private String uName;
    private String email;
    private String password;
    private int numOfEvents;
    private Event[] eventsList;

    //constrcter
    public User(String fName, String lName, String uName, String password, String email) {//start constrcter
        this.fName = fName;
        this.lName = lName;
        this.uName = uName;
        this.password = password;
        this.email = email;
        numOfEvents = 0;
        eventsList = new Event[100];
    }//end constrcter

    public void addEvent(Event e) {//start add
        if (numOfEvents < eventsList.length) {
            eventsList[numOfEvents++] = new Event(e);
        }
        eventsList[numOfEvents-1].setID(numOfEvents);
        System.out.println("add was succscefull");
    }

    public boolean cancelEvent(int id) {//start cencel Event
        if (id < numOfEvents && id >= 0) {//start if
            eventsList[id] = eventsList[--numOfEvents];
            eventsList[numOfEvents] = null;
            return true;
        }//end if
        return false;
    }//end cencel Event

    public int findEvent(int Id) {//start find  
        for (int i = 0; i < numOfEvents; i++) {
            if (eventsList[i].getID() == Id) {
                return i;
            }
        }
        return -1;
    }//end find 

    public void displayAll() {//start display
        for (int i = 0; i < numOfEvents; i++) {
            if(eventsList[i] instanceof RecurrentEvent)
                System.out.println((RecurrentEvent)eventsList[i]);
            else
                System.out.println(eventsList[i]);
        }
    }//end display

    public Event[] remindAll() {//start RemindAll
        Event[] futureEvent = new Event[numOfEvents];
        int count = 0;
        for (int i = 0; i < numOfEvents; i++) {
            if (eventsList[i].remind()) {
                futureEvent[count++] = eventsList[i];
            }
        }
        return futureEvent;
    }//end RemindAll

    public void saveAgenda()   {//start  save Agenda  
    try {
        File F = new File(uName + ".txt");
        FileOutputStream InputF = new FileOutputStream(F);
        PrintWriter PW = new PrintWriter(InputF);
        PW.println(fName + " " + lName);
        PW.println("Username: " + uName);
        PW.println("Email: " + email);
        PW.println("Number of Events: " + numOfEvents);
        for (int i = 0; i < numOfEvents; i++) {
            PW.println(eventsList[i]);
        
        }//end for 
        PW.close();
        }catch(FileNotFoundException e ){}
    }//end save Agenda

    public void importEvents(String File)   {
    try {
        FileInputStream FIS = new FileInputStream(File);
        ObjectInputStream o = new ObjectInputStream(FIS);
        try {
            while (true) {//start while 

                //  E=new Event();
                this.addEvent((Event) o.readObject());
            }//end while
        } catch (EOFException ex) {
            o.close();
        }
        }
        catch (FileNotFoundException e){}
        catch (ClassNotFoundException e ){}
        catch (IOException e ){}
    }//end import

    public void exportEvents(String file) {//start Export Events
        try {// start try
            File OF = new File(file);
            FileOutputStream FOS = new FileOutputStream(OF);
            ObjectOutputStream OS = new ObjectOutputStream(FOS);
            for (int i = 0; i < numOfEvents; i++) {
                OS.writeObject(eventsList[i]);
            }
            OS.close();
        }//end try
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }//end ExportEvents

    //setter and getter
    public void setfName(String f) {
        fName = f;
    }

    public void setlName(String l) {
        lName = l;
    }

    public void setuName(String u) {
        uName = u;
    }

    public void setemail(String e) {
        email = e;
    }

    public void setpassword(String p) {
        password = p;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUsername() {
        return uName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}//end class
