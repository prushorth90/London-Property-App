import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;

/**
 * A GUI class that load the popup window which displays when the user
 * select a particular property to view more details about the property
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 202
 */
public class PropertyWindow {
    /**
     *  Load fxml elements and set the stage for the layout to be
     *  display.
     */
    public void display(AirbnbListing propertySelected) throws Exception
    {
        Stage stage = new Stage();

        URL location = getClass().getResource("propertyView.fxml");
        FXMLLoader ld = new FXMLLoader(location);
        Pane root = ld.load();

        PropertyViewController controller = ld.getController();
        controller.setPropertySelected(propertySelected);

        Scene scene = new Scene(root, 400,400);
        
        stage.setTitle("Property Details");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
}
