package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
import javafx.scene.text.TextAlignment;
import utility.Utility;

public class BusinessOwnerMenu {
	
	static Scene mainScene;
	
	public static Scene displayMenu()
	{		
		MenuGUI.window.setTitle("ABS - Busniess Menu");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		Button b1 = new Button("Employee Management");
		b1.setOnAction(e -> MenuGUI.window.setScene(displayEmployeeManagement()));
		b1.setMaxWidth(Double.MAX_VALUE);
		Button b2 = new Button("View Booking Summaries");
		b2.setOnAction(e ->	MenuGUI.window.setScene(bookingsMenu()));
		b2.setMaxWidth(Double.MAX_VALUE);
		Button b3 = new Button("View New Bookings");
		b3.setMaxWidth(Double.MAX_VALUE);
		Button b4 = new Button("Add service");
		b4.setOnAction(e ->	MenuGUI.window.setScene(addServiceMenu()));
		b4.setMaxWidth(Double.MAX_VALUE);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(b1, b2, b3, b4);
		centerBox.setSpacing(10);
		centerBox.setPadding(new Insets(15, 15, 15, 15));
		centerBox.setAlignment(Pos.CENTER);
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().add(logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(centerBox);
		borderPane.setLeft(leftBox);
		
		mainScene = new Scene(borderPane, 400, 250);
		return mainScene;		
	}
	
	public static Scene displayEmployeeManagement()
	{
		MenuGUI.window.setTitle("ABS - Employee Management");
		BorderPane borderPane = new BorderPane();
		String[] buttonNames = {"Add employee", "List employees", "Show Schedule", "Add Schedule", "Remove Schedule", "Show Employee Availability", "Update Schedules"};
		Button[] buttons = new Button[7];		
		Label heading = new Label("--Business Name--");
		
		VBox centerBox = new VBox();
		centerBox.setSpacing(10);
		centerBox.setPadding(new Insets(15, 15, 15, 15));
		centerBox.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < 7; i++)
		{
			buttons[i] = new Button(buttonNames[i]);
			buttons[i].setMaxWidth(300);
			centerBox.getChildren().add(buttons[i]);
		}
		
		buttons[0].setOnAction(e -> MenuGUI.window.setScene(addEmployeeMenu()));
		buttons[1].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu()));
		buttons[6].setStyle("-fx-base: #03A9F4;");
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(mainScene));
		homeButton.setMaxWidth(100);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
				
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(centerBox);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 400, 350);
		return scene;
	}
	
	public static Scene addEmployeeMenu()
	{
		MenuGUI.window.setTitle("ABS - Add Employee");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
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
					MenuGUI.window.setScene(displayEmployeeManagement());
					MenuGUI.alertBox("ALERT!", "Employee has been successfully added!");
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
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> MenuGUI.window.setScene(displayEmployeeManagement()));
		GridPane.setConstraints(cancelButton, 2, 3);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(mainScene));
		homeButton.setMaxWidth(100);
				
		HBox hBtns = new HBox(10);
		hBtns.setAlignment(Pos.BOTTOM_RIGHT);
		hBtns.getChildren().addAll(confirmButton, cancelButton);
		GridPane.setConstraints(hBtns, 1, 3);
		
		grid.getChildren().addAll(firstNameLabel, lastNameLabel, firstNameField, lastNameField, message, hBtns);
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 410, 300);
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
		Label heading = new Label("--Business Name--");
		
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
		
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
		homeButton.setMaxWidth(100);
		
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
				
		borderPane.setTop(topBox);
		borderPane.setCenter(table);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 400, 300);
		return scene;
	}
	
	public static ObservableList<Booking> getBookings()
	{
		ObservableList<Booking> Booking = FXCollections.observableArrayList();
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
		
		StringTokenizer st = new StringTokenizer(BusinessManagement.selectedBusiness.getName(), " ");
		String file_name = "";
		while (st.hasMoreTokens())
		{
			file_name += st.nextToken();
		}

		file_name += "Bookings.txt";
		
		try 
		{	
			reader = new BufferedReader(new FileReader(file_name));
			
			while ((currentLine = reader.readLine()) != null)
			{
				dataValues = currentLine.split(deliminator);
				
				Booking.add(new Booking(dataValues[0], dataValues[1], dataValues[2], dataValues[3], dataValues[4], dataValues[5], dataValues[6], dataValues[7], dataValues[8]));	
			}
			
			reader.close();	
		}
		catch (IOException ioe1)
		{
			ioe1.printStackTrace();
		}
		
		return Booking;
	}
	
	public static Scene bookingsMenu()
	{
		MenuGUI.window.setTitle("ABS - View Booking Summaries");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		TableView<Booking> table;
		TableColumn<Booking, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setMinWidth(50);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		TableColumn<Booking, String> timeColumn = new TableColumn<>("Time");
		timeColumn.setMinWidth(50);
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		
		TableColumn<Booking, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(50);
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		TableColumn<Booking, String> cusomterColumn = new TableColumn<>("Customer");
		cusomterColumn.setMinWidth(50);
		cusomterColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

		TableColumn<Booking, String> employeeColumn = new TableColumn<>("Employee");
		employeeColumn.setMinWidth(50);
		employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
		
		TableColumn<Booking, String> serviceColumn = new TableColumn<>("Service");
		serviceColumn.setMinWidth(80);
		serviceColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
		
		table = new TableView<>();
		table.setItems(getBookings());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(dateColumn, timeColumn, statusColumn, cusomterColumn, employeeColumn, serviceColumn);
		
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e ->	MenuGUI.window.setScene(displayMenu()));
		homeButton.setMaxWidth(100);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(table);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 600, 300);
		return scene;
	}
	
	public static Scene addServiceMenu()
	{
		MenuGUI.window.setTitle("ABS - Add Service");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Busniess Name--");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label addServiceLabel = new Label("Service Name:");
		GridPane.setConstraints(addServiceLabel, 0, 0);
		TextField addServiceField = new TextField();
		GridPane.setConstraints(addServiceField, 1, 0);
		Label serviceDurationLabel = new Label("Duration (min):");
		GridPane.setConstraints(serviceDurationLabel, 0, 1);
		Label serviceDescriptionLabel = new Label("Description:");
		serviceDescriptionLabel.setAlignment(Pos.TOP_CENTER);
		GridPane.setConstraints(serviceDescriptionLabel, 0, 2);
		TextArea serviceDescriptionField = new TextArea();
		serviceDescriptionField.setPrefWidth(330);
		serviceDescriptionField.setWrapText(true);
		serviceDescriptionField.setPrefRowCount(3);
		GridPane.setConstraints(serviceDescriptionField, 1, 2);
		
		ChoiceBox<String> durationBox = new ChoiceBox<>();
		durationBox.getItems().addAll(
				"15",
				"30",
				"45",
				"60",
				"75",
				"90",
				"105",
				"120"
		);
		GridPane.setConstraints(durationBox, 1, 1);
		
		final Label message = new Label("");
		message.setTextFill(Color.rgb(210, 39, 30));
		GridPane.setConstraints(message, 1, 3);
		
		Button addButton = new Button("Add Service");
		addButton.setOnAction(e -> 
		{
			if(BusinessManagement.isNumericAndPositive(durationBox.getValue()) && !Utility.isBlank(durationBox.getValue()))
			{
				if(!Utility.isBlank(addServiceField.getText()) && !Utility.isBlank(serviceDescriptionField.getText()))
				{
					try
					{
						BusinessManagement.addService(addServiceField.getText(), durationBox.getValue(), serviceDescriptionField.getText());
						MenuGUI.alertBox("ALERT!", "Service has been successfully added!");
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
		
		grid.getChildren().addAll(addServiceLabel, addServiceField, serviceDurationLabel, durationBox, serviceDescriptionLabel, serviceDescriptionField, addButton, message);
		
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
		homeButton.setMaxWidth(100);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 555, 300);
		return scene;
	}
}