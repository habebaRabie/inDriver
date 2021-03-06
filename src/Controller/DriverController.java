package Controller;

import java.sql.*;
import java.util.ArrayList;
import ModulesPackage.*;

public class DriverController {
    static ArrayList<String> drivers = new ArrayList<>();

    EventController event = new EventController();
    public static void driversACTIVE(int RideID){
        String sql = "select username from driver where status = 'ACTIVE' ";
        try (Connection conn = DriverManager.getConnection(Admin.url)) {
            ResultSet RS = conn.createStatement().executeQuery(sql);
            while (RS.next()){
                String name= RS.getString("username");
                drivers.add(name);
                String sql1 = "insert into RideRequest (driverName, RideID) values (?, ?)";
                try {
                    PreparedStatement ins = conn.prepareStatement(sql);
                    ins.setString(1, name);
                    ins.setInt(2, RideID);
                    ins.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> ChooseRide(String username){
        ArrayList<String> rides = new ArrayList<>();
        String sql = "select RideID from RideRequest where username = " + username;
        try(Connection con = DriverManager.getConnection(Admin.url)) {
             ResultSet Rs = con.createStatement().executeQuery(sql);
             while (Rs.next()){
                 rides.add(String.valueOf(Rs.getInt("RideID")));
             }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rides;
    }

    public void driverSetPrice(String username , double price , int rideID){
            String sql = "update RideRequest set price = ? where username = ? and RideID =?" ;
            String date ;
            try(Connection con = DriverManager.getConnection(Admin.url)) {
                PreparedStatement ins = con.prepareStatement(sql);
                ins.setDouble(1, price);
                ins.setString(2, username);
                ins.setInt(3, rideID);
                ins.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        String sql2 = "select  date  from ride where RideID = " + rideID;
        try (Connection conn = DriverManager.getConnection(Admin.url)) {
            ResultSet RS = conn.createStatement().executeQuery(sql2);
            date = String.valueOf(RS.getDate("date"));
            event.event1 (date,  username, price);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

//
//    ModulesPackage.Ride myRide;
//    UserPackage.User user;
//    ModulesPackage.Driver rideDriver;
//
//    public ModulesPackage.Ride getMyRide() {
//        return myRide;
//    }
//
//    public UserPackage.User getUser() {
//        return user;
//    }
//
//    public ModulesPackage.Driver getRideDriver() {
//        return rideDriver;
//    }
//
//    public void userRequest(UserPackage.User client) {
//        this.user = client;
//        ArrayList<ModulesPackage.Driver.ModulesPackage.Driver> myDriver = new ArrayList();
//        myRide = client.requestUserRide();
//
//        String url = "jdbc:sqlite:" + System.getProperty("user.dir")+"\\SW.db";
//        String query = "insert into ride (user, source, destination, status) values (?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(url)) {
//            PreparedStatement ins = conn.prepareStatement(query);
//            ins.setString(1, user.getUserName());
//            ins.setString(2, myRide.getSource());
//            ins.setString(3, myRide.getDestnation());
//            ins.setString(4, "PENDINGDRIVER");
//            ins.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        myDriver = searchAreas(myRide.getSource());
//        if (myDriver.size() == 0) {
//            System.out.println("There are no driver in this place");
//            myRide.setMystatus(ModulesPackage.Ride.rideStatus.REJECTED);
//            return;
//        }
//        // System.out.println(myDriver.get(0).userName);
//        driverRequest(myDriver);
//    }
//
//    public ArrayList<ModulesPackage.Driver> searchAreas(String source) { //on the user point of view
//        ArrayList<ModulesPackage.Driver> myavailableDrivers = ModulesPackage.Admin.ModulesPackage.Admin.getAllDrivers();
//        ArrayList<ModulesPackage.Driver> myDriver = new ArrayList();
//        for (ModulesPackage.Driver driver : myavailableDrivers) {
//            for (String area : driver.getMyAreas()) {
//                if (myRide.getSource().equals(area)) {
//                    myDriver.add(driver);
//                    break;
//                }
//            }
//        }
//
//        return myDriver;
//    }
//
//    public void driverRequest(ArrayList<ModulesPackage.Driver> myDriver) {
//        HashMap<ModulesPackage.Driver.ModulesPackage.Driver, Double> offers = new HashMap<ModulesPackage.Driver.ModulesPackage.Driver, Double>();
//        for (ModulesPackage.Driver driver : myDriver) {
//            System.out.println("The ride is from " + myRide.getSource() + " to " + myRide.getDestnation());
//            System.out.print("Please enter your offer to this ride: ");
//            Scanner in = new Scanner(System.in);
//            double price = in.nextDouble();
//            double driverOffer = driver.rideOffer(price);
//            offers.put(driver, driverOffer);
//        }//Map.Entry<String, Tab> entry : hash.entrySet()
//        int i = 1;
//        for (HashMap.Entry<ModulesPackage.Driver.ModulesPackage.Driver, Double> offr : offers.entrySet()) {
//            System.out.println(i + " - the driver name is : " + offr.getKey().userName + " the offer is : " + offr.getValue());
//            i++;
//        }
//        if (i == 1) {
//            System.out.println("No Drivers is valid right now");
//            return;
//        }
//        System.out.println(i + "- Cancel the trip");
//        System.out.println("Please enter the choice of offer you need :");
//        Scanner input = new Scanner(System.in);
//        int choice = input.nextInt();
//        if (choice == i) { //cancelled
//            myRide.setMystatus(rideStatus.REJECTED);
//        } else {
//            //ModulesPackage.Driver selectedOfferDriver = null;
//
//            int counter = 1;
//            for (ModulesPackage.Driver driver : offers.keySet()) {
//                if (counter == choice) {
//                    rideDriver = driver;
//                    myRide.setPrice(offers.get(driver));
//                    break;
//                }
//                counter++;
//            }
//            rideDriver.setDriverStatus(DriverStatus.INDRIVE);
//            rideDriver.setMyRides(myRide);
//            myRide.setMystatus(rideStatus.ACCEPTED);
//        }
//    }
}
