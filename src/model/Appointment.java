package model;

import java.sql.Timestamp;

/**This is an appointment class. It allows me to create instances of different appointment objects*/
public class Appointment {
    //Creating fields/variables for appointment object
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Timestamp startDateAndTime;
    private Timestamp endDateAndTime;
    private int customerID;
    private int userID;

    /**This is a constructor for the appointment class
     @param appointmentID,title,description,location,contact,type,startDatAndTime,EndDateAndTime,customerID,userID characteristics for appointment class
     */
    //Constructor for class when Appointment ID is known
    public Appointment(int appointmentID,String title, String description, String location, String contact, String type, Timestamp startDateAndTime, Timestamp endDateAndTime, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.customerID = customerID;
        this.userID = userID;
    }
    /**This is an alternative constructor for the Appointment class
     @param title,description,location,contact,type,startDateAndTime,endDate,customerID,userID characteristics/variables for appointment class
     */
    public Appointment(String title, String description, String location, String contact, String type, Timestamp startDateAndTime, Timestamp endDateAndTime, int customerID, int userID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.customerID = customerID;
        this.userID = userID;
    }

    /**This method is a getter for the title field
     @return a title for an appointment
     */
    public String getTitle() {
        return title;
    }

    /**This is a setter for the title field
     * @param title for appointment */
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method is a getter for the description field
     @return a description for an appointment
     */
    public String getDescription() {
        return description;
    }

    /**This is a setter for the description field
     * @param description for appointment*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**This method is a getter for the location field
     @return a location for an appointment
     */
    public String getLocation() {
        return location;
    }

    /**This is a setter for the location field
     * @param location for appointment */
    public void setLocation(String location) {
        this.location = location;
    }

    /**This method is a getter for the contact field
     @return a contact for an appointment
     */
    public String getContact() {
        return contact;
    }

    /**This is a setter for the contact field
     * @param contact for appointment*/
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**This method is a getter for the type field
     @return a type for an appointment
     */
    public String getType() {
        return type;
    }

    /**This is a setter for the type field
     * @param type for appointment*/
    public void setType(String type) {
        this.type = type;
    }

    /**This method is a getter for the startDateAndTime field
     @return a startDateAndTime for an appointment
     */
    public Timestamp getStartDateAndTime() {
        return startDateAndTime;
    }

    /**This is a setter for the startDateAndTime field
     * @param startDateAndTime for appointment */
    public void setStartDateAndTime(Timestamp startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    /**This method is a getter for the endDateAndTime field
     @return an endDateAndTime for an appointment
     */
    public Timestamp getEndDateAndTime() {
        return endDateAndTime;
    }

    /**This is a setter for the endDateAndTime field
     * @param endDateAndTime for appointment*/
    public void setEndDateAndTime(Timestamp endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    /**This method is a getter for the customerID field
     @return a customerID for an appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**This is a setter for the customerID field
     * @param customerID for appointment*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method is a getter for the userID field
     @return a userID for an appointment
     */
    public int getUserID() {
        return userID;
    }

    /**This is a setter for the userID field
     * @param userID for appointment*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method is a getter for the appointmentID field
     @return an appointmentID for an appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**This is a setter for the appointmentID field
     * @param appointmentID for appointment*/
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
}
