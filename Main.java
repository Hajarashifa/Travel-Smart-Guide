import dao.UserDAO;
import models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your password: ");
        String pass = sc.nextLine();

        User user = userDAO.loginUser(email, pass);

        if (user != null) {
            System.out.println("\nLogin Successful! Welcome to the Travel Smart Guide App, " + user.getName() + "!");
            // You can add more app features here later
        } else {
            System.out.println("\nUser does not exist. Please register first.\n");

            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Preferences: ");
            String pref = sc.nextLine();

            User newUser = new User(name, email, pass, pref);
            userDAO.registerUser(newUser);
            System.out.println("Welcome to the Travel Smart Guide App, " + name + "!");
        }
    }
}
