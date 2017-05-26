package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MenuGUI extends Application
{
	Button loginButton, registerButton, backButton;
	static Stage window;
	static Scene loginScene, registrationScene;
	String username, password;
	public static int userType;
	static Label[] regLabel = new Label[8];
	static TextField[] regTextField = new TextField[8];
	static Label[] regBlankIndicator = new Label[8];
	public UserManagement userManagement;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//Creates window
		window = primaryStage;
		//Creates the scene which hosts the 'Log in' scene
		Scene scene = displayBusinessSelection();
		window.setTitle("Business Selection");
		//Sets and displays the 'Log in' scene
		window.setScene(scene);
		window.show();
		
	}
	
	public Scene displayBusinessSelection() {

		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Business Selection");
		
		TableView<Business> table;
		TableColumn<Business, String> businessColumn = new TableColumn<>("Business Name");
		businessColumn.setMinWidth(125);
		businessColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Business, String> leftColumn = new TableColumn<>("");
		leftColumn.setMinWidth(50);
		leftColumn.setCellValueFactory(new PropertyValueFactory<>("BLANK"));
		
		TableColumn<Business, String> rightColumn = new TableColumn<>("");
		rightColumn.setMinWidth(50);
		rightColumn.setCellValueFactory(new PropertyValueFactory<>("BLANK"));

		
		table = new TableView<>();
		table.setItems(FXCollections.observableArrayList(BusinessManagement.retrieveBusiness()));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(leftColumn, businessColumn, rightColumn);
		
		businessColumn.setCellFactory(
				new Callback<TableColumn<Business, String>, TableCell<Business, String>>() {

	                @Override
	                public TableCell<Business, String> call(TableColumn<Business, String> idButton) {
	                	return new TableCell<Business,String>() {
	               		 
	                     	String business = getItem();

	                		Button cellButton = new Button(business);
	                     		                     	
	                     	@Override
	                		protected void updateItem(String item, boolean value) {
	                     		super.updateItem(item, value);
	                     		cellButton.setOnAction(e -> {
	                     			String business = getItem();
	                     			
	                     			try {
										userManagement = new UserManagement(business);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
	                     			
	                     			window.setScene(displayLoginScene());
	                     			
	                     			});
	                     		cellButton.setText(getItem());
	                     		cellButton.setMaxSize(Double.MAX_VALUE, computeMaxHeight(0));
	                     		
	                     		if (getItem() != null) {

		                     		setGraphic(cellButton);
		                     		
	                     		}
	                     		
	                     		
	                     	}
	                		
	                	};
	                }
	                
				});		

		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		

		borderPane.setTop(topBox);
		borderPane.setCenter(table);
		
		Scene scene = new Scene(borderPane, 400, 300);
		return scene;
		
	}
	
	public Scene displayLoginScene()
	{

		BorderPane borderPane = new BorderPane();
		window.setTitle("Login");
		Label heading = new Label(BusinessManagement.selectedBusiness.getName());
		
		//Create a grid to hold the necessary items in a clean layout
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
				window.setScene(BusinessOwnerMenu.displayMenu());
				new UserManagement("Suggar Haircut");
				MenuGUI.userType = 2;
				//Taking the data from the username and password textfields and validating them
				/*userType = Login.verifyLoginDetails(usernameField.getText(), passwordField.getText());
				if(userType == 1)
				{
					//If customer details are found
					window.setScene(CustomerMenu.displayMenu());
					message.setText("");
					usernameField.clear();
					passwordField.clear();
				}
				else if(userType == 2)
				{
					//If Business Owner details are found
					window.setScene(BusinessOwnerMenu.displayMenu());					
					message.setText("");
					usernameField.clear();
					passwordField.clear();
				}
				else
				{
					//If no users are found
					message.setText("Your username or password is incorrect!");
					passwordField.clear();
				}*/
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
			//Setting scene for registration
			registrationScene = displayRegistration();
			//Changes scene to 'Sign Up' scene
			window.setScene(registrationScene);
		});

		//Horizontally align buttons
		HBox hBtns = new HBox(10);
		hBtns.setAlignment(Pos.BOTTOM_RIGHT);
		hBtns.getChildren().addAll(loginButton, registerButton);
		GridPane.setConstraints(hBtns, 1, 3);
		
		//Add all items/nodes to grid pane
		grid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, hBtns, message);
		//Set grid pane to scene

		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		
		loginScene = new Scene(borderPane, 400, 275);
		return loginScene;
	}
	
	public static Scene displayRegistration()
	{
		window.setTitle("ABS - Sign Up");
		String[] labelName = {"Username:", "Password:", "Confirm Password:", "Street:", "Suburb:", "Postcode:", "Phone Number:"};
		String[] textFieldValues = {"Username", "Password", "", "Street", "Suburb", "Postcode", "Phone Number"};
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: #336699;");
		grid.setVgap(5);
		grid.setHgap(10);
		
		//Creating the labels, textfields and error messages and initializing them
		for(int i = 0; i < 7; i++)
		{
			regLabel[i] = new Label(labelName[i]);
			GridPane.setConstraints(regLabel[i], 0, i*3);
			
			if (i == 1 || i == 2) {
				
				regTextField[i] = new PasswordField();
				
			} else {
				
				regTextField[i] = new TextField();
				
			}
			regTextField[i].setPromptText(textFieldValues[i]);
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
			
			//Resets fields and messages 
			for(int i = 0; i < 7; i++)
			{ 
				regBlankIndicator[i].setText("");
				regTextField[i].setStyle(null);
			}
			
			//Validating the inputs of the fields
			errors = Menu.checker(regTextField[0].getText(), regTextField[1].getText(), regTextField[2].getText(), regTextField[3].getText(),
				regTextField[4].getText(), regTextField[5].getText(), regTextField[6].getText());
			
			//If errors are detected
			if(errors != null)
			{
				//Loop for the number of errors
				for(int i = 0; i < errors.length(); i++)
				{
					for(int j = 0; j < 7; j++)
					{
						//if the field matches the associated error
						if(j == Character.getNumericValue(errors.charAt(i)))
						{
							//Display respective message and highlight textfield
							int num = Character.getNumericValue(errors.charAt(i));
							regBlankIndicator[num].setText(Menu.messageGenerator(errors.charAt(i)));
							regTextField[j].setStyle("-fx-border-color: red;");
						}
					}
				}					
			}
			else
			{
				//If no errors
				window.setScene(loginScene);
			}					
		});
		
		//Button to go back to previous window
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> window.setScene(loginScene));		
		
		HBox hBtns = new HBox(10);
		hBtns.setAlignment(Pos.BOTTOM_RIGHT);
		hBtns.getChildren().addAll(confirmButton, cancelButton);
		GridPane.setConstraints(hBtns, 0, 24);
		
		for(int i = 0; i < 7; i++)
		{
			grid.getChildren().add(regTextField[i]);
			grid.getChildren().add(regBlankIndicator[i]);
			grid.getChildren().add(regLabel[i]);
		}
		grid.getChildren().addAll(hBtns);
		Scene registrationScene = new Scene(grid, 350, 600);
		
		return registrationScene;
	}
	
	//Alert box for displaying messages
	public static void alertBox(String title, String message)
	{
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox();
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}