package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.CustomerRecords;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

/**This class is a controller for the CustomerList form
 */
public class CustomerList {
    public TableView customerTable;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn divisionCol;
    public TableColumn countryCol;
    public TableColumn phoneNumCol;

    /**Method populates customer table once the class is initialized
     */
    public void initialize (){
        customerTable.setItems(CustomerRecords.getCustomerList());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    /**This method takes a user to the Add/Update Customer Form after update customer button is clicked.
     @param actionEvent modify part button being clicked
     @throws IOException when unable to locate "AddOrUpdateCustomer.fxml"
     */
    public void updateCustomerButtonClicked(ActionEvent actionEvent) throws IOException{
        //need to keep user on page if no customer has been selected
        if(customerTable.getSelectionModel().getSelectedItem() == null){
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddOrUpdateCustomer.fxml"));
        loader.load();

        //Creating instance of addOrUpdateCustomer controller so I can call sendCustomer method
        AddOrUpdateCustomer addOrUpdateCustomerFormController = loader.getController();
        addOrUpdateCustomerFormController.sendCustomer((Customer)customerTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }



    /**This method takes a user to the Add/Update Customer Form after add customer button is clicked.
     @param actionEvent modify part button being clicked
     @throws IOException when unable to locate "AddOrUpdateCustomer.fxml"
     */
    public void addCustomerButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddOrUpdateCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add New Customer Form");
        stage.setScene(scene);
        stage.show();
    }

    /**Method deletes a customer if they have no appointments currently scheduled
     * @param actionEvent delete customer button clicked
     * @throws SQLException generated from SQL query
     */
    public void deleteCustomerButtonClicked(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this customer?");
            //Creating container
            Optional<ButtonType> result = alert.showAndWait();

            //returns a boolean if button is in container
            if(result.isPresent() && result.get() == ButtonType.OK){
                CustomerRecords.deleteCustomer(selectedCustomer);
            }
        }
    }

    /** This method opens up new scene for report page/form
     * @param actionEvent report page button clicked
     * @throws IOException generated from FXML loader
     */
    public void reportPageButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Report Page");
        stage.setScene(scene);
        stage.show();
    }

    /**Method takes uses lambda expression display AppointmentView form
     * @param actionEvent view appointments button clicked
     * @throws IOException generated from FXML loader
     */
    public void viewAppointmentsButtonClicked(ActionEvent actionEvent) throws IOException{

        //Lambda expression
        NewScene displayScene = someView -> {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + someView));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Appointment View");
            stage.setScene(scene);
            stage.show();
        };

        displayScene.newScene("AppointmentView.fxml");
    }

}
