import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteController {

    @FXML
    private TextArea valueTextArea;

    @FXML
    private Label dateLabel;

    private Stage addStage;
    private Notes notes;
    private boolean saveClicked = false;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

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

    public void setDateLabel(Date date) {
        dateLabel.setText("Текущее дата/время: " + simpleDateFormat.format(date));
    }

    public Date getDateLabel() {
        try {
            String s = dateLabel.getText().replaceAll("Текущее дата/время: ", "");
            Date date = simpleDateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

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
            notes.setDate(new Timestamp(getDateLabel().getTime()));
            DBHelper.addNoteToDB(notes);

            saveClicked = true;
            addStage.close();
        }

    }


}
