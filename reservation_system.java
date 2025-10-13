import java.time.LocalDate; //Import for getting date
import java.time.LocalTime; //Import for getting time
import java.time.format.DateTimeFormatter; //Import for setting time and date format
import java.util.ArrayList; //Import for more flexible arrays
import java.util.Scanner; //Import for input

class restaurant_reservation_system {
    static Scanner sc = new Scanner(System.in); //static varibale for scanner to access it globally

    public static void main(String[] args) {
        //Initialize main list, reservations array to store reservations
        ArrayList<ArrayList<String>> reservations = new ArrayList<>();
        ArrayList<ArrayList<String>> all_reservations = new ArrayList<>(); //separate list of reservations for report
        
        int reservation_count = 1; //variable to track reservation number

        while (true) { //main system loop
            String a = prompt(); //promt user to input

            System.out.println(); //just for formatting

            //main if statement to handle user input
            if (a.equals("k")) { 
                System.out.println("\nViewing reservations\n");

                view_reservations(reservations);
                
                read_prompt(); //this function allows for easier reading
                
            }

            else if (a.equals("l")) {
                System.out.println("Making reservation\n");
                ArrayList<String> new_reservation = make_reservation(reservation_count);
                
                reservations.add(new_reservation); 
                all_reservations.add(new_reservation); 
                reservation_count++; //Increment this to order reservations
                
                System.out.println("\nReservation added");
                read_prompt();

            }

            else if (a.equals("m")) {
                reservations = delete_rsv(reservations); //sets the reservation list to the edited list
                read_prompt();

            }

            else if (a.equals("n")) {
                System.out.printf("%65s%n", "Report"); //formatting only
                generate_report(all_reservations);
                read_prompt();
            }

            else if (a.equals("o")) {
                System.out.println("Thank you!");
                sc.close(); //closes the scanner
                break; //Stops the program by breaking the main loop
            }

            else { //handles invalid inputs
                System.out.println("Invalid Input");
                read_prompt();

            }

        }

    }

    //this function prompts the user for input and returns it
    public static String prompt() {
        System.out.println("\n      SYSTEM MENU\n");
        System.out.println("k. View All Reservations");
        System.out.println("l. Make A Reservation");
        System.out.println("m. Delete A Reservation");
        System.out.println("n. Generate Reservation Report");
        System.out.println("o. Exit Application");
        System.out.print("\n> ");

        String input = sc.nextLine();

        //handles invalid input
        if (input.equals("") || input.length() > 1) {
            return "invalid";
        }

        input = input.toLowerCase();

        return input;

    }

    //function that helps user to read outputs
    public static void read_prompt() {
        System.out.print("\nEnter any key to continue: ");
        sc.nextLine();

    }

    //function that displays the current reservations
    public static void view_reservations(ArrayList<ArrayList<String>> rsv_list) {
        if (rsv_list.isEmpty()) { //handles if the reservation list is empty
            System.out.println("No reservations");
            return;

        }

        System.out.printf("%-8s %-14s %-14s %-22s %-16s %-14s%n",
         "#", "Date", "Time", "Name", "Adults", "Children");

        for (int i = 0; i < rsv_list.size(); i++) {         
            System.out.printf("%-8s %-14s %-14s %-22s %-16s %-14s%n", 
                            rsv_list.get(i).get(0),
                            rsv_list.get(i).get(1), 
                            rsv_list.get(i).get(2), 
                            rsv_list.get(i).get(3),
                            rsv_list.get(i).get(4), 
                            rsv_list.get(i).get(5));

            System.out.println();

        }

    }

    //function to add a new reservation to the list
    public static ArrayList<String> make_reservation(int res_number) {

        ArrayList<String> new_reservation = new ArrayList<>(); //creates a new array to serve as a new reservation
                                                                    //that will hold reservation data
        
        //gets the data of the new reservation and adds it to the new reservation array
        String reservation_num = String.valueOf(res_number);
        new_reservation.add(reservation_num);
        
        String new_date = get_date();
        new_reservation.add(new_date);

        String new_time = get_time();
        new_reservation.add(new_time);

        String new_name = get_name();
        new_reservation.add(new_name);

        String new_adult_count = get_adult_count();
        new_reservation.add(new_adult_count);

        String new_children_count =  get_children_count();
        new_reservation.add(new_children_count);

        return new_reservation; //returns the new reservation array that would be added to the main list

    }

    //returns the current date with use of imported modules
    public static String get_date() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM dd, yyyy");
        LocalDate date = LocalDate.now();

        return date.format(format);

    }

    //returns the current time with use of imported modules
    public static String get_time() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime time = LocalTime.now();

        return time.format(format);

    }

    //promts for name and returns it
    public static String get_name() {
        String name = get_info("Enter name: ");
        return name;
    }

    //promts for adult count and returns it
    public static String get_adult_count() {
        //main loop for handling
        while (true) { 
            String adult_count = get_info("How many adults?: ");

            //if statement that handles invalid inputs
            if(is_int(adult_count) == false) {
                System.out.println("\nInvalid: Must be a positive integer.\n");

            }
            else if (Integer.parseInt(adult_count) <= 0) {
                System.out.println("\nReservation must at least have 1 adult.\n");

            }
            else{
                return adult_count; //retuns if valid
            }
        }
        
    }

    //promts for children count and returns it
    public static String get_children_count() {
        while (true) { //main loop for handling
            String children_count = get_info("How many children?: ");

            //if statement for handling invalid inputs
            if(is_int(children_count) == false) {
                System.out.println("\nInvalid: Must be a positive integer.\n");

            }
            else if (Integer.parseInt(children_count) < 0) {
                System.out.println("\nInvalid Input.\n");

            }
            else {
                return children_count; //returns if valid
            }

        }

    }

    //function for prompting user
    public static String get_info(String prompt) {
        while (true) {
            System.out.print(prompt);
            String info = sc.nextLine();

            if (info.equals("")) { //handles invalid inputs
                System.out.println("Please enter a valid input\n");

            }

            else {
                return info.trim(); //retuns the input with no spaces on the side if valid
            }

        }

    }

    //function to identify if data is an integer
    public static boolean is_int(String value) {
        //converts data to an integer if possible
        //return false if not
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
        
        return true; //return true if successful
    }

    //deletes a reservation from the list set
    public static ArrayList<ArrayList<String>> delete_rsv(ArrayList<ArrayList<String>> rsv_list) {
        if (rsv_list.isEmpty()) { //handles if list is empty
            System.out.println("No reservations");
            return rsv_list;

        }

        view_reservations(rsv_list);

        //main loop
        while (true) {
            //prompt for the reservation number of the reservation to be deleted
            String input = get_info("Enter reservation number to delete (0 to cancel): ");

            //if statement to handle invalid inputs
            if (is_int(input) == false) {
                System.out.println("\nInvalid input");

            }
            else if (input.equals("0")) {
                System.out.println("\nCancelled\n");
                return rsv_list;

            }
            else { //finds the reservation if valid input
                for (int i = 0; i < rsv_list.size(); i++) {
                    if (input.equals(rsv_list.get(i).get(0))) {
                        rsv_list.remove(i); //deletes the reservation selected

                        System.out.println("\nReservation " + input + " deleted");

                        return rsv_list; //return the edited list
                    }

                }

                System.out.println("\nReservation does not exist\n"); //handles if the reservation is not found
                
            }

        }

    }

    //generates the report
    public static void generate_report(ArrayList<ArrayList<String>> rsv_list) {
        if (rsv_list.isEmpty()) {
            System.out.println("No reservations");
            return;

        }

        ArrayList<String> sub_total_list = new ArrayList<>(); //initialize new list to store the sub total of
                                                                        //each reservation

        //loops over every reservation to compute its sub total
        //then adds it to the list
        for (int i = 0; i < rsv_list.size(); i++) { 

            int no_of_adults = Integer.parseInt(rsv_list.get(i).get(4));
            int no_of_children = Integer.parseInt(rsv_list.get(i).get(5));

            int sub_total = (no_of_adults * 500) + (no_of_children * 300);
            
            sub_total_list.add(Integer.toString(sub_total));

        }

        //this block prints out the reservations along with there sub totals
        System.out.printf("%-8s %-14s %-14s %-22s %-16s %-14s %-14s%n",
         "#", "Date", "Time", "Name", "Adults", "Children", "Sub Total");

        for (int j = 0; j < rsv_list.size(); j++) {

            System.out.printf("%-8s %-14s %-14s %-22s %-16s %-14s %-14s%n", 
                            rsv_list.get(j).get(0),
                            rsv_list.get(j).get(1), 
                            rsv_list.get(j).get(2), 
                            rsv_list.get(j).get(3),
                            rsv_list.get(j).get(4), 
                            rsv_list.get(j).get(5),
                            sub_total_list.get(j));

            System.out.println();

        }

        //initialize general variables to be displayed
        int total_adults = 0;
        int total_children = 0;
        int grand_total = 0;

        //computes the total adults and children
        for (int k = 0; k < rsv_list.size(); k++) {
            total_adults += Integer.parseInt(rsv_list.get(k).get(4));
            total_children += Integer.parseInt(rsv_list.get(k).get(5));

        }

        //adds up the sub totals to make up the grand total
        for (int l = 0; l < sub_total_list.size(); l++) {
            grand_total += Integer.parseInt(sub_total_list.get(l));
        }

        //prints out the general report total
        System.out.println("\nTotal Adutls: " + Integer.toString(total_adults));
        System.out.println("Total Children: " + Integer.toString(total_children));
        System.out.println("Grand Total: " + Integer.toString(grand_total));

    }

}
    