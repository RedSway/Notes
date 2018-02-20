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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private ObservableList<Notes> notesDate = FXCollections.observableArrayList();
    private ExecutorService service = Executors.newFixedThreadPool(1);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Notes");

        initRootLayout();

        Thread thread = new Thread(this::ini);
        thread.start();
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
            controller.setDateLabel(new Date());

            stage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void ini() {
        List<Notes> listNotes = null;
        try {
            Future<List<Notes>> future = service.submit(DBHelper::loadDB);
            listNotes = future.get();;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdownNow();

        notesDate.addAll(listNotes);
    }

    public Main() {}

    public ObservableList<Notes> getNotesDate() {
        return notesDate;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
