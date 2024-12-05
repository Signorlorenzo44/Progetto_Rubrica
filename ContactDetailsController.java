public class ContactDetailsController {
    private AddressBookModel model;
    private ContactDetailsView view;

    public ContactDetailsController(AddressBookModel model, ContactDetailsView view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.getEditButton().setOnAction(e -> handleEditContact());
        view.getBackButton().setOnAction(e -> handleBack());
    }

    private void handleEditContact() {
    }

    private void handleBack() {
    }
}