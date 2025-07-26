import dao.UserDAO;
import models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("1. Register\n2. Login");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine();
            System.out.print("Enter Preferences: ");
            String pref = sc.nextLine();

            User user = new User(name, email, pass, pref);
            userDAO.registerUser(user);
        } else if (choice == 2) {
            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            User user = userDAO.loginUser(email, pass);
            if (user != null) {
                System.out.println("Login Successful! Welcome, " + user.getName());
            } else {
                System.out.println("Invalid credentials!");
            }
        }
    }
}
