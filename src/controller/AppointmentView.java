package controller;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Appointment;
import model.CustomerRecords;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**This class is a controller for the appointment view form
 */
public class AppointmentView {

    public TableView appointmentTable;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startTimeCol;
    public TableColumn endTimeCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public RadioButton allAppsRadioButton;
    public ToggleGroup differentAppViews;
    public RadioButton monthlyAppsRadioButton;
    public RadioButton weeklyAppsRadioButton;
    public Button updateAppButton;
    public Button addAppButton;
    public Button deleteAppButton;
    public Button backButton;

    /**This method sets up the table view of appointments once the form is opened up
     */
    public void initialize () {
        appointmentTable.setItems(CustomerRecords.getAllAppointments());

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**This method displays all appointments in table similar to initialize() method
     * @param actionEvent all appointments radio button selected
     */
    public void allAppsRadioButtonSelected(ActionEvent actionEvent) {
        appointmentTable.setItems(CustomerRecords.getAllAppointments());

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**This method displays the upcoming appointments withing the next 30 days
     * @param actionEvent appointments this month radio button selected
     */
    public void monthlyAppsRadioButtonSelected(ActionEvent actionEvent) {

        //Old cold for 30 days out
        /*
        ObservableList<Appointment> appsWithinMonth = FXCollections.observableArrayList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusDays(30);

        for(Appointment someApp: CustomerRecords.getAllAppointments()){
            LocalDateTime currentAppStart = someApp.getStartDateAndTime().toLocalDateTime();
            if(currentAppStart.isAfter(currentDateTime) || currentAppStart.isEqual(currentDateTime)){
                if(currentAppStart.isBefore(futureDateTime) || currentAppStart.isEqual(futureDateTime)){
                    appsWithinMonth.add(someApp);
                }
            }
        }

        appointmentTable.setItems(appsWithinMonth);

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
         */
    }

    /**Method displays upcoming appointments within the next 7 days
     * @param actionEvent weekly appointments radio button selected
     */
    public void weeklyAppsRadioButtonSelected(ActionEvent actionEvent) {
        ObservableList<Appointment> appsWithinMonth = FXCollections.observableArrayList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusDays(7);

        for(Appointment someApp: CustomerRecords.getAllAppointments()){
            LocalDateTime currentAppStart = someApp.getStartDateAndTime().toLocalDateTime();
            if(currentAppStart.isAfter(currentDateTime) || currentAppStart.isEqual(currentDateTime)){
                if(currentAppStart.isBefore(futureDateTime) || currentAppStart.isEqual(futureDateTime)){
                    appsWithinMonth.add(someApp);
                }
            }
        }

        appointmentTable.setItems(appsWithinMonth);

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**This method takes the user to the UpdateOrAddAppointment form once the update button is clicked
     * @param actionEvent update button clicked
     * @throws IOException generated by FXML loader
     */
    public void updateAppButtonClicked(ActionEvent actionEvent) throws IOException {
        //need to keep user on page if no customer has been selected
        if(appointmentTable.getSelectionModel().getSelectedItem() == null){
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateOrAddAppointment.fxml"));
        loader.load();

        //Creating instance of updateOrAddAppointment controller so I can call sendAppointment method
        UpdateOrAddAppointment updateOrAddAppointmentFormController = loader.getController();
        updateOrAddAppointmentFormController.sendAppointment((Appointment) appointmentTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method takes the user to the UpdateOrAddAppointment form once the add button is clicked
     * @param actionEvent add appointment button clicked
     * @throws IOException generated by FXML loader
     */
    public void addAppButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateOrAddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add New Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**Method deletes an appointment
     * @param actionEvent delete appointment button clicked
     * @throws SQLException generated by SQL query
     */
    public void deleteAppButtonClicked(ActionEvent actionEvent) throws SQLException {
        if(appointmentTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        else {
            Appointment someApp = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
            int appID = someApp.getAppointmentID();
            String type = someApp.getType();
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appID + ";";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.executeUpdate();

            CustomerRecords.getCustomer(someApp.getCustomerID()).deleteAppointment(someApp);
            CustomerRecords.getAllAppointments().remove(someApp);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment with appointment ID# " + appID + " for " + type + " was cancelled.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    /**Method takes user back to CustomerList form
     * @param actionEvent back button clicked
     * @throws IOException generated by FXML loader
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerList.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Customer List Form");
        stage.setScene(scene);
        stage.show();
    }

}
