
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


public class Planner {

    Scanner read = new Scanner(System.in);

    private int numOfUsers;
    private User[] usersList;
    private User currentUser;
    
    public Planner(User[] users) {
        usersList=users.clone();
    }

    public boolean registerUser(String fName, String lName, String uName, String password, String email) {
     
        if(numOfUsers<usersList.length)
        {
            usersList[numOfUsers++]=new User(fName,lName,uName,password,email);
            return true;
        }
        return false;
    }

    public boolean login(String uName, String password) {
 
        for(int i =0; i<numOfUsers; i++)
        {
            if(usersList[i].getUsername().equals(uName) && usersList[i].getPassword().equals(password)){
                currentUser = usersList[i];
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void saveUserList()  {
    try{
       ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("users.obj"));
       o.writeObject(usersList);
       o.writeInt(numOfUsers);
       o.close();
       } catch (IOException ex) {
       }
       
    }
    
    public void loadUserList() 
    {
       ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("users.obj"));
            usersList= (User[]) in.readObject();
            numOfUsers = in.readInt();
            in.close();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } 
    }

}
