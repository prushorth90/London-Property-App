import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * A GUI class that load GUI element for a new window which display the
 * list of properties available for rent within an area.
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class AreaWindow {

    /**
     *  Load fxml elements and set the stage for the layout to be
     *  display.
     *
     * @param areaSelected The circle ID of the user selected area
     * @throws Exception
     */
    public void display(String areaSelected) throws Exception
    {
        Stage stage = new Stage();

        URL location = getClass().getResource("areaView.fxml");
        FXMLLoader ld = new FXMLLoader(location);
        Pane root = ld.load();

        AreaViewController controller = ld.getController();
        controller.setAreaSelected(areaSelected);

        Scene scene = new Scene(root, 600,500);

        stage.setTitle("Properties in the area");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

}
