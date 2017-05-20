package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utility.Utility;

public class BusinessOwnerMenu {
	
	//static Stage window;
	static Scene mainScene;
	
	public static Scene displayMenu()
	{		
		BorderPane borderPane = new BorderPane();
		
		Label heading = new Label("Business Owner Menu");
		
		Button b1 = new Button("Employee Management");
		b1.setOnAction(e -> MenuGUI.window.setScene(displayMenu2()));
		b1.setMaxWidth(Double.MAX_VALUE);
		Button b2 = new Button("View Booking Summaries");
		b2.setMaxWidth(Double.MAX_VALUE);
		Button b3 = new Button("View New Bookings");
		b3.setMaxWidth(Double.MAX_VALUE);
		Button b4 = new Button("Add service");
		b4.setOnAction(e -> MenuGUI.window.setScene(addServiceMenu()));
		b4.setMaxWidth(Double.MAX_VALUE);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(MenuGUI.loginScene);
		});
		
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(b1, b2, b3, b4);
		centerBox.setSpacing(10);
		centerBox.setPadding(new Insets(15, 15, 15, 15));
		centerBox.setAlignment(Pos.CENTER);
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font: 24 arial; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(15, 15, 15, 15));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		HBox bottomBox = new HBox();
		bottomBox.setPadding(new Insets(10, 15, 10, 15));
		bottomBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.setStyle("-fx-background-color: grey;");
		bottomBox.getChildren().add(logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(centerBox);
		borderPane.setBottom(bottomBox);
		
		mainScene = new Scene(borderPane, 300, 300);
		return mainScene;		
	}
	
	public static Scene displayMenu2()
	{
		BorderPane borderPane = new BorderPane();
		String[] buttonNames = {"Add employee", "List employee", "Show Schedule", "Add Schedule", "Remove Schedule", "Show Employee Availability", "Update Schedules"};
		Button[] buttons = new Button[7];
		Label heading = new Label("Business Owner Menu");
		
		VBox centerBox = new VBox();
		centerBox.setSpacing(10);
		centerBox.setPadding(new Insets(15, 15, 15, 15));
		centerBox.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < 7; i++)
		{
			buttons[i] = new Button(buttonNames[i]);
			buttons[i].setMaxWidth(Double.MAX_VALUE);
			centerBox.getChildren().add(buttons[i]);
		}
		
		buttons[0].setOnAction(e -> MenuGUI.window.setScene(addEmployeeMenu()));
		buttons[1].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu()));
		buttons[6].setStyle("-fx-base: #03A9F4;");
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> MenuGUI.window.setScene(mainScene));
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(MenuGUI.loginScene);
		});
				
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font: 24 arial; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(15, 15, 15, 15));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		HBox bottomBox = new HBox();
		bottomBox.setPadding(new Insets(10, 15, 10, 15));
		bottomBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.setStyle("-fx-background-color: grey;");
		bottomBox.getChildren().addAll(backButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(centerBox);
		borderPane.setBottom(bottomBox);
		
		Scene scene = new Scene(borderPane);
		return scene;
	}
	
	public static Scene addEmployeeMenu()
	{
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Add Employee");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label firstNameLabel = new Label("First Name:");
		GridPane.setConstraints(firstNameLabel, 0, 0);
		Label lastNameLabel = new Label("Last Name:");
		GridPane.setConstraints(lastNameLabel, 0, 1);
		final Label message = new Label("");
		message.setTextFill(Color.rgb(210, 39, 30));
		GridPane.setConstraints(message, 1, 2);
		
		TextField firstNameField = new TextField();
		firstNameField.setPrefWidth(200);
		GridPane.setConstraints(firstNameField, 1, 0);
		TextField lastNameField = new TextField();
		lastNameField.setPrefWidth(200);
		GridPane.setConstraints(lastNameField, 1, 1);
		
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e -> 
		{
			try
			{
				if(EmployeeManagement.addEmployeeGUI(firstNameField.getText(), lastNameField.getText()))
				{
					MenuGUI.window.setScene(displayMenu2());
					message.setText("");
				}
				else
				{
					message.setText("First or last name are incorrect!");
				}
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		});
		GridPane.setConstraints(confirmButton, 1, 3);
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(displayMenu2());
			message.setText("");
		});
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(MenuGUI.loginScene);
			message.setText("");
		});
		
		grid.getChildren().addAll(firstNameLabel, lastNameLabel, firstNameField, lastNameField, message, confirmButton);
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font: 24 arial; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(15, 15, 15, 15));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		HBox bottomBox = new HBox();
		bottomBox.setPadding(new Insets(10, 15, 10, 15));
		bottomBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.setStyle("-fx-background-color: grey;");
		bottomBox.getChildren().addAll(backButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		borderPane.setBottom(bottomBox);
		
		Scene scene = new Scene(borderPane, 350, 300);
		return scene;
	}
	
	public static ObservableList<Employee> getEmployee()
	{
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
				
		try 
		{	
			reader = new BufferedReader(new FileReader("employeeList.txt"));
			
			while ((currentLine = reader.readLine()) != null)
			{
				dataValues = currentLine.split(deliminator);
				
				employees.add(new Employee(dataValues[0], dataValues[1], dataValues[2]));	
			}
			
			reader.close();	
		}
		catch (IOException ioe1)
		{
			ioe1.printStackTrace();
		}
		
		return employees;
	}
	
	public static Scene employeeListMenu()
	{
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Add Employee");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		TableView<Employee> table;
		TableColumn<Employee, String> employeeIdColumn = new TableColumn<>("Employee ID");
		employeeIdColumn.setMinWidth(50);
		employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
		
		TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
		firstNameColumn.setMinWidth(50);
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		
		TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
		lastNameColumn.setMinWidth(50);
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		table = new TableView<>();
		table.setItems(getEmployee());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(employeeIdColumn, firstNameColumn, lastNameColumn);
		
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(displayMenu2());
		});
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> 
		{
			MenuGUI.window.setScene(MenuGUI.loginScene);
		});
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font: 24 arial; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(15, 15, 15, 15));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		HBox bottomBox = new HBox();
		bottomBox.setPadding(new Insets(10, 15, 10, 15));
		bottomBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.setStyle("-fx-background-color: grey;");
		bottomBox.getChildren().addAll(backButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(table);
		borderPane.setBottom(bottomBox);
		
		Scene scene = new Scene(borderPane, 350, 300);
		return scene;
	}
	
	public static Scene addServiceMenu()
	{
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Add Service");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label addServiceLabel = new Label("Service Name:");
		GridPane.setConstraints(addServiceLabel, 0, 0);
		TextField addServiceField = new TextField();
		GridPane.setConstraints(addServiceField, 1, 0);
		Label serviceDurationLabel = new Label("Service Duration (minutes):");
		GridPane.setConstraints(serviceDurationLabel, 0, 1);
		TextField serviceDurationField = new TextField();
		GridPane.setConstraints(serviceDurationField, 1, 1);
		Label serviceDescriptionLabel = new Label("Service Description:");
		serviceDescriptionLabel.setAlignment(Pos.TOP_CENTER);
		GridPane.setConstraints(serviceDescriptionLabel, 0, 2);
		TextArea serviceDescriptionField = new TextArea();
		serviceDescriptionField.setPrefWidth(350);
		serviceDescriptionField.setWrapText(true);
		serviceDescriptionField.setPrefRowCount(3);
		GridPane.setConstraints(serviceDescriptionField, 1, 2);
		
		final Label message = new Label("");
		message.setTextFill(Color.rgb(210, 39, 30));
		GridPane.setConstraints(message, 1, 3);
		
		Button addButton = new Button("Add Service");
		addButton.setOnAction(e -> 
		{
			if(BusinessManagement.isNumericAndPositive(serviceDurationField.getText()) && !Utility.isBlank(serviceDurationField.getText()))
			{
				if(!Utility.isBlank(addServiceField.getText()) && !Utility.isBlank(serviceDescriptionField.getText()))
				{
					try
					{
						BusinessManagement.addService(addServiceField.getText(), serviceDurationField.getText(), serviceDescriptionField.getText());
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					message.setText("One or more of the above fields are either blank or incorrect!");
				}
			}
			else
			{
				message.setText("One or more of the above fields are either blank or incorrect!");
			}
		});
		GridPane.setConstraints(addButton, 1, 4);
		
		grid.getChildren().addAll(addServiceLabel, addServiceField, serviceDurationLabel, serviceDurationField, serviceDescriptionLabel, serviceDescriptionField, addButton, message);
		
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font: 24 arial; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(15, 15, 15, 15));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		HBox bottomBox = new HBox();
		bottomBox.setPadding(new Insets(10, 15, 10, 15));
		bottomBox.setAlignment(Pos.CENTER_RIGHT);
		bottomBox.setStyle("-fx-background-color: grey;");
		bottomBox.getChildren().addAll(backButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		borderPane.setBottom(bottomBox);
		
		Scene scene = new Scene(borderPane, 550, 350);
		return scene;
	}
}