import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Ticket {
    //attributes
    private char row;
    private int seat;
    private double price;
    private Person person;

    //get constructor
    public Ticket(char row , int seat, double price, Person person){
        this.setRow(row);
        this.setSeat(seat);
        this.setPrice(price);
        this.setPerson(person);
    }
    //add getters and setters
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //Method to save ticket information to file
    public void save(){
        String filename = row + String.valueOf(seat)+".txt";// Construct the filename
        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(filename);
            writer.write("Ticket information \n");
            writer.write("Row: " + row+"  \n");
            writer.write("Seat: " + seat+ "  \n");
            writer.write("Price: " + price+"£  \n");
            writer.write("\nPerson Information:\n");
            writer.write("Name: " + person.getName()+"  \n");
            writer.write("Surname: " + person.getSurname()+"  \n");
            writer.write("Email: " + person.getEmail()+"  \n");
            writer.write("Ticket information saved to file: " + filename);
            writer.close();
        } catch (IOException ex){
            System.out.println("Error");

        }
    }
    public void printTicketInfo() {
        System.out.println("Ticket Information: ");
        System.out.println("Row:  " + row);
        System.out.println("Seat:  " + seat);
        System.out.println("Price:  " + price);
        System.out.println("Person Information: ");
        person.printInfo();
    }

}
