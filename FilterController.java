public class FilterController {
    private AddressBookModel model;
    private FilterView view;

    public FilterController(AddressBookModel model, FilterView view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.getApplyFilterButton().setOnAction(e -> applyFilters());
    }

    private void applyFilters() {
        boolean byPhone = view.getFilterByPhone().isSelected();
        boolean byEmail = view.getFilterByEmail().isSelected();

        // Apply filters to the model
        String criteria = "";
        if (byPhone) criteria += "phone";
        if (byEmail) criteria += "email";

        model.filterContacts(criteria);
    }
}