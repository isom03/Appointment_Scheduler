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
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.CustomerRecords;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Class is a controller for the report page
 */
public class ReportPage {
    public ComboBox<String> monthComboBox;
    public ComboBox<String> typeComboBox;
    public TextField numAppsTextField;
    public ComboBox<String> contactComboBox;
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
    public ComboBox<String> customerComboBox;
    public TextField numOfAppsForCust;
    public Button backButton;
    private ObservableList<String> listOfMonths = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /**Method setups up combo-boxes for each report
     */
    public void initialize() {
        ObservableList<String> listOfCustNames = FXCollections.observableArrayList();
        for (Customer someCustomer : CustomerRecords.getCustomerList()){
            listOfCustNames.add(someCustomer.getCustomerName());
        }
        monthComboBox.setItems(listOfMonths);
        typeComboBox.setItems(CustomerRecords.getTypesOfApps());
        contactComboBox.setItems(CustomerRecords.getContactList());
        customerComboBox.setItems(listOfCustNames);
    }


    /**Method takes user back to CustomerList form
     * @param actionEvent back button clicked
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerList.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Customer List");
        stage.setScene(scene);
        stage.show();
    }

    /**Method populates appointment table when once a contact is selected
     * @param actionEvent contact selected from contactComboBox
     */
    public void contactSelected(ActionEvent actionEvent) {
        String contactName = contactComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> listOfContactsApps = FXCollections.observableArrayList();
        for (Appointment someApp: CustomerRecords.getAllAppointments()){
            if(contactName.equals(someApp.getContact())){
                listOfContactsApps.add(someApp);
            }
        }
        appointmentTable.setItems(listOfContactsApps);

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

    /**Method generates report for total number of apps base on month and type if both comboboxes are selected
     * @param actionEvent month selected from monthComboBox
     * @throws SQLException generated from SQL query
     */
    public void monthSelected(ActionEvent actionEvent) throws SQLException {
        String typeOfApp = typeComboBox.getSelectionModel().getSelectedItem();
        String monthOfApp = monthComboBox.getSelectionModel().getSelectedItem();
        int monthAsInt = listOfMonths.indexOf(monthOfApp) + 1;
        if(typeOfApp == null){
            return;
        }
        else{
            String sql = "SELECT count(*) FROM appointments where month(start) = " + monthAsInt + " AND Type = '" + typeOfApp +"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            numAppsTextField.setText(rs.getString("count(*)"));
        }
    }

    /**Method generates report for total number of apps base on month and type if both comboboxes are selected
     * @param actionEvent month selected from monthComboBox
     * @throws SQLException generated from SQL query
     */
    public void typeSelected(ActionEvent actionEvent) throws SQLException {
        String typeOfApp = typeComboBox.getSelectionModel().getSelectedItem();
        String monthOfApp = monthComboBox.getSelectionModel().getSelectedItem();
        if(monthOfApp == null){
            return;
        }
        else{
            int monthAsInt = listOfMonths.indexOf(monthOfApp) + 1;
            String sql = "SELECT count(*) FROM appointments where month(start) = " + monthAsInt + " AND Type = '" + typeOfApp +"';";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            numAppsTextField.setText(rs.getString("count(*)"));
        }
    }

    /**Method uses a Lambda expression to generate a report for the number of appointments a customer has scheduled
     * @param actionEvent customer selected from customerComboBox
     * @throws SQLException generated by SQL query
     */
    public void customerSelected(ActionEvent actionEvent) throws SQLException {

        //Lambda expression performs sql query for count of customers appointments
        Report numApps = customerName -> {
            int customerID = CustomerRecords.getCustomerID((customerName));
           String sql = "SELECT count(*) FROM appointments WHERE Customer_ID = " + customerID + ";";
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           rs.next();
           return rs.getString("count(*)");
       };

       numOfAppsForCust.setText(numApps.calculateNumApps(customerComboBox.getSelectionModel().getSelectedItem()));
    }
}
