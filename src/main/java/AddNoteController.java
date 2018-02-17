import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Timestamp;

public class AddNoteController {

    @FXML
    private TextArea valueTextArea;

    private Stage addStage;
    private Notes notes;
    private boolean saveClicked = false;

    @FXML
    private void initialize(){

    }

    public void setAddStage(Stage addStage) {
        this.addStage = addStage;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        valueTextArea.setText(notes.getValue());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (valueTextArea.getText().length() > 100) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(this.addStage);
            alert.setTitle("Длина текста > 100");
            alert.setHeaderText("Длина текста > 100");
            alert.setContentText("Введите текст не более 100");

            alert.showAndWait();

        } else if (valueTextArea.getText() != null || valueTextArea.getText().length() != 0) {
            notes.setValue(valueTextArea.getText());
            java.util.Date date = new java.util.Date();
            notes.setDate(new Timestamp(date.getTime()));
            DBHelper.addNoteToDB(notes);

            saveClicked = true;
            addStage.close();
        }

    }


}
