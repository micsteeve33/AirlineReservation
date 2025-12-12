package AirlineRes;

import java.sql.*;
import java.util.Scanner;

public class AirlineReservation {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airline_reservation", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void registerUser(Scanner sc) {
        try {
            Connection con = getConnection();
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            String sql = "INSERT INTO users(name,email,password) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            int status = ps.executeUpdate();
            if (status > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Error registering user.");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewFlights() {
        try {
            Connection con = getConnection();
            String sql = "SELECT * FROM flights";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\nAvailable Flights:");
            System.out.println("ID | Flight Number | Origin | Destination | Seats");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("flight_number") + " | " +
                                   rs.getString("origin") + " | " +
                                   rs.getString("destination") + " | " +
                                   rs.getInt("seats"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Airline Reservation System ---");
            System.out.println("1. Register User");
            System.out.println("2. View Flights");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser(sc);
                    break;
                case 2:
                    viewFlights();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

