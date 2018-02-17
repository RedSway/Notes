import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private ObservableList<Notes> notesDate = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Notes");

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/sample.fxml"));
            rootLayout = loader.load();

            RootController controller = loader.getController();
            controller.setMain(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAddNotes(Notes notes) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/addNote.fxml"));
            AnchorPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add note");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            stage.setScene(scene);

            AddNoteController controller = loader.getController();
            controller.setAddStage(stage);
            controller.setNotes(notes);

            stage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Main() {
        List<Notes> listNotes = DBHelper.loadDB();

        for (Notes note : listNotes) {
            System.out.println(note.getValue() + "---" + simpleDateFormat.format(note.getDate()));
            notesDate.add(note);
        }
    }

    public ObservableList<Notes> getNotesDate() {
        return notesDate;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
