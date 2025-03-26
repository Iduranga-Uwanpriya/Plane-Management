import java.io.File;
import java.util.Scanner;

public class plane_managment {
    // Define a 2D array to represent the plane seats
    private static final int[][] seats = new int[4][14];
    // Creating an array to store ticket objects
    private static Ticket[] tickets = new Ticket[52];

    //Main method
    public  static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to the Plane Management Application");
        System.out.println();

        // Initialize the seats array with zeros
        for (int row = 0; row < seats.length; row++) {
            for (int column = 0; column < seats[row].length; column++) {
                seats[row][column] = 0;

            }

        }
        // Display the seating plan
        ShowSatingPlan();
        System.out.println();

        //Create a scanner object to read user input
        Scanner input = new Scanner(System.in);
        int selection;

        //Start a loop to display menu options and process user input
        do {
            //Display menu option
            System.out.println("**************************************************");
            System.out.println("*                  MENU OPTIONS                  *");
            System.out.println("**************************************************");
            System.out.println("      1)  Buy a seat");
            System.out.println("      2)  Cancel a seat");
            System.out.println("      3)  Find first available seat");
            System.out.println("      4)  Show seating plan");
            System.out.println("      5)  Print tickets information and total sales");
            System.out.println("      6)  Search ticket  ");
            System.out.println("      0)  Quit ");
            System.out.println("**************************************************");
            System.out.println("Please select your option:");

            // Read user input
            if (input.hasNextInt()) {
                selection = input.nextInt();
                // Process user selection using a switch statement
                switch (selection) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailableSeat();
                        break;
                    case 4:
                        ShowSatingPlan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Ticket information saved to file " );
                        System.out.println("Quiting...");
                        break;
                    default:
                        System.out.println("Invalid Input. Try again");
                        break;
                }
            } else {
                // Handle invalid input
                System.out.println("Invalid Input.Try valid option");
                input.next();// Consume the invalid input
                selection = -1;
            }
        } while (selection != 0);  // Continue looping until the user selects to quit

    }

    // Method to buy a seat
    private static void buy_seat() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row that you like (A-D)");
        char rowLetter = input.next().charAt(0);
        System.out.println("Enter the column that you like(1-14) ");
        int columnNumber;

        //read user input for row and column
        if (input.hasNextInt()) {
            columnNumber = input.nextInt();
            int rowNumber = rowLetter - 'A';
            int columnNum = columnNumber - 1;

            //Check if the input row and column are valid
            if (rowNumber >= 0 && rowNumber < seats.length && columnNum >= 0 && columnNum < seats[rowNumber].length) {
                if ((rowLetter == 'B' && (columnNumber == 13 || columnNumber == 14)) ||
                        (rowLetter == 'C' && (columnNumber == 13 || columnNumber == 14))) {
                    System.out.println("Invalid Input. Seat " + rowLetter + columnNumber + " not applicable");
                } else {
                    if (seats[rowNumber][columnNum] == 0) {// Check if the seat is available
                        seats[rowNumber][columnNum] = 1; // Mark the seat as booked

                        // Ask for person information
                        System.out.println("Enter the person's name:");
                        String name = input.next();
                        System.out.println("Enter the person's surname:");
                        String surname = input.next();
                        System.out.println("Enter the person's email:");
                        String email = input.next();
                        Person person = new Person(name, surname, email);
                        Ticket ticket =  new Ticket(rowLetter, columnNumber, getSeatPrice(columnNumber), person);

                        // Task 9 part 1
                        // Save the ticket information
                        for (int i = 0; i < tickets.length; i++) {
                            if (tickets[i] == null) {
                                tickets[i] = ticket;
                                break;
                            }
                        }
                        ticket.save();

                        // Print booking information
                        System.out.println(name+" "+surname);
                        System.out.println(email);
                        System.out.println("Seat " + rowLetter + columnNumber + " has been successfully booked");

                    } else {
                        // Handle case when the seat is already booked
                        System.out.println("Seat " + rowLetter + columnNumber + " already book . Try another Seat");
                    }
                }
            } else {
                // Handle invalid input for row and column
                System.out.println("Invalid Input. Please Enter valid row and column numbers");
            }

        } else {
            // Handle invalid input
            System.out.println("Invalid Input . Try again!");
        }

    }

    private static void cancelSeat() {
        try {

            Scanner input = new Scanner(System.in);
            System.out.println("Enter the row that you'd like to cancel (A-D)");
            char rowLetter = input.next().charAt(0);
            System.out.println("Enter the column that you'd like to cancel (1-14)");
            int columnNumber = input.nextInt();
            int rowNumber = rowLetter - 'A';
            int columnNum = columnNumber - 1;

            boolean canceled = false;

            // Check if the input row and column are valid
            if (rowNumber >= 0 && rowNumber < seats.length && columnNum >= 0 && columnNum < seats[rowNumber].length) {
                if ((rowLetter == 'B' && (columnNumber == 13 || columnNumber == 14)) ||
                        (rowLetter == 'C' && (columnNumber == 13 || columnNumber == 14))) {
                    System.out.println("Invalid Input. Seat " + rowLetter + columnNumber + " not applicable .");
                } else {
                    // Check if the seat is booked
                    if (seats[rowNumber][columnNum] == 1) {
                        seats[rowNumber][columnNum] = 0;
                        // Task 9 part 2
                        // Cancel the ticket
                        for (int i = 0; i < tickets.length; i++) {
                            if (tickets[i] != null && tickets[i].getRow() == rowLetter && tickets[i].getSeat() == columnNumber) {
                                tickets[i] = null;
                                canceled = true;
                                // Delete the associated file
                                String filename = rowLetter + String.valueOf(columnNumber) + ".txt";
                                File fileToDelete = new File(filename);
                                if (fileToDelete.delete()) {
                                    System.out.println("File " + filename + " deleted successfully.");
                                } else {
                                    System.out.println("Failed to delete file " + filename);
                                }
                                break;
                            }
                        }

                        // Print cancellation status
                        if (canceled) {

                            System.out.println("Seat " + rowLetter + columnNumber + " has been successfully canceled");
                        } else {
                            System.out.println("Failed to cancel seat. Ticket information not found.");
                        }
                    } else {
                        System.out.println("Seat " + rowLetter + columnNumber + " is already available");
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter a valid row and seat number.");
            }
        }catch (Exception e){
            System.out.println("Invalid input. Please enter a valid row and seat number.");
        }
    }


    private static void findFirstAvailableSeat() {
        boolean found = false;
        for (int row = 0; row < seats.length && !found; row++) {
            for (int column = 0; column < seats[row].length && !found; column++) {
                if ((row == 1 && (column == 12 || column == 13)) ||
                        (row == 2 && (column == 12 || column == 13))) {
                    continue;
                }
                if (seats[row][column] == 0) {
                    System.out.println("First available seat :" + (char) ('A' + row) + (column + 1));
                    found = true;

                }
            }
        }
        if (!found) {
            System.out.println("No seats available");
        }

    }

    private static void ShowSatingPlan() {
        System.out.println("--------------------------------------------------");
        System.out.println("                 Seating Plan                   ");
        System.out.println("--------------------------------------------------");
        for (int row = 0; row < seats.length; row++) {
            for (int column = 0; column < seats[row].length; column++) {
                // Skip printing seats in rows 2 and 3 where columns 13 and 14 are not available
                if ((row == 1 && (column == 12 || column == 13)) ||
                        (row == 2 && (column == 12 || column == 13))) {
                    System.out.print("  ");
                } else {
                    // Print 'O' for available seats and 'X' for booked seats
                    char seatStatus = (seats[row][column] == 0) ? 'O' : 'X';
                    System.out.print(seatStatus + " ");
                }
            }
            System.out.println(" ");
        }
    }

    // Task 9
    // Method to determine the price of a seat based on its column number
    private static Double getSeatPrice(int columnNo) {

        if (columnNo == 0 || columnNo == 1 || columnNo == 2 || columnNo == 3 || columnNo == 4) {
            return 200.0;
        } else if (columnNo == 5 || columnNo == 6 || columnNo == 7 || columnNo == 8) {
            return 150.0;
        } else {
            return 180.0;
        }
    }

    // Task 10
    private static void print_tickets_info() {
        double totAmount = 0.00;
        // Iterate through the tickets array to print ticket information and calculate total sales

        for (int i = 0; i < tickets.length; i++) {
            if(tickets[i] != null) {
                totAmount += tickets[i].getPrice();
                System.out.println("Name         : "+tickets[i].getPerson().getName());
                System.out.println("Surname      : "+tickets[i].getPerson().getSurname());
                System.out.println("Email        : "+tickets[i].getPerson().getEmail());
                System.out.println("Row number   : "+tickets[i].getRow());
                System.out.println("Seat Number  : "+tickets[i].getSeat());
                System.out.println("Price        : "+tickets[i].getPrice()+"£");
                System.out.println();
            }
        }
        // Print the total amount of sales
        System.out.println("Total amount: " + totAmount+" £");
    }

    // Task 11
    // Method to search for a ticket based on the row and column
    private static void search_ticket() {
        try {

            Scanner input = new Scanner(System.in);
            System.out.println("Enter the row that you like to Search (A-D)");
            char rowLetter = input.next().charAt(0);
            System.out.println("Enter the column that you like to search (1-14) ");
            int columnNumber = input.nextInt();
            int rowNumber = rowLetter - 'A';
            int columnNum = columnNumber - 1;

            // Check if the input row and column are valid
            if (rowNumber >= 0 && rowNumber < seats.length && columnNum >= 0 && columnNum < seats[rowNumber].length) {
                if ((rowLetter == 'B' && (columnNumber == 13 || columnNumber == 14)) ||
                        (rowLetter == 'C' && (columnNumber == 13 || columnNumber == 14))) {
                    System.out.println("Invalid Input. Seat " + rowLetter + columnNumber + " not applicable");
                } else {
                    if (seats[rowNumber][columnNum] == 1) {
                        // Search for the ticket and person information

                        for (int i = 0; i < tickets.length; i++) {
                            if (tickets[i] != null) {
                                if (tickets[i].getRow() == rowLetter && tickets[i].getSeat() == columnNumber) {
                                    tickets[i].printTicketInfo();
                                }
                            }
                        }

                    } else {
                        System.out.println("This seat is available.");
                    }


                }
            } else {
                System.out.println("Invalid input. Please enter a valid row and seat number.");
            }
        }catch (Exception e){
            System.out.println("Invalid input. Please enter a valid row and seat number.");
        }


    }

}


