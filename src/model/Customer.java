package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This is my Customer class. It allows me to create instances of different customers*/
public class Customer {
    //Creating fields for customer
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;
    private ObservableList <Appointment> scheduledAppointments = FXCollections.observableArrayList();

    /**This method is a constructor for the Customer class
     * @param customerID,customerName,address,postalCode,phone,country,division characteristics of customer class*/
    public Customer (int customerID,String customerName,String address,String postalCode,String phone,String country,String division){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

    /**THis method is a constructor for the Customer class when the customerID is unknown
     * @param customerName,address,postalCode,phone,country,division characteristics of customer class*/
    public Customer (String customerName,String address,String postalCode,String phone,String country,String division) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

    /**Method adds an appointment to a customers list of scheduled appointments
     * @param appointment to be added to customer's list of scheduled appointments*/
    public void addAppointment(Appointment appointment) {
        scheduledAppointments.add(appointment);
    }

    /**Method deletes an appointment from a customers list of scheduled appointments
     * @param appointment to be deleted from customer's scheduled appointments*/
    public void deleteAppointment(Appointment appointment){
        scheduledAppointments.remove(appointment);
    }

    /**Method checks to see if a customer has any appointments scheduled
     * @return a boolean value based on whether the list is empty
     */
    public boolean scheduledAppointmentIsEmpty(){
        boolean result = this.scheduledAppointments.isEmpty();
        return result;
    }

    /**Method is a getter for the customer's list of scheduled apppointments
     * @return an observable list of scheduledAppointments
     */
    public ObservableList<Appointment> getScheduledAppointments(){
        return this.scheduledAppointments;
    }

    /**Method gets a specific appointment given and appointmentID
     * @param appointmentID for appointment needed
     * @return an appointment
     */
    public Appointment getAppointment (int appointmentID){
        Appointment theApp = null;
        for(Appointment someApp:scheduledAppointments){
            if(someApp.getAppointmentID() == appointmentID){
                theApp = someApp;
            }
        }
        return theApp;
    }

    /**Method is a getter for the customerID field
     * @return customerID of customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**This method is a setter for the customerID field
     * @param customerID new customerID for customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**Method is a getter for the name field
     * @return name of customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**This method is a setter for the customerName field
     * @param customerName name for customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**Method is a getter for the address field
     * @return address of customer
     */
    public String getAddress() {
        return address;
    }

    /**This method is a setter for the address field
     * @param address for customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**Method is a getter for the postalCode field
     * @return postalCode of customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**This method is a setter for the postalCode field
     * @param postalCode for customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**Method is a getter for the country field
     * @return country of customer
     */
    public String getCountry() {
        return country;
    }

    /**This method is a setter for the country field
     * @param country for customer
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**Method is a getter for the division field
     * @return division of customer
     */
    public String getDivision() {
        return division;
    }

    /**This method is a setter for the division field
     * @param division for customer
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**Method is a getter for the phone field
     * @return phone of customer
     */
    public String getPhone() {
        return phone;
    }

    /**This method is a setter for the phone field
     * @param phone for customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
