package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuGUI extends Application
{
	Button loginButton, registerButton, backButton;
	static Stage window;
	static Scene loginScene, registrationScene;
	String username, password;
	int userType;
	static Label[] regLabel = new Label[8];
	static TextField[] regTextField = new TextField[8];
	static Label[] regBlankIndicator = new Label[8];
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		window = primaryStage;
		Scene scene = displayLoginScene();
		window.setTitle("Appointment Booking System - Log In");
		window.setScene(scene);
		window.show();
	}
	
	public Scene displayLoginScene()
	{
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: #336699;");
		grid.setVgap(8);
		grid.setHgap(10);
				
		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);
		
		TextField usernameField = new TextField();
		usernameField.setPrefWidth(250);
		GridPane.setConstraints(usernameField, 1, 0);
		
		Label passwordLabel = new Label("Password:");
		GridPane.setConstraints(passwordLabel, 0, 1);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefWidth(250);
		GridPane.setConstraints(passwordField, 1, 1);
		
		final Label message = new Label("");
		message.setTextFill(Color.rgb(210, 39, 30));
		
		//Login button
		loginButton = new Button("Log In");
		loginButton.setOnAction(e ->
		{ 
			try
			{
				userType = Login.verifyLoginDetails(usernameField.getText(), passwordField.getText());
				if(userType == 1)
				{
					System.out.println("It worked! (Customer)");
					message.setText("");
					usernameField.clear();
					passwordField.clear();
				}
				else if(userType == 2)
				{
					window.setScene(BusinessOwnerMenu.displayMenu());					
					message.setText("");
					usernameField.clear();
					passwordField.clear();
					//window.close();
				}
				else
				{
					message.setText("Your username or password is incorrect!");
					passwordField.clear();
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
		GridPane.setConstraints(message, 1, 2);
		
		//Button 2
		registerButton = new Button("Sign Up");
		registerButton.setOnAction(e -> 
		{
			usernameField.clear();
			passwordField.clear();
			message.setText("");
			registrationScene = displayRegistration();
			window.setScene(registrationScene);
			window.setTitle("Appointment Booking System - Sign Up");
		});

		//Horizontally align buttons
		HBox hBtns = new HBox(10);
		hBtns.setAlignment(Pos.BOTTOM_RIGHT);
		hBtns.getChildren().addAll(loginButton, registerButton);
		GridPane.setConstraints(hBtns, 1, 3);
		
		grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, hBtns, message);
		loginScene = new Scene(grid, 400, 275);
		return loginScene;
	}
	
	public static Scene displayRegistration()
	{
		String[] labelName = {"Name:", "Username:", "Password:", "Confirm Password:", "Street:", "Suburb:", "Postcode:", "Phone Number:"};
				
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: #336699;");
		grid.setVgap(5);
		grid.setHgap(10);
		
		for(int i = 0; i < 8; i++)
		{
			regLabel[i] = new Label(labelName[i]);
			GridPane.setConstraints(regLabel[i], 0, i*3);
			
			regTextField[i] = new TextField();
			regTextField[i].setPrefWidth(250);
			GridPane.setConstraints(regTextField[i], 0, i*3+1);
			
			regBlankIndicator[i] = new Label("");
			regBlankIndicator[i].setTextFill(Color.rgb(210, 39, 30));
			GridPane.setConstraints(regBlankIndicator[i], 0, i*3+2);
		}
		
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e ->
		{
			String errors;
			Boolean blankField = false;
			
			for(int i = 0; i < 8; i++)
			{ 
				if(Registration.isNotBlank(regTextField[i].getText()) == false)
				{
					blankField = true;
					regBlankIndicator[i].setText("Field cannot be left blank!.");
				}
				else
				{
					regBlankIndicator[i].setText("");
				}
			}
			
			if(blankField == false)
			{
				errors = Menu.checker(regTextField[0].getText(), regTextField[1].getText(), regTextField[2].getText(), regTextField[3].getText(),
					regTextField[4].getText(), regTextField[5].getText(), regTextField[6].getText(), regTextField[7].getText());
				
				if(errors != null)
				{
					for(int i = 0; i < errors.length(); i++)
					{
						for(int j = 0; j < 8; j++)
						{
							//int num = Character.getNumericValue(errors.charAt(i));
							if(j == Character.getNumericValue(errors.charAt(i)))
							{
								int num = Character.getNumericValue(errors.charAt(i));
								System.out.println(num);
								if(j == 0 ||j == 1)
								{
									regBlankIndicator[1].setText(Menu.messageGenerator(errors.charAt(i)));
								}
								else
								{
									regBlankIndicator[num].setText(Menu.messageGenerator(errors.charAt(i)));
								}								
							}
						}
					}					
				}
				else
				{
					window.setScene(loginScene);
					window.setTitle("Appointment Booking System - Log In");
				}
			}			
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> 
		{
			window.setScene(loginScene);
			window.setTitle("Appointment Booking System - Log In");
		});		
		
		HBox hBtns = new HBox(10);
		hBtns.setAlignment(Pos.BOTTOM_RIGHT);
		hBtns.getChildren().addAll(confirmButton, cancelButton);
		GridPane.setConstraints(hBtns, 0, 24);
		
		for(int i = 0; i < 8; i++)
		{
			grid.getChildren().add(regTextField[i]);
			grid.getChildren().add(regBlankIndicator[i]);
			grid.getChildren().add(regLabel[i]);
		}
		grid.getChildren().addAll(hBtns);
		Scene registrationScene = new Scene(grid, 400, 650);
		
		return registrationScene;
	}
	
	public static String getFieldValue(String fieldName)
	{
		String value = "";
		
		for(int i = 0; i < 8; i++)
		{
			if(regLabel[i].getText().equals(fieldName))
			{
				value = regTextField[i].getText();
			}
		}
		
		return value;
	}
}
