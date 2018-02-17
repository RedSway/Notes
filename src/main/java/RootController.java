import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.sql.Date;
import java.sql.Timestamp;

public class RootController {

    @FXML
    private TableView<Notes> notesTable;

    @FXML
    private TableColumn<Notes, String> valueColumn;

    @FXML
    private TableColumn<Notes, Timestamp> dateColumn;

    @FXML
    private Label noteValueLabel;

    private Main main;

    public RootController() {
    }

    @FXML
    private void initialize() {
        valueColumn.setCellValueFactory(cellDate -> cellDate.getValue().valueProperty());
        dateColumn.setCellValueFactory(cellDate -> cellDate.getValue().dateProperty());

        showNoteValue(null);

        notesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNoteValue(newValue)
        );
    }

    @FXML
    private void handleNewNote() {
        Notes note = new Notes();
        boolean saveClicked = main.showAddNotes(note);
        if (saveClicked)
            main.getNotesDate().add(note);
    }

    public void setMain(Main main) {
        this.main = main;
        notesTable.setItems(main.getNotesDate());
    }

    private void showNoteValue(Notes note) {
        if (note != null) {
            noteValueLabel.setText(note.getValue());
        } else {
            noteValueLabel.setText("");
        }
    }
}
