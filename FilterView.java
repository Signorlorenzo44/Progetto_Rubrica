import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class FilterView {
    private VBox root;
    private CheckBox filterByPhone;
    private CheckBox filterByEmail;
    private Button applyFilterButton;

    public FilterView() {
        root = new VBox();
        filterByPhone = new CheckBox("Filter by Phone");
        filterByEmail = new CheckBox("Filter by Email");
        applyFilterButton = new Button("Apply Filter");
        root.getChildren().addAll(filterByPhone, filterByEmail, applyFilterButton);
    }

    public VBox getRoot() {
        return root;
    }

    public CheckBox getFilterByPhone() {
        return filterByPhone;
    }

    public CheckBox getFilterByEmail() {
        return filterByEmail;
    }

    public Button getApplyFilterButton() {
        return applyFilterButton;}
}