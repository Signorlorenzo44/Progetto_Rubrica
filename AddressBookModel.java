import java.util.ArrayList;
import java.util.List;

/**
 * Manages the data for the address book.
 */
public class AddressBookModel {
    private List<Contact> contacts;
    private List<Group> groups;

    /**
     * Adds a contact to the address book.
     * @param contact The contact to add.
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
    }
    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }
    public List<Contact> findDuplicates() {
        // Implementation here
        return null;
    }

    public List<Contact> filterContacts(String criteria)  {
        return new ArrayList<>();
    }
    public List<Group> getGroups(){
        return groups;
    }

}