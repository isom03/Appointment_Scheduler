package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.CustomerRecords;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**This class is controller for the Login form
 */
public class LoginForm {
    public Label zoneID;
    public Button signInButton;
    public PasswordField passwordTxtField;
    public TextField usernameTextField;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label companyName;

    /**Method determines users location and displays login form in appropriate language when class is initialized
     * @throws IOException generated when getting resource bundle
     */
    public void initialize () throws IOException{
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());
        zoneID.setText(localZoneID.toString());

        if(Locale.getDefault().getLanguage().equals("fr")){
            ResourceBundle rb = ResourceBundle.getBundle("main/Nat",Locale.getDefault());
            usernameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            companyName.setText(rb.getString("Global") + " " + rb.getString("Consulting"));

            signInButton.setText((rb.getString("Sign") + " " + rb.getString("in")));
            usernameTextField.setPromptText(rb.getString("Type") + " " + rb.getString("username"));
            passwordTxtField.setPromptText(rb.getString("Type") + " " + rb.getString("password"));

        }
    }

    /**This method takes a user to the Customer list page once signed in.
     It also writes user logins (successful and unsuccessful) to a text file
     Lastly it allows the log page to be displayed in French
     @param actionEvent modify part button being clicked
     @throws IOException when unable to locate "CustomerList.fxml"
     */
    public void signInButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        //Creating and implementing SQL query in Java. Result of query gets placed in ResultSet rs
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        String fileName = "src/textFiles/login_activity";
        FileWriter fWriter = new FileWriter(fileName,true);
        PrintWriter outputFile = new PrintWriter(fWriter);


        String username = usernameTextField.getText();
        String password = passwordTxtField.getText();

        boolean loginInfoCorrect = false;

        //Iterating through result set and checking to see is user inputs matches username and password in database
        while(rs.next()){
            if(username.equals(rs.getString("User_Name"))){
                if(password.equals(rs.getString("Password"))){
                    loginInfoCorrect = true;

                    //need to get date and time and write successful login in attempt to txt.file
                    outputFile.println("User " + username + " successfully logged in at " + java.time.Clock.systemUTC().instant() + " UTC");

                    Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerList.fxml"));
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 600);
                    stage.setTitle("Customer List Form");
                    stage.setScene(scene);
                    stage.show();

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    LocalDateTime currentDateTimePlus15Min = currentDateTime.plusMinutes(15);
                    boolean upcomingAppointments = false;

                    for(Appointment someApp:CustomerRecords.getAllAppointments()){
                        LocalDateTime appStart = someApp.getStartDateAndTime().toLocalDateTime();
                        if(appStart.isAfter(currentDateTime) && (appStart.isBefore(currentDateTimePlus15Min) || appStart.isEqual(currentDateTimePlus15Min))){
                            upcomingAppointments = true;
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have an appointment with appointment ID#" + someApp.getAppointmentID()
                            + " starting at " + appStart);
                            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            alert.showAndWait();
                        }
                    }

                    if(!upcomingAppointments) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have no upcoming appointments");
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();
                    }
                    //Breaking out of while loop once I've found user has entered correct login information
                    break;
                }
            }
        }

        //creating alert to advise user they entered an incorrect username or password
        if(!loginInfoCorrect){

            //need to write to text file current time and failed login for user
            outputFile.println("User " + username + " gave invalid log-in at " + java.time.Clock.systemUTC().instant() + " UTC");

            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password entered");
            if (Locale.getDefault().getLanguage().equals("fr")) {
                ResourceBundle rb = ResourceBundle.getBundle("main/Nat",Locale.getDefault());
                alert.setContentText(rb.getString("Incorrect") + " " + rb.getString("username") + " " + rb.getString("or") + " " + rb.getString("password"));
            }
            alert.showAndWait();
        }
        outputFile.close();
    }
}
