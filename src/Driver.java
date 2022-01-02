import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;



enum DriverStatus {
    ACTIVE, REJECTED, SUSPENDED, PENDING, WAITING, OFFLINE, INDRIVE
}

public class Driver extends Person {

    private String nationalId;
    private String drivingLicense;
    private DriverStatus status = DriverStatus.PENDING;
//    private ArrayList<Ride> myRides = new ArrayList<>();
   private Rating myRate = new Rating();
//    private DriverAreas myAreas = new DriverAreas();


    public void setNewArea(String myNewArea) {
       // myAreas.addFavAreas(myNewArea);

    }

    public void register(String userName, String password, String email, String phoneNumber, String nationalId, String drivingLicense) {
        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\SW.db";
//        Scanner input = new Scanner(System.in);
//        System.out.println("Please enter your information: ");
//        System.out.println("Username: ");
//        this.userName = userName;
//        this.password = password;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.nationalId = nationalId;
//        this.drivingLicense = drivingLicense;
//        System.out.println("Password: ");
//        password = input.nextLine();
//        System.out.println("Email (optional): press enter to skip it if you want");
//        email = input.nextLine();
//        System.out.println("Phone Number: ");
//        phoneNumber = input.nextLine();
//        System.out.println("Nationa ID: ");
//        nationalId = input.nextLine();
//        System.out.println("Driving License: ");
//        drivingLicense = input.nextLine();
        status = DriverStatus.SUSPENDED;

        String sql = "insert into driver (username, password, email, phone, nationalId, drivingLicense, status) values (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ins = conn.prepareStatement(sql);
            ins.setString(1, userName);
            ins.setString(2, password);
            ins.setString(3, email);
            ins.setString(4, phoneNumber);
            ins.setString(5, nationalId);
            ins.setString(6, drivingLicense);
            ins.setString(7, "SUSPENDED");
            ins.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Driver has been registered successfully");
    }

    public Driver login(String Name, String pass) {
        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\SW.db";
        /*System.out.println("Please enter your username and password");
        Scanner input = new Scanner(System.in);
        String Name = input.nextLine().trim();
        String pass = input.nextLine().trim();*/

        try ( Connection conn = DriverManager.getConnection(url)) {
            String query = "select count(*),* FROM driver WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, Name);
            pst.setString(2, pass);

            try ( ResultSet rs = pst.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    boolean found = rs.getBoolean(1); // "found" column
                    if (found) {
//                        Driver d = new Driver();
                        this.setUserName(rs.getString("username"));
                        this.setPassword(rs.getString("password"));
                        this.setPhoneNumber(rs.getString("phone"));
                        this.setPEmail(rs.getString("email"));
                        this.setNationalId(rs.getString("nationalId"));
                        this.setDrivingLicense(rs.getString("drivingLicense"));
                        this.setDriverStatus(DriverStatus.valueOf(rs.getString("status")));
//                        DriverStatus s = DriverStatus.valueOf(status);
//                        d.setDriverStatus(s);
//                        String areasQuery = "select area from favoriteAreas where driver = " + this.getUserName();
//                        Statement stmt = conn.createStatement();
//                        ResultSet areas = stmt.executeQuery(areasQuery);
//                        while (areas.next()) {
//                            d.myAreas.addFavArea(areas.getString("area"));
//                        }
                        if (this.getStatus().equals(DriverStatus.PENDING) || this.getStatus().equals(DriverStatus.SUSPENDED)) {
                            System.out.println("Please wait until the verification of your account finish");
                            return null;
                        } else {
                            System.out.println(this.getStatus());
                            System.out.println("Logged in successfully");
                            return this;
                        }

                    } else {
                        System.out.println("Wrong password or username");
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public double getAvgRate() {
        return myRate.calcAvgRate(this);
    }

    public Driver() {
    }
    public void setDriverStatus(DriverStatus state) {
        this.status = state;
    }
    public DriverStatus getStatus() {
        return status;
    }
    public Double rideOffer(double price) {
        return price;
    }
    public void setRidePrice(){

    }


//    public void getMyRate() {
//        myRate.printRatings(this);
//    }
//
//    public void setMyRate(User user) {
//        myRate.setRating(user, this);
//    }
//
//    public void setMyRides(Ride newRide) {
//        myRides.add(newRide);
//    }
//
//    public ArrayList<String> getMyAreas() {
//        return myAreas.getAllArea();
//    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }
    /*  static Driver loginDriver() {
        ArrayList<Driver> allDrivers = Admin.getAllDrivers();
        //allDrivers.add(Admin.getAllDrivers());
        System.out.println("Please enter your username and password");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        String pass = input.nextLine();
        for (Driver driver : allDrivers) {
            if (driver.getUserName().equals(name)) {
                if (driver.getPassword().equals(pass) && (driver.status.equals(DriverStatus.ACTIVE)
                        || driver.status.equals(DriverStatus.INDRIVE)
                        || driver.status.equals(DriverStatus.WAITING))) {
                    System.out.println("Logged in successfully");
                    return driver;
                } else {
                    System.out.println("Wrong password");
                    return null;
                }
            }
        }
        System.out.println("Please wait until the verification of your account finish or wrong input");
        return null;
    }*/


    /* Driver(String userName, String password,
            String email, String phoneNumber,
            String nationalId, String drivingLicense, int driverID) {

        super(userName, password, email, phoneNumber);
        this.nationalId = nationalId;
        this.drivingLicense = drivingLicense;
    }*/
//    public ArrayList<Ride> getMyRides() {
//        return myRides;
//    }

}
