package com.example.loginpage;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HelloApplication extends Application {

    // Class-level label for displaying invalid messages
    private Label invalidMessageLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        // Load Banner Image
        Image bannerImage = new Image("file:C:/banner.jpg"); // Use "file:" prefix for local files
        ImageView bannerImageView = new ImageView(bannerImage);
        bannerImageView.setFitHeight(100);
        bannerImageView.setFitWidth(400); // Adjust width to match layout

        // Login Form Components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPrefWidth(250); // Resize the username input field

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(250); // Resize the password input field

        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        // Configure invalidMessageLabel
        invalidMessageLabel.setStyle("-fx-text-fill: red;"); // Red text for invalid login message
        invalidMessageLabel.setVisible(false); // Initially hidden

        // Layout for the form
        VBox loginForm = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, exitButton, invalidMessageLabel);
        loginForm.setAlignment(Pos.CENTER);

        // Combine Banner and Form
        VBox mainLayout = new VBox(10, bannerImageView, loginForm);
        mainLayout.setAlignment(Pos.CENTER);

        // Scene 1: Login Scene
        Scene loginScene = new Scene(mainLayout, 400, 400); // Increased height to fit the image and form

        // Scene 2: Welcome Scene
        Label welcomeLabel = new Label();
        VBox welcomeLayout = new VBox(20, welcomeLabel);
        welcomeLayout.setAlignment(Pos.CENTER);
        Scene welcomeScene = new Scene(welcomeLayout, 400, 300);

        // Login Button Action
        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (validateLogin(username, password)) {
                welcomeLabel.setText("Welcome, " + username + "!");
                primaryStage.setScene(welcomeScene); // Switch to Welcome Scene
                invalidMessageLabel.setVisible(false); // Hide invalid message
            } else {
                invalidMessageLabel.setText("Incorrect username or password.");
                invalidMessageLabel.setVisible(true); // Show invalid message
            }
        });

        // Exit Button Action
        exitButton.setOnAction(event -> {
            primaryStage.close(); // Closes the application
        });

        // Set up the primary stage
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    // Method to validate login credentials using BufferedReader and try-with-resources
    private boolean validateLogin(String username, String password) {
        // Using try-with-resources to ensure BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                // If a line contains exactly two parts and matches the username/password
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true; // Valid login
                }
            }
        } catch (IOException e) {
            invalidMessageLabel.setText("Error reading the file. Please try again.");
            invalidMessageLabel.setVisible(true); // Show file error message
        }
        return false; // Invalid login if no match found
    }

    public static void main(String[] args) {
        launch(args);
    }
}
