package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.TimeZone;

/**This is the CustomerRecords class. It allows me to keep track of all customer records including all appointments*/
public class CustomerRecords {
    //Fields for class
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    //Creating list that of countries that I can get at any point throughout the application
    private static ObservableList<String> countryList = FXCollections.observableArrayList();

    //Creating list that can store list of divisions that I can get at any point throughout application
    private static ObservableList<String> usDivisionList = FXCollections.observableArrayList();

    private static ObservableList<String> ukDivisionList = FXCollections.observableArrayList();

    private static ObservableList<String> canadaDivisionList = FXCollections.observableArrayList();

    private static ObservableList<String > contactList = FXCollections.observableArrayList();

    private  static ObservableList<Integer> userList = FXCollections.observableArrayList();

    private static ObservableList<String> typesOfApps = FXCollections.observableArrayList();

    /**This method initializes the list containing all appointments regardless of customer*/
    public static void setAllAppointments(){
        for(Customer aCustomer:customerList){
            for(Appointment aAppointment:aCustomer.getScheduledAppointments()){
                allAppointments.add(aAppointment);
            }
        }
    }

    /**This method is a getter for the list of all appointments*/
    public static ObservableList<Appointment> getAllAppointments () {
        return allAppointments;
    }

    /**This method allows an appointment to be added to the allAppointments list
     * @param appointment
     */
    public static void addToAllAppointments (Appointment appointment) {
        allAppointments.add(appointment);
    }

    /**Method returns a list of all UK divisions
     * @return ukDivisionList
     */
    public static ObservableList<String> getUkDivisionList() {
        return ukDivisionList;
    }

    /**Method reads through database looking for divisions from the UK once it locates one it adds it the UK division list
     * @throws SQLException generated from SQL query
     */
    public static void setUkDivisionList() throws SQLException {
        String sql = "SELECT first_level_divisions.Division, countries.Country FROM first_level_divisions" +
                " JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE Country = 'UK';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            ukDivisionList.add(rs.getString("Division"));
        }
    }

    /**Method returns a list of all Canadian divisions
     * @return canadaDivisionList
     */
    public static ObservableList<String> getCanadaDivisionList() {
        return canadaDivisionList;
    }

    /**Method does SQL query looking for Canadian divisions then adds each division to canadaDivisionList
     * @throws SQLException generated from SQL query
     */
    public static void setCanadaDivisionList() throws SQLException {
        String sql = "SELECT first_level_divisions.Division, countries.Country FROM first_level_divisions" +
                " JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE Country = 'Canada';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            canadaDivisionList.add(rs.getString("Division"));
        }
    }
    /**Method sets up US division list from SQL query
     * @throws SQLException generated from SQL query
     */
    public static void setUSDivisionList() throws SQLException {
        String sql = "SELECT first_level_divisions.Division, countries.Country FROM first_level_divisions" +
                " JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE Country = 'U.S';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            usDivisionList.add(rs.getString("Division"));
        }
    }

    /**Method is a getter for the usDivisionList
     * @return usDivisionList
     */
    public static ObservableList<String> getUSDivisionList(){
        return usDivisionList;
    }

    /**Method is a setter for the countryList
     * @throws SQLException generated from SQL query
     */
    public static void setCountryList() throws SQLException{
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            countryList.add(rs.getString("Country"));
        }
    }

    /**Method is a getter for the countryList
     * @return countryList
     */
    public static ObservableList<String> getCountryList() {
        return countryList;
    }

    /**Method does a SQL query to help build a list of the available type of appointments
     * @throws SQLException generated from SQL query
     */
    public static void setTypeofAppsList () throws SQLException {
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            typesOfApps.add(rs.getString("Type"));
        }
    }

    /**Method is a getter for the typesOfApps list
     * @return typeOfApps
     */
    public static ObservableList<String> getTypesOfApps (){
        return typesOfApps;
    }

    /**Method does an SQL query in database to obtain/create list of all contacts in database
     * @throws SQLException generated from SQL query
     */
    public static void setContactList () throws SQLException{
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            contactList.add(rs.getString("Contact_Name"));
        }
    }

    /**Method returns a list of all contacts in database
     * @return contactList
     * */
    public static ObservableList<String > getContactList (){
        return contactList;
    }

    public static void setUserList () throws SQLException{
        String sql = "SELECT User_ID FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            userList.add(rs.getInt("User_ID"));
        }
    }

    /**Method is a getter for the userList
     * @return userList
     */
    public static ObservableList<Integer> getUserList () {
        return userList;
    }

    /**Method returns a division ID given the name of a division
     * @param division
     * @return divisionID
     * @throws SQLException generated from SQL query
     */
    public static String getDivisionID (String division) throws SQLException{
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + division + "';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String divisionID = String.valueOf(rs.getInt("Division_ID"));
        return divisionID;
    }

    /**Method looks through database using a customerName and returns the customer's ID#
     * @param customerName
     * @return customerID
     * @throws SQLException generated from SQL query
     */
    public static int getCustomerID (String customerName) throws SQLException{
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = '" + customerName + "';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int customerID = rs.getInt("Customer_ID");
        return customerID;
    }


    /**Method should return a specific customer given a customerID
     * @param customerID
     * @return theCustomer
     */
    public static Customer getCustomer(int customerID){
        Customer theCustomer = null;
        for(Customer someCustomer: customerList) {
            if (someCustomer.getCustomerID() == customerID) {
                theCustomer = someCustomer;
                break;
            }
        }
        return theCustomer;
    }

    /**Method performs SQL query to get a contact's name from the database given an contactID
     * @param contactID
     * @return contactName
     * @throws SQLException generated from SQL query
     */
    public static String getContactName (int contactID) throws SQLException {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactID + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Contact_Name");
    }

    /**Method performs an SQL query to get a contactID from the database given a contactName
     * @param contactName
     * @return contactID
     * @throws SQLException generated from SQL query
     */
    public static String getContactID (String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contactName + "';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int contactID = rs.getInt("Contact_ID");
        String contactIDStr = String.valueOf(contactID);
        return contactIDStr;
    }

    /**Method gets and an appointmentID from the database given a start and end time of the appointment and customerID
     * @param startTime start time of appointment
     * @param endTime end time of appointment
     * @param customerID customerID # associated with appointment
     * @return appointmentID associated with appointment
     * @throws SQLException generated from SQL query
     */
    public static int getAppID (Timestamp startTime, Timestamp  endTime, int customerID) throws SQLException {
        String sql = "SELECT Appointment_ID FROM appointments WHERE Start = '" + startTime + "' AND End = '" + endTime + "' AND Customer_ID = " + customerID + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Appointment_ID");
    }


    /**Method adds a customer to the customer records and also adds customer to database
     * @param customer instance of customer I want to add
     * @throws SQLException generated from SQL query
     */
    public static void addCustomer (Customer customer) throws SQLException{
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                "VALUES ('" + customer.getCustomerName() + "', '" + customer.getAddress() + "', '" + customer.getPostalCode() +
                "', '" + customer.getPhone() + "', '" + getDivisionID(customer.getDivision()) + "');";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.executeUpdate();

        customer.setCustomerID(getCustomerID(customer.getCustomerName()));
        customerList.add(customer);
    }

    /**Method takes an instance of a customer as a parameter, it then removes the referenced customer from the database/records
     * @param customer customer I want to delete
     * @throws SQLException generated from SQL query
     */
    public static void deleteCustomer(Customer customer) throws SQLException{
        //Can't delete appointments from allAppointments while iterating through so I created a list of items that needed to be deleted
        ObservableList<Appointment> appointmentToBeDeleted = FXCollections.observableArrayList();
        //If a customer has appointments they need to be deleted before deleting customer
        if(!customer.scheduledAppointmentIsEmpty()){
            for (Appointment someApp: getAllAppointments()){
                if(someApp.getCustomerID() == customer.getCustomerID()){
                    int appID = someApp.getAppointmentID();
                    String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appID + ";";
                    PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                    ps.executeUpdate();

                    customer.deleteAppointment(someApp);
                    appointmentToBeDeleted.add(someApp);
                }
            }
            for(Appointment app: appointmentToBeDeleted){
                allAppointments.remove(app);
            }
            appointmentToBeDeleted.clear();
        }
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customer.getCustomerID() + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.executeUpdate();
        customerList.remove(customer);
    }

    /**Method takes an appointment as a parameter then adds it to the database and updates records
     * @param appointment to be added to database
     * @throws SQLException generated from SQL update
     */
    public static void addAppToDatabase (Appointment appointment) throws SQLException{
        //SQl update
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                "VALUES ('" + appointment.getTitle() + "', '" + appointment.getDescription() + "', '" + appointment.getLocation() +
                "', '" + appointment.getType() + "', '" + localTimeStampToUTC(appointment.getStartDateAndTime()) + "', '" + localTimeStampToUTC(appointment.getEndDateAndTime()) +
                "', '" + appointment.getCustomerID() + "', '" + appointment.getUserID() + "', '" + CustomerRecords.getContactID(appointment.getContact()) +
                "');";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.executeUpdate();

        //Line updates the appointment ID now that appointment has been added to database and an actual ID had has been generated
        appointment.setAppointmentID(CustomerRecords.getAppID(localTimeStampToUTC(appointment.getStartDateAndTime()),localTimeStampToUTC(appointment.getEndDateAndTime()),appointment.getCustomerID()));

        //Getting Customer the appointment was scheduled for and adding it to their list of scheduled appointments
        getCustomer(appointment.getCustomerID()).addAppointment(appointment);
        addToAllAppointments(appointment);
    }

    /**Method prints customerList, acts as troubleshooting tool*/
    public static void printList(){
        for (Customer someCustomer: customerList){
            System.out.println(someCustomer.getCustomerID() +" | " + someCustomer.getCustomerName() + " | " + someCustomer.getAddress() + " | " + someCustomer.getPostalCode() + " | " + someCustomer.getPhone() +
                    " | " + someCustomer.getDivision() + " | " + someCustomer.getCountry() + "\n");
        }
    }

    /**Method is a getter for the customerList, is used for combo-boxes
     * @return customerList list of all customers
     */
    public static ObservableList<Customer> getCustomerList(){
        return customerList;
    }

    /**Method populates all records (lists) using data from database
     * @throws SQLException from SQL query
     */
    public static void populateCustomerRecords() throws SQLException {
        //storing SQL statement in string variable to letter use as prepared statement when connecting to database
        //SQL statement joins customer, first_level_division, and country table together to place a customers
        //complete address (including division and country) in one result set I can parse through using while loop
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, first_level_divisions.Division, countries.Country" +
                " FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries" + " ON first_level_divisions.Country_ID = countries.Country_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String division = rs.getString("Division");
            String country = rs.getString("Country");


            Customer someCustomer = new Customer(customerID,customerName,address,postalCode,phone,country,division);

            String sqlForApps = "SELECT * FROM appointments WHERE Customer_ID = " + getCustomerID(someCustomer.getCustomerName());
            PreparedStatement psOne = JDBC.connection.prepareStatement(sqlForApps);
            ResultSet rsOne = psOne.executeQuery();
            while(rsOne.next()){
                Appointment someAppointment = new Appointment(rsOne.getInt("Appointment_ID"),
                       rsOne.getString("Title"),rsOne.getString("Description"),rsOne.getString("Location"),
                       getContactName(rsOne.getInt("Contact_ID")),rsOne.getString("Type"),
                        utcTimeStampToLocal(rsOne.getTimestamp("Start")),utcTimeStampToLocal(rsOne.getTimestamp("End")),
                       someCustomer.getCustomerID(),rsOne.getInt("User_ID"));
                someCustomer.addAppointment(someAppointment);
            }
            customerList.add(someCustomer);
        }

    }

    /**This method determines whether a proposed start time for a new appointment is free of overlaps
     * @param startDateAndTime proposed start time for appointment
     * @param endDateAndTime proposed end time for appointment
     * @param customerID proposed customer appointment is being scheduled for
     * @return boolean value 'false' if there are overlaps, 'true' if appointment time is valid
     */
    public static boolean validAppointmentTime (LocalDateTime startDateAndTime,LocalDateTime endDateAndTime, int customerID) {
        //Sorting through customer's list of scheduled appointments to see if there is any overlap
        //if there is an overlap there's no need to continue and i can return false
        for(Appointment someApp: CustomerRecords.getCustomer(customerID).getScheduledAppointments()){
            LocalDateTime someAppStart = someApp.getStartDateAndTime().toLocalDateTime();
            LocalDateTime someAppEnd = someApp.getEndDateAndTime().toLocalDateTime();
            //Proposed app startDateAndTime falls in between current appointments start and end date/time
            if((startDateAndTime.isAfter(someAppStart) || startDateAndTime.isEqual(someAppStart)) && startDateAndTime.isBefore(someAppEnd) ){
                return false;
            }
            //Proposed app endDateAndTime falls between current appointments start and end date/time
            if (endDateAndTime.isAfter(someAppStart) && (endDateAndTime.isBefore(someAppEnd) || endDateAndTime.isEqual(someAppEnd))){
                return false;
            }
            if ((startDateAndTime.isEqual(someAppStart) || startDateAndTime.isBefore(someAppStart)) && (endDateAndTime.isEqual(someAppEnd) || endDateAndTime.isAfter(someAppEnd))){
                return false;
            }
        }
        return true;
    }

    /**This method determines whether a proposed start time for an updated appointment is free of overlaps
     * @param startDateAndTime proposed start time for appointment
     * @param endDateAndTime proposed end time for appointment
     * @param customerID proposed customer appointment is being scheduled for
     * @param appID appointmentID for appointment being updated
     * @return boolean value based on whether there is an overlap in appointments
     */
    public static boolean validAppointmentTime (LocalDateTime startDateAndTime,LocalDateTime endDateAndTime, int customerID, int appID) {
        //Sorting through customer's list of scheduled appointments to see if there is any overlap
        //if there is an overlap there's no need to continue and i can return false
        for(Appointment someApp: CustomerRecords.getCustomer(customerID).getScheduledAppointments()){
            if(someApp.getAppointmentID() != appID){
                LocalDateTime someAppStart = someApp.getStartDateAndTime().toLocalDateTime();
                LocalDateTime someAppEnd = someApp.getEndDateAndTime().toLocalDateTime();
                //Proposed app startDateAndTime falls in between current appointments start and end date/time
                if((startDateAndTime.isAfter(someAppStart) || startDateAndTime.isEqual(someAppStart)) && startDateAndTime.isBefore(someAppEnd) ){
                    return false;
                }
                //Proposed app endDateAndTime falls between current appointments start and end date/time
                if (endDateAndTime.isAfter(someAppStart) && (endDateAndTime.isBefore(someAppEnd) || endDateAndTime.isEqual(someAppEnd))){
                    return false;
                }
                if ((startDateAndTime.isEqual(someAppStart) || startDateAndTime.isBefore(someAppStart)) && (endDateAndTime.isEqual(someAppEnd) || endDateAndTime.isAfter(someAppEnd))){
                    return false;
                }
            }
        }
        return true;
    }

    /**Method takes a UTC Timestamp and converts it to local Timestamp
     * @param aTimeStamp a utc timestamp
     * @return TimestampInLocalTime
     */
    public static Timestamp utcTimeStampToLocal (Timestamp aTimeStamp){

        LocalDate date = aTimeStamp.toLocalDateTime().toLocalDate();
        LocalTime time = aTimeStamp.toLocalDateTime().toLocalTime();
        ZoneId zoneId = ZoneId.of("UTC");

        ZonedDateTime utcZDT = ZonedDateTime.of(date,time,zoneId);

        ZonedDateTime utcToLocalZDT = utcZDT.withZoneSameInstant(ZoneId.systemDefault());

        Timestamp tsInLocalTime = Timestamp.valueOf(utcToLocalZDT.toLocalDateTime());

        return tsInLocalTime;
    }

    /**Method takes a Timestamp in user's local time and returns it in UTC
     * @param someTimestamp a local timestamp
     * @return timestampInUTCTime
     */
    public static Timestamp localTimeStampToUTC (Timestamp someTimestamp) {
        LocalDate date = someTimestamp.toLocalDateTime().toLocalDate();
        LocalTime time = someTimestamp.toLocalDateTime().toLocalTime();
        ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime localZDT = ZonedDateTime.of(date,time,localZoneID);

        ZonedDateTime localToUTCZDT = localZDT.withZoneSameInstant(ZoneId.of("UTC"));

        Timestamp tsInUTCTime = Timestamp.valueOf(localToUTCZDT.toLocalDateTime());

        return tsInUTCTime;
    }

    public enum Months {
        January, February, March, April, May, June, July, August, September, October, November, December
    }

    public enum Days {
        Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
    }
}
