public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String note;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}