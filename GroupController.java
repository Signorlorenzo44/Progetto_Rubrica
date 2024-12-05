import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;

public class GroupController {
    private GroupView view;
    private AddressBookModel model;
    private ObservableList<String> groupList;
    public GroupController(GroupView view, AddressBookModel model) {
        this.view = view;
        this.model = model;
        this.groupList = FXCollections.observableArrayList();

        initialize();
    }
    private void initialize() {
        view.getGroupListView().setItems(groupList);

        view.getAddGroupButton().setOnAction(e -> handleAddGroup());
        view.getDeleteGroupButton().setOnAction(e -> handleDeleteGroup());
    }

    private void handleAddGroup() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Group");
        dialog.setHeaderText("Create a new group");
        dialog.setContentText("Enter group name:");

        dialog.showAndWait().ifPresent(groupName -> {
            if (!groupName.trim().isEmpty()) {
                groupList.add(groupName);
                System.out.println("Group added: " + groupName);
            } else {
                System.out.println("Error: Group name cannot be empty.");
            }
        });
    }
    private void handleDeleteGroup() {
        int selectedIndex = view.getGroupListView().getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String removedGroup = groupList.remove(selectedIndex);
            System.out.println("Group removed: " + removedGroup);
        } else {
            System.out.println("Error: No group selected.");
        }
    }
}
