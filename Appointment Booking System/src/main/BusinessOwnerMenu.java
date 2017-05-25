package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;
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
		Button b2 = new Button("Booking Management");
		b2.setOnAction(e ->	MenuGUI.window.setScene(displayBookingManagement()));
		b2.setMaxWidth(Double.MAX_VALUE);
		Button b3 = new Button("Add service");
		b3.setOnAction(e ->	MenuGUI.window.setScene(addServiceMenu()));
		b3.setMaxWidth(Double.MAX_VALUE);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(b1, b2, b3);
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
		Label heading = new Label("Employee Management");
		
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
		buttons[1].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(false,-1)));
		buttons[2].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,0)));
		buttons[3].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,1)));
		buttons[4].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,2)));
		buttons[5].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,3)));
		buttons[6].setStyle("-fx-base: #03A9F4;");
		buttons[6].setOnAction(e -> ScheduleManagement2.updateAllSchedules());
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
	
	public static Scene displayBookingManagement()
	{
		MenuGUI.window.setTitle("ABS - Booking Management");
		BorderPane borderPane = new BorderPane();
		String[] buttonNames = {"View Booking Summaries", "View New Bookings", "Add Booking"};
		Button[] buttons = new Button[3];		
		Label heading = new Label("--Business Name--");
		
		VBox centerBox = new VBox();
		centerBox.setSpacing(10);
		centerBox.setPadding(new Insets(15, 15, 15, 15));
		centerBox.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < 3; i++)
		{
			buttons[i] = new Button(buttonNames[i]);
			buttons[i].setMaxWidth(300);
			centerBox.getChildren().add(buttons[i]);
		}

		buttons[0].setOnAction(e -> MenuGUI.window.setScene(bookingsMenu(false)));
		buttons[1].setOnAction(e -> MenuGUI.window.setScene(bookingsMenu(true)));
		buttons[2].setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,4)));
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
	
	//if remove is true, it will add a button allow for removing of schedule items
	public static Scene showScheduleMenu(String employee, boolean remove) {
				
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		TableView<Schedule> table;
		TableColumn<Schedule, String> dayColumn = new TableColumn<>("Day");
		dayColumn.setMinWidth(50);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		
		TableColumn<Schedule, String> startTimeColumn = new TableColumn<>("Start Time");
		startTimeColumn.setMinWidth(50);
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		TableColumn<Schedule, String> endTimeColumn = new TableColumn<>("End Time");
		endTimeColumn.setMinWidth(50);
		endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
		
		TableColumn<Schedule, String> actionColumn = new TableColumn<>("");
		actionColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
		
		table = new TableView<>();
		table.setItems(getSchedule(employee));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		if (remove == true) {
			actionColumn.setCellFactory(
				new Callback<TableColumn<Schedule, String>, TableCell<Schedule, String>>() {

	                @Override
	                public TableCell<Schedule, String> call(TableColumn<Schedule, String> idButton) {
	                	return new TableCell<Schedule,String>() {
	               		 
	                		Button cellButton = new Button("Remove");
	                     		                     	
	                     	@Override
	                		protected void updateItem(String item, boolean value) {
	                     		super.updateItem(item, value);
	                     		cellButton.setOnAction(e -> {
	                     			String employeeID = getItem();
		                     				                     		
		                     		ScheduleManagement2.employeeRecuringScheduleRemoveGUI(Integer.parseInt(getItem()), employee);
		                     		
		                     		MenuGUI.window.setScene(showScheduleMenu(employee,true));
	                     			
	                     			});
	                     		
	                     		if (getItem() != null) {
		                     		
		                     		setGraphic(cellButton);
	                     			
	                     		}
	                     		
	                     	}
	                		
	                	};
	                }
	                
				});

			table.getColumns().addAll(actionColumn, dayColumn, startTimeColumn, endTimeColumn);
			
		} else {

			table.getColumns().addAll(dayColumn, startTimeColumn, endTimeColumn);
			
		}
		
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
		homeButton.setMaxWidth(100);
		
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,2)));
		backButton.setMaxWidth(100);
		
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
		leftBox.getChildren().addAll(homeButton, backButton, logoutButton);
				
		borderPane.setTop(topBox);
		borderPane.setCenter(table);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 400, 300);
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
	
	public static Scene addScheduleMenu(String employee) {
		
		MenuGUI.window.setTitle("ABS - Add Employee");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		ObservableList<String> days = FXCollections.observableArrayList();
		for (int i = 0; i < 7; i++) {
			
			days.add(weekDays[i]);
			
		}
		
		ObservableList<String> hours = FXCollections.observableArrayList();
		for (int i = 1; i < 24; i++) {
			
			hours.add("" + i);
			
		}
		
		ObservableList<String> minutes = FXCollections.observableArrayList();
		for (int i = 0; i < 60; i++) {
			
			if (i < 10) {
				minutes.add("0" + i);
			} else {
				minutes.add("" + i);
			}
			
		}

		Label dayLabel = new Label("Day:");
		GridPane.setConstraints(dayLabel, 0, 0);
		Label startHourLabel = new Label("Start Hour:");
		GridPane.setConstraints(startHourLabel, 0, 1);
		Label startMinuteLabel = new Label("Start Minute:");
		GridPane.setConstraints(startMinuteLabel, 0, 2);
		Label endHourLabel = new Label("End Hour:");
		GridPane.setConstraints(endHourLabel, 0, 3);
		Label endMinuteLabel = new Label("End Minute:");
		GridPane.setConstraints(endMinuteLabel, 0, 4);
		
		ComboBox dayBox = new ComboBox(days);
		ComboBox startHourBox = new ComboBox(hours);
		ComboBox startMinuteBox = new ComboBox(minutes);
		ComboBox endHourBox = new ComboBox(hours);
		ComboBox endMinuteBox = new ComboBox(minutes);
		
		dayBox.setPrefWidth(200);
		GridPane.setConstraints(dayBox, 1, 0);
		startHourBox.setPrefWidth(200);
		GridPane.setConstraints(startHourBox, 1, 1);
		startMinuteBox.setPrefWidth(200);
		GridPane.setConstraints(startMinuteBox, 1, 2);
		endHourBox.setPrefWidth(200);
		GridPane.setConstraints(endHourBox, 1, 3);
		endMinuteBox.setPrefWidth(200);
		GridPane.setConstraints(endMinuteBox, 1, 4);
		final Label message = new Label("");
		message.setTextFill(Color.rgb(210, 39, 30));
		GridPane.setConstraints(message, 0, 5);
		
		
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(e -> 
		{
			int[] daytime = new int[3];
			
			for (int i = 0; i < 7; i++) {
				
				if ((String) dayBox.getValue() == weekDays[i]) {
					
					daytime[0] = i + 1;
					
				}
				
			}
			
			daytime[1] = Integer.parseInt((String) startHourBox.getValue()) * 100 + Integer.parseInt((String) startMinuteBox.getValue());
			daytime[2] = Integer.parseInt((String) endHourBox.getValue()) * 100 + Integer.parseInt((String) endMinuteBox.getValue());
			
			if(ScheduleManagement2.employeeRecuringScheduleAdd(employee, daytime))
			{
				MenuGUI.window.setScene(displayEmployeeManagement());
				MenuGUI.alertBox("ALERT!", "Schedule has been successfully added");
				message.setText("");
			}
			else
			{
				message.setText("Error. Please try again.");
			}
		});
		GridPane.setConstraints(confirmButton, 1, 5);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(mainScene));
		homeButton.setMaxWidth(100);
		Button backButton = new Button("BACK");
		backButton.setOnAction(e -> MenuGUI.window.setScene(employeeListMenu(true,0)));
		backButton.setMaxWidth(100);
				
		
		grid.getChildren().addAll(dayLabel, dayBox, startHourLabel, startHourBox, startMinuteLabel, startMinuteBox, endHourLabel, endHourBox, endMinuteLabel, endMinuteBox, confirmButton);
		
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
		leftBox.getChildren().addAll(homeButton, backButton, logoutButton);
		
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
	
	public static ObservableList<Bookings> getBookings(String employeeID, boolean freeOnly) {
		
		ObservableList<Bookings> bookings = FXCollections.observableArrayList();
			
		List<String> booking;
		
		if (employeeID == null) {

			booking = ScheduleManagement.returnGeneralAvailability();
			
		} else {
		
			booking = ScheduleManagement.scheduleGet(employeeID);
			
		}
		
		if (freeOnly == true) {
			
			booking = ScheduleManagement.scheduleRemoveNonFree(booking);
			
		}
			
		String[] dataValues;
			
		String deliminator = "\\|";
			
		for (int i = 1; i < booking.size(); i++) {
				
			dataValues = booking.get(i).split(deliminator);
				
			bookings.add(new Bookings(booking.get(i), dataValues[0], dataValues[1], dataValues[2], dataValues[3], dataValues[4]));
				
		}
			
		return bookings;
		
	}
	
	public static ObservableList<Schedule> getSchedule(String employeeID)
	{
		ObservableList<Schedule> scheduled = FXCollections.observableArrayList();
		
		List<String> schedule = ScheduleManagement2.getEmployeeSchedule(employeeID);
		
		String[] dataValues;
		
		String deliminator = "\\|";
		
		String[] weekDays = {"Ix", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		for (int i = 1; i < schedule.size(); i++) {
			
			dataValues = schedule.get(i).split(deliminator);
			
			scheduled.add(new Schedule("" + i, weekDays[Integer.parseInt(dataValues[0])], dataValues[1], dataValues[2]));
			
		}
		
		return scheduled;
	}
	
	public static Scene showCustomers(String data, boolean selectable) {
		
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		TableView<Customer> table;
		TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(50);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Customer, String> streetColumn = new TableColumn<>("Street");
		streetColumn.setMinWidth(50);
		streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
		
		TableColumn<Customer, String> suburbColumn = new TableColumn<>("Suburb");
		suburbColumn.setMinWidth(50);
		suburbColumn.setCellValueFactory(new PropertyValueFactory<>("suburb"));
		
		TableColumn<Customer, String> postcodeColumn = new TableColumn<>("Postcode");
		postcodeColumn.setMinWidth(50);
		postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));
		
		TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
		phoneColumn.setMinWidth(50);
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		
		if (selectable == true) {
			
			nameColumn.setCellFactory(
				new Callback<TableColumn<Customer, String>, TableCell<Customer, String>>() {

	                @Override
	                public TableCell<Customer, String> call(TableColumn<Customer, String> idButton) {
	                	return new TableCell<Customer,String>() {
	               		 
	                     	String name = getItem();

	                		Button cellButton = new Button(name);
	                     		                     	
	                     	@Override
	                		protected void updateItem(String item, boolean value) {
	                     		super.updateItem(item, value);
	                     		cellButton.setOnAction(e -> {
	                     			String name = getItem();
	                     			System.out.println(name);
	                     			
	                     			String[] dataValues;
	                    			
	                     			String deliminator = "\\|";
	                     				
	                     			dataValues = data.split(deliminator);
	                     			
	                     			System.out.println(Integer.parseInt(dataValues[3]));
	                     			
	                     			System.out.println((Integer.parseInt(dataValues[3])) % 100);
	                     			System.out.println(Integer.parseInt(dataValues[3]) - Integer.parseInt(dataValues[3]) % 100);
	                     			System.out.println((Integer.parseInt(dataValues[3]) - Integer.parseInt(dataValues[3]) % 100) / 100);
	                     			
	                     			System.out.println(dataValues[0] + "," + dataValues[1] + "," + dataValues[2] + "," + (Integer.parseInt(dataValues[3]) - Integer.parseInt(dataValues[3]) % 100) / 100 + ","
	                     					+ dataValues[4] + "," + dataValues[5] + "," + dataValues[6]);
                     				
                     				try {
										BusinessManagement.addBooking(Integer.parseInt(dataValues[0]), Integer.parseInt(dataValues[1]), 
												Integer.parseInt(dataValues[2]), 
												(Integer.parseInt(dataValues[3]) - Integer.parseInt(dataValues[3]) % 100) / 100, 
												Integer.parseInt(dataValues[3]) % 100, name, dataValues[5], dataValues[6]);
									} catch (IOException e1) {
										System.out.println("error");
										e1.printStackTrace();
									}
	                     			
	                     			
	                     			});
	                     		cellButton.setText(getItem());
	                     		setGraphic(cellButton);
	                     		
	                     	}
	                		
	                	};
	                }
	                
				});
			
		}
		
		table = new TableView<>();
		table.setItems(getCustomers());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(nameColumn, streetColumn, suburbColumn, postcodeColumn, phoneColumn);
		
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
	
	public static ObservableList<Customer> getCustomers() {
		
		ObservableList<Customer> customers = FXCollections.observableArrayList();
			
		List<String> customerList = CustomerManagement.listCustomers();
		
		String[] dataValues;
			
		String deliminator = "\\|";
			
		for (int i = 1; i < customerList.size(); i++) {
				
			dataValues = customerList.get(i).split(deliminator);
				
			customers.add(new Customer(dataValues[0], dataValues[2], dataValues[3], dataValues[4], dataValues[5]));
				
		}
			
		return customers;
		
	}
	
	public static Scene showBookingMenu(String employeeID, boolean freeOnly, boolean makeBooking) {
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("--Business Name--");
		
		TableView<Bookings> table;
		
		TableColumn<Bookings, String> actionColumn = new TableColumn<>("");
		actionColumn.setMinWidth(50);
		actionColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
				
		TableColumn<Bookings, String> yearColumn = new TableColumn<>("Year");
		yearColumn.setMinWidth(50);
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		TableColumn<Bookings, String> monthColumn = new TableColumn<>("Month");
		monthColumn.setMinWidth(50);
		monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		
		TableColumn<Bookings, String> dayColumn = new TableColumn<>("Day");
		dayColumn.setMinWidth(50);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		
		TableColumn<Bookings, String> startTimeColumn = new TableColumn<>("Start Time");
		startTimeColumn.setMinWidth(50);
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		TableColumn<Bookings, String> valueColumn = new TableColumn<>("Customer");
		valueColumn.setMinWidth(50);
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		
		table = new TableView<>();
		table.setItems(getBookings(employeeID, freeOnly || makeBooking));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		if (makeBooking == true) {
			actionColumn.setCellFactory(
				new Callback<TableColumn<Bookings, String>, TableCell<Bookings, String>>() {

	                @Override
	                public TableCell<Bookings, String> call(TableColumn<Bookings, String> idButton) {
	                	return new TableCell<Bookings,String>() {
	               		 
	                		Button cellButton = new Button("Book");
	                     		                     	
	                     	@Override
	                		protected void updateItem(String item, boolean value) {
	                     		super.updateItem(item, value);
	                     		cellButton.setOnAction(e -> {
	                     			String index = getItem();
	                     			
	                     			System.out.println(index);
	                     			
	                     			index = index + "|" + employeeID;
	                     				                     			
	                     			MenuGUI.window.setScene(CustomerMenu.serviceListMenu(true, index));
	                     			
	                     			});
	                     		
	                     		if (getItem() != null) {
		                     		
		                     		setGraphic(cellButton);
	                     			
	                     		}
	                     		
	                     	}
	                		
	                	};
	                }
	                
				});
			
			table.getColumns().addAll(actionColumn,yearColumn, monthColumn, dayColumn, startTimeColumn);
			
		} else if (freeOnly == true) {
			
			table.getColumns().addAll(yearColumn, monthColumn, dayColumn, startTimeColumn);
			
		} else {
			
			table.getColumns().addAll(yearColumn, monthColumn, dayColumn, startTimeColumn, valueColumn);
			
		}
		
		Button homeButton = new Button("HOME");
		if (MenuGUI.userType == 1) {
			
			homeButton.setOnAction(e -> MenuGUI.window.setScene(CustomerMenu.displayMenu()));
			
		} else {
			
			homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
						
		}		
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
	
	//buttons is true if the employeelistmenu is being used to select an employee for some purpose
	public static Scene employeeListMenu(boolean buttons, int function)
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
		
		if (buttons == true) {
			
			employeeIdColumn.setCellFactory(
				new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {

	                @Override
	                public TableCell<Employee, String> call(TableColumn<Employee, String> idButton) {
	                	return new TableCell<Employee,String>() {
	               		 
	                     	String employeeID = getItem();

	                		Button cellButton = new Button(employeeID);
	                     		                     	
	                     	@Override
	                		protected void updateItem(String item, boolean value) {
	                     		super.updateItem(item, value);
	                     		cellButton.setOnAction(e -> {
	                     			String employeeID = getItem();
	                     			System.out.println(employeeID);
	                     			if (function == 0) {
		                     			MenuGUI.window.setScene(showScheduleMenu(employeeID,false));
	                     			} else if (function == 1) {
		                     			MenuGUI.window.setScene(addScheduleMenu(employeeID));
	                     			} else if (function == 2) {
		                     			MenuGUI.window.setScene(showScheduleMenu(employeeID,true));
	                     			} else if (function == 3) {
		                     			MenuGUI.window.setScene(showBookingMenu(employeeID,true,false));
	                     			} else if (function == 4) {
		                     			MenuGUI.window.setScene(showBookingMenu(employeeID,true,true));
	                     			}
	                     			
	                     			});
	                     		cellButton.setText(getItem());
	                     		setGraphic(cellButton);
	                     		
	                     	}
	                		
	                	};
	                }
	                
				});
			
		}
		
		table = new TableView<>();
		table.setItems(getEmployee());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(employeeIdColumn, firstNameColumn, lastNameColumn);
		
		Button homeButton = new Button("HOME");
		if (MenuGUI.userType == 1) {
			
			homeButton.setOnAction(e -> MenuGUI.window.setScene(CustomerMenu.displayMenu()));
			
		} else {
			
			homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
						
		}
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
	
	public static ObservableList<Booking> getBookings(boolean newOnly)
	{
		ObservableList<Booking> Booking = FXCollections.observableArrayList();
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		Booking bookingSlot;
		
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
				
				if (newOnly == false || dataValues[0].compareTo("NEW") == 0) {
				
					bookingSlot = new Booking(dataValues[0], dataValues[1], dataValues[2], dataValues[3], dataValues[4], dataValues[5], dataValues[6], dataValues[7], dataValues[8]);
					
					Booking.add(bookingSlot);	
					
					if (newOnly == true) {
						
						bookingSlot.refreshNew();
						
					}
					
				}
				
			}
			
			reader.close();	
		}
		catch (IOException ioe1)
		{
			ioe1.printStackTrace();
		}
		
		return Booking;
	}
	
	public static Scene bookingsMenu(boolean newOnly)
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
		table.setItems(getBookings(newOnly));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		if (newOnly == true) {

			table.getColumns().addAll(dateColumn, timeColumn, cusomterColumn, employeeColumn, serviceColumn);
			
		} else {
			
			table.getColumns().addAll(dateColumn, timeColumn, statusColumn, cusomterColumn, employeeColumn, serviceColumn);
			
		}
		
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