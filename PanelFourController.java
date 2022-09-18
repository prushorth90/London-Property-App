import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.nio.charset.Charset;
import java.sql.Timestamp;
/**
 * A controller for panel 4 
 *
 * @author Michael Kolling, Manivannan Prushorth,Hieu Trung Nguyen and Alexander Baker
 * @version 2021
 */
public class PanelFourController extends MainController 
{
    private ModelFour model4;
    private List<String> details = new ArrayList<>();
    private static final String CUSTOMER_FILE = "customerInfo.txt";
    private ObservableList<Integer> toPriceOptions;
    private ObservableList<Integer> fromPriceOptions;
    @FXML
    private ComboBox<Integer> fromBox, toBox;
    @FXML
    private Button backBtn, nextBtn;
    @FXML
    private ComboBox boroughBox;
    
    @FXML
    private Button submitButton;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
   
    /**
     * Check whether the user selected price range is valid. if the range is
     * not valid display invalid range message to user. Each time the user
     * change the price range, the color corresponding to an area is also updated.
     */
    public void checkValidRange() {
        String fromPriceString = fromBox.getEditor().getText();
        String toPriceString = toBox.getEditor().getText();

        if (!fromPriceString.isEmpty() && !toPriceString.isEmpty()) {
            try {
                int fromPrice = Integer.parseInt(fromPriceString);
                int toPrice = Integer.parseInt(toPriceString);

                setCurrFromSelection(fromPrice);
                setCurrToSelection(toPrice);

                if (isValidRange(fromPrice, toPrice)) {
                    nextBtn.setDisable(false);
                    backBtn.setDisable(false);
                    model4.setAvailablePropertiesInRange(getAvailablePropertiesWithinRange());
                    boroughBox.getItems().clear();
                    // propertyBox.getItems().clear();
                    boroughBox.getItems().addAll(model4.getBoroughsInPriceRange());
                    // propertyBox.getItems().addAll(model4.getPropertiesIDInPriceRange());
                } else {
                    nextBtn.setDisable(true);
                    backBtn.setDisable(true);
                    boroughBox.getItems().clear();
                    displayInvalidRangeMessage();
                }
            } catch (NumberFormatException e) {
                displayInvalidRangeMessage();
                boroughBox.getItems().clear();
            }
        } else {
            nextBtn.setDisable(true);
            backBtn.setDisable(true);
            boroughBox.getItems().clear();
        }
    }

    /**
     * Display invalid range message and disable navigation button
     */
    private void displayInvalidRangeMessage() {
        createInvalidRangeMessage();
        nextBtn.setDisable(true);
        backBtn.setDisable(true);
    }

    /**
     * Make the current window to transit into the next panel display
     *
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goForward(ActionEvent event) throws IOException {
        URL location = getClass().getResource("panel1.fxml");
        changePanel(event, location);
    }

    /**
     * Make the current window to transit into the previous panel display
     *
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        URL location = getClass().getResource("panel3.fxml");
        changePanel(event, location);
    }
    
    /**
     * Initialise the fxml element for panel 4
     */
    public void initialize() 
    {
        fromPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);
        toPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);
        
        fromBox.setItems(fromPriceOptions);
        toBox.setItems(toPriceOptions);
        
        if (getCurrFromSelection() != null && getCurrToSelection() != null) {
            fromBox.getEditor().setText(getCurrFromSelection().toString());
            toBox.getEditor().setText(getCurrToSelection().toString());
        }
        model4 = new ModelFour();
        model4.setAvailablePropertiesInRange(getAvailablePropertiesWithinRange());
        
        boroughBox.getItems().addAll(model4.getBoroughsInPriceRange());
        checkValidRange();
    }
    
    /**
     * When submit button clicked update the text file with the user details
     * 
     * For try catch block used some code provided in 
     * week 11 FILE -Based I/O slides provided by Michael Kolling 
     * slide 8 and video "File output and try-with-resource".We added some lines. 
     */
    public void submitForm(ActionEvent e)
    {
        if (hasEmptyDetails()) {
            createInvalidDetailsMessage();
            return;
        }
        
        String nameValue = name.getText().trim().toLowerCase();
        String emailValue = email.getText().trim().trim().toLowerCase();
        String boroughValue = boroughBox.getValue().toString().trim();
        String newData = nameValue + " " + emailValue + " " + boroughValue + " ";
        
        if (hasAlreadyInputted(newData)) {
            createRepeatedDataMessage();
            return;
        }
        
        Path customerFile = Paths.get(CUSTOMER_FILE).toAbsolutePath();
        
        try (FileWriter writer = new FileWriter(customerFile.toString(), true)) {   
            details.add(nameValue);
            details.add(emailValue);
            details.add(boroughValue);
            
            for (String field : details) {
                writer.append(field.toString() + " ");
            }
            writer.append("\n");
            details.clear();
            createReceivedMessage();
        }
        catch (IOException ev) {
            System.out.println(ev);
        } 
    }
    
    /**
     *@return true If one of the boxes are left unfilled.
     */
    private boolean hasEmptyDetails()
    {
        if (name.getText().trim().isEmpty() || email.getText().trim().isEmpty() || boroughBox.getValue() == null) {
            return true;
        }
        return false;
    }
    
    /**
     * Check If same details inputted more than once by user 
     * 
     * 
     * week 11 FILE -Based I/O slides provided by Michael Kolling, try catch block
     * slide 11 and "Reading from a file" video. We modified some lines. 
     */
    private boolean hasAlreadyInputted(String newData)
    {
        Charset charset = Charset.forName("US-ASCII");
        Path path = Paths.get(CUSTOMER_FILE).toAbsolutePath();
        try (BufferedReader reader = Files.newBufferedReader(path,charset)) {
            String line = reader.readLine(); 
            while (line != null) {
                
                if (line.equals(newData)) {
                    return true;
                }
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * User has not filled all details in so alert them. 
     */
    public void createInvalidDetailsMessage() 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Details empty");
        alert.setHeaderText(null);
        alert.setContentText("Have you filled all details correctly");
        alert.showAndWait();
    }
    
    /**
     * User tried submitting same details again so alert them. 
     */
    public void createRepeatedDataMessage() 
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Repeated data");
        alert.setHeaderText(null);
        alert.setContentText("WHOOPS! IT SEEMS YOU ENTERED THE SAME DATA ALREADY");
        alert.showAndWait();
    }
    
    /**
     * User is informed that they have booked appointment. 
     */
    private void createReceivedMessage() 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database updated");
        alert.setHeaderText(null);
        alert.setContentText("Our database has been updated. We hope to respond in a few days");
        alert.showAndWait();
    }
}
