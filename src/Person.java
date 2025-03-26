public class Person {
    private String name;
    private String surname;
    private String email;

    // Constructor
    public Person(String name, String surname, String email) {
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to print information
    public void printInfo() {
        System.out.println("Name:  " + name);
        System.out.println("Surname:  " + surname);
        System.out.println("Email:  " + email);
    }

}

