
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class test {

    static Scanner scan = new Scanner(System.in);
    static Planner planner;
    static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws ParseException, IOException, FileNotFoundException, ClassNotFoundException {
        int ans = 0;
        loadUsers();
        do {
            System.out.print("Please select one option from Main Menu\n"
                    + "1. Register user\n"
                    + "2. Log in\n"
                    + "3. Exit\n"
                    + "Enter 1,2 or 3: ");
            ans = scan.nextInt();

            switch (ans) {
                case 1:
                    CreateUser();
                    break;
                case 2:
                    User loggedIn = login();
                    if (loggedIn == null) {
                        System.out.println("Wrong username/password");
                    } else {
                        showUserMenu(loggedIn);
                    }

                    break;
                case 3:
                   saveUsers();
                    break;
                default:
                    System.out.println("Invalid choice ... re-try");
            }
        } while (ans != 3);
    }

    private static void CreateUser() {
        System.out.print("Enter first Name: ");
        String fName = scan.next();
        System.out.print("Enter last Name: ");
        String lName = scan.next();
        System.out.print("Enter user Name: ");
        String uName = scan.next();
        System.out.print("Enter email:");
        String email = scan.next();
        System.out.print("Enter password: ");
        String password = scan.next();
        planner.registerUser(fName, lName, uName, password, email);
    }

   

    private static User login() {
        System.out.print("Enter user Name: ");
        String uName = scan.next();
        System.out.print("Enter password: ");
        String password = scan.next();
        boolean log = planner.login(uName, password);
        if (!log) {
            return null;
        } else {
            return planner.getCurrentUser();
        }

    }

    private static void showUserMenu(User user) throws ParseException, FileNotFoundException, IOException, ClassNotFoundException {
        int ans = 0;
        do {
            System.out.print("Please select one option from User Menu\n"
                    + "1. Add event\n"
                    + "2. Find event\n"
                    + "3. Cancel event\n"
                    + "4. Reminders\n"
                    + "5. Display all events\n"
                    + "6. Save Agenda\n"
                    + "7. Import Events\n"
                    + "8. Export Events\n"
                    + "9. logout\n"
                    + "Enter 1,2,3,4,5,6,7,8 or 9: ");
            ans = scan.nextInt();
            switch (ans) {
                case 1:
                    System.out.print("Enter Event title: ");
                    String title = scan.next();
                    System.out.print("Enter Event description: ");
                    String description = scan.next();
                    System.out.print("Enter start Date in the format(dd/mm/yyyy): ");
                    Date start = format.parse(scan.next());
                    System.out.print("Enter end Date in the format(dd/mm/yyyy): ");
                    Date end = format.parse(scan.next());
                    System.out.print("Does event repeat: ");
                    String str = scan.next().toLowerCase();
                    Event e;
                    if (str.equals("y")) {
                        System.out.print("Enter durantion (in days): ");
                        int duration = scan.nextInt();
                        System.out.print("Enter recurrence type (d:daily, w:weekly m:monthly): ");
                        char recuurence = scan.next().toUpperCase().charAt(0);
                        e = new RecurrentEvent(title, description, start, end, duration, recuurence);
                    } else {
                        e = new Event(title, description, start, end);
                    }

                    user.addEvent(e);
                    break;
                case 2:
                    System.out.print("Enter Event ID: ");
                    int id = scan.nextInt();
                    int index = user.findEvent(id);
                    if (index == -1) {
                        System.out.println("event not found");
                    } else {
                        System.out.println("event found");
                    }
                    break;
                case 3:
                    System.out.print("Enter Event ID: ");
                    id = scan.nextInt();
                    index = user.findEvent(id);
                    if (index == -1) {
                        System.out.println("event not found");
                    } else {
                        user.cancelEvent(id);
                        System.out.println("event deleted");
                    }
                    break;

                case 4:
                    Event[] future = user.remindAll();
                    if (future.length == 0) {
                        System.out.println("no future events");
                    } else {
                        for (int i = 0; i < future.length; i++) {
                            System.out.println("-------\n" + future[i] + "\n---------");
                        }
                    }
                    break;

                case 5:
                   user.displayAll();
                    break;
                case 6:
                    user.saveAgenda();
                    break;
                case 7:
                     System.out.print("Enter file name: ");
                     String file = scan.next();
                    user.importEvents(file);
                    break;
                case 8:
                     System.out.print("Enter file name: ");
                      file = scan.next();
                    user.exportEvents(file);
                case 9:
                    break;
                default:
                    System.out.println("Invalid choice ... re-try");
            }
        } while (ans != 9);
    }

    private static void saveUsers() throws IOException {
        planner.saveUserList();
    }
 private static void loadUsers() throws IOException, ClassNotFoundException {
       planner = new Planner(new User[100]);
       planner.loadUserList();
    }
}
