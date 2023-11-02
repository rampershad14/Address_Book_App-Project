import java.io.*;
import java.util.*;
class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nEmail: " + email + "\n";
    }
}

class AddressBook implements Serializable {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Address book saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AddressBook loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)) ){
            AddressBook addressBook = (AddressBook) ois.readObject();
            System.out.println("Address book loaded from " + fileName);
            return addressBook;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new AddressBook();
    }
}

public class AddressBookApp {
    public static void main(String[] args) {
        AddressBook addressBook = AddressBook.loadFromFile("addressbook.ser");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Address Book System Menu:");
            System.out.println("1. Add a new contact");
            System.out.println("2. Edit a contact");
            System.out.println("3. Search for a contact");
            System.out.println("4. Display all contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
                        Contact newContact = new Contact(name, phoneNumber, email);
                        addressBook.addContact(newContact);
                        System.out.println("Contact added successfully.");
                    } else {
                        System.out.println("Please fill in all required fields.");
                    }
                    break;

                case 2:
                    System.out.print("Enter name to edit: ");
                    String editName = scanner.nextLine();
                    Contact editContact = addressBook.searchContact(editName);
                    if (editContact != null) {
                        System.out.print("Enter new phone number: ");
                        String newPhoneNumber = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        if (!newPhoneNumber.isEmpty() && !newEmail.isEmpty()) {
                            editContact = new Contact(editName, newPhoneNumber, newEmail);
                            System.out.println("Contact edited successfully.");
                        } else {
                            System.out.println("Please fill in all required fields.");
                        }
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.nextLine();
                    Contact foundContact = addressBook.searchContact(searchName);
                    if (foundContact != null) {
                        System.out.println("Contact found:\n" + foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 4:
                    List<Contact> allContacts = addressBook.getAllContacts();
                    for (Contact contact : allContacts) {
                        System.out.println(contact);
                    }
                    break;

                case 5:
                    addressBook.saveToFile("addressbook.ser");
                    System.out.println("Exiting Address Book System.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
