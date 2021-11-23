import java.util.ArrayList;
import java.util.Scanner;

enum DriverStatus {
    ACTIVE, REJECTED, SUSPENDED, PENDING, WAITING, OFFLINE, INDRIVE
}

public class Driver extends Person {

    private int driverID;
    private String nationalId;
    private String drivingLicense;
    private DriverStatus state = DriverStatus.PENDING;
    private ArrayList<Ride> myRides;
    private Rating myRate;
    private DriverAreas myAreas;

    public ArrayList<Ride> getMyRides() {
        return myRides;
    }

    public void setMyRides(Ride newRide) {
        myRides.add(newRide);
    }

    public ArrayList<String> getMyAreas() {
        return myAreas.getAllArea();
    }

    public void setMyAreas(String area) {
        myAreas.addFavAreas(area);
    }

    public void register() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your information: ");
        System.out.println("Username: ");
        userName = input.nextLine();
        System.out.println("Password: ");
        password = input.nextLine();
        System.out.println("Email (optional): press enter to skip it if you want");
        email = input.nextLine();
        System.out.println("Phone Number: ");
        phoneNumber = input.nextLine();
        state = DriverStatus.ACTIVE;
        Admin.addActiveDriver(this);
    }

    Driver(String userName, String password,
           String email, String phoneNumber,
           String nationalId, String drivingLicense, int driverID) {

        super(userName, password, email, phoneNumber);
        this.nationalId = nationalId;
        this.drivingLicense = drivingLicense;
        this.driverID = driverID;
    }

    public Driver() {
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setDriverStatus(DriverStatus state) {
        this.state = state;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public int getDriverID() {
        return driverID;
    }

    public DriverStatus getState() {
        return state;
    }

    public Double rideOffer() {
        System.out.print("Please enter your offer to this ride");
        Scanner in = new Scanner(System.in);
        double price = in.nextDouble();
        return price;
    }

    public void setMyRate(User user) {
        myRate.setRating(user);
    }

}
