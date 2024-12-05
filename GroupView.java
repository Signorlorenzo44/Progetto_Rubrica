import javafx.scene.layout.VBox;
import javafx.scene.control.*;

public class GroupView {
    private VBox root;
    private ListView<String> groupListView;
    private Button addGroupButton;
    private Button deleteGroupButton;
    public GroupView() {
        root = new VBox();
        groupListView = new ListView<>();
        groupListView.setPrefHeight(400);

        addGroupButton = new Button("Add Group");
        deleteGroupButton = new Button("Delete Group");

        root.getChildren().addAll(groupListView, addGroupButton, deleteGroupButton);
    }
    public VBox getRoot() {
        return root;
    }
    public ListView<String> getGroupListView() {
        return groupListView;
    }

    public Button getAddGroupButton() {
        return addGroupButton;
    }

    public Button getDeleteGroupButton() {
        return deleteGroupButton;
    }
}