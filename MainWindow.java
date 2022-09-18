import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * A GUI class that load the main window display when the user first
 * run the application.
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class MainWindow extends Application
{
    /**
     * When the application run. Load the fxml loader and setup the layout
     * for display.
     *
     * @param stage The initial stage when the application first run
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        URL location = getClass().getResource("panel1.fxml");
        FXMLLoader ld = new FXMLLoader(location);
        Pane root = ld.load();

        Scene scene = new Scene(root, 800,600);
        
        stage.setTitle("AirBnB");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launch the application
     */
    public static void main(String[] args) {launch(args);}
}