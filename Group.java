import java.util.List;

public class Group {
    private String groupName;
    private List<Contact> members;
    public void addContact(Contact contact) {
        members.add(contact);
    }
    public void removeContact(Contact contact) {
        members.remove(contact);
    }

}

