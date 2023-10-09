package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CustomerRecords;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Locale;


public class Main extends Application {

    /** This is the start method.
     * This method creates the main stage for displaying the main menu of the inventory management application.
     * @param primaryStage is the primary stage the main menu scene wil be displayed on
     * @throws java.io.IOException if FXMLLoader cannot locate resource
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //creating main stage to display inventory management scene
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        primaryStage.setTitle("Login Form"); //gives a title for stage
        primaryStage.setScene(new Scene(root, 600, 600)); //actual creation of scene, parameters are dimension for screen
        primaryStage.show(); // displays stage and scene

    }

    /**This is the main method.
     * This is method is used to launch application
     * @param args */
    public static void main(String[] args) throws SQLException {
        //opening database connection
        JDBC.openConnection();

        //initializing records(lists) from information in database
        CustomerRecords.populateCustomerRecords();
        CustomerRecords.setUSDivisionList();
        CustomerRecords.setUkDivisionList();
        CustomerRecords.setCanadaDivisionList();
        CustomerRecords.setCountryList();
        CustomerRecords.setTypeofAppsList();
        CustomerRecords.setContactList();
        CustomerRecords.setUserList();
        CustomerRecords.setAllAppointments();


        launch(args);

        //closing database
        JDBC.closeConnection();
    }
}
