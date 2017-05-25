package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerMenu 
{
	//Displays customer menu
	public static Scene displayMenu()
	{		
		MenuGUI.window.setTitle("ABS - Customer Menu");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Welcome " + Menu.username);
		
		Button b1 = new Button("Make a Booking");
		b1.setOnAction(e -> MenuGUI.window.setScene(makeBookingMenu()));
		b1.setMaxWidth(Double.MAX_VALUE);
		Button b2 = new Button("View My Bookings");
		b2.setOnAction(e ->	MenuGUI.window.setScene(bookingsMenu()));
		b2.setMaxWidth(Double.MAX_VALUE);
		Button b3 = new Button("View Availablility");
		//b3.setOnAction(e ->	MenuGUI.window.setScene(bookingsMenu()));
		b3.setMaxWidth(Double.MAX_VALUE);
		Button b4 = new Button("View Business Opening Days/Time");
		b4.setOnAction(e ->	MenuGUI.window.setScene(displayBusinessTimes()));
		b4.setMaxWidth(Double.MAX_VALUE);
		Button b5 = new Button("View Services");
		b5.setOnAction(e ->	MenuGUI.window.setScene(serviceListMenu()));
		b5.setMaxWidth(Double.MAX_VALUE);
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		
		VBox centerBox = new VBox();
		centerBox.getChildren().addAll(b1, b2, b3, b4, b5);
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
		
		Scene scene = new Scene(borderPane, 400, 250);
		return scene;		
	}
	
	//Displays make booking
	public static Scene makeBookingMenu()
	{
		MenuGUI.window.setTitle("ABS - Make a Booking");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Make a Booking");

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
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
		
		Scene scene = new Scene(borderPane, 700, 300);
		return scene;
	}
	
	public static ObservableList<Service> getService()
	{
		ObservableList<Service> serivces = FXCollections.observableArrayList();
		String deliminator = "\\|";
		String[] dataValues;
		String currentLine;
		
		BufferedReader reader = null;
				
		try 
		{	
			reader = new BufferedReader(new FileReader("SuggarHaircutServices.txt"));
			
			while ((currentLine = reader.readLine()) != null)
			{
				dataValues = currentLine.split(deliminator);
				
				serivces.add(new Service(dataValues[0], dataValues[1], dataValues[2]));	
			}
			
			reader.close();	
		}
		catch (IOException ioe1)
		{
			ioe1.printStackTrace();
		}
		
		return serivces;
	}
	
	//Displays a table of services
	public static Scene serviceListMenu()
	{
		MenuGUI.window.setTitle("ABS - Service List");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Service List");
		
		TableView<Service> table;
		TableColumn<Service, String> serviceNameColumn = new TableColumn<>("Name");
		serviceNameColumn.setMinWidth(50);
		serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Service, Integer> serviceDurationColumn = new TableColumn<>("Duration");
		serviceDurationColumn.setMinWidth(25);
		serviceDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		
		TableColumn<Service, String> serviceDescriptionColumn = new TableColumn<>("Description");
		serviceDescriptionColumn.setMinWidth(400);
		serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		table = new TableView<>();
		table.setItems(getService());
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(serviceNameColumn, serviceDurationColumn, serviceDescriptionColumn);
		
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
		
		Scene scene = new Scene(borderPane, 700, 300);
		return scene;
	}
	
	//Displays business times
	public static Scene displayBusinessTimes()
	{		
		MenuGUI.window.setTitle("ABS - Business Times");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Business Times");
		String[] days = {"Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:", "Sunday:"};
		Label[] dayLabel = new Label[7];
		Label[] openingTimeLabel = new Label[7];
		Label[] closingTimeLabel = new Label[7];
		ArrayList<DayOfWeek> numberOfDays = BusinessManagement.selectedBusiness.getOpeningDays();
		ArrayList<LocalTime> openingTimes = BusinessManagement.selectedBusiness.getOpenTime();
		ArrayList<LocalTime> closingTimes = BusinessManagement.selectedBusiness.getCloseTime();
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(8);
		grid.setHgap(10);
		
		for(int i = 0; i < numberOfDays.size(); i++)
		{
			//Displays open days
			dayLabel[i] = new Label(days[i]);
			GridPane.setConstraints(dayLabel[i], 0, i);
			
			//Displays open times
			openingTimeLabel[i] = new Label(openingTimes.get(i).toString());
			GridPane.setConstraints(openingTimeLabel[i], 1, i);
			
			//Displays closing times
			closingTimeLabel[i] = new Label(closingTimes.get(i).toString());
			GridPane.setConstraints(closingTimeLabel[i], 2, i);
			
			grid.getChildren().addAll(dayLabel[i], openingTimeLabel[i], closingTimeLabel[i]);
		}
		
		Button logoutButton = new Button("LOGOUT");
		logoutButton.setStyle("-fx-base: red;");
		logoutButton.setOnAction(e -> MenuGUI.window.setScene(MenuGUI.loginScene));
		Button homeButton = new Button("HOME");
		homeButton.setOnAction(e -> MenuGUI.window.setScene(displayMenu()));
		homeButton.setMaxWidth(100);
		
		HBox topBox = new HBox();
		topBox.setStyle("-fx-font-size: 24; -fx-background-color: #1976D2;");
		topBox.setPadding(new Insets(10, 10, 10, 10));
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().add(heading);
		
		VBox leftBox = new VBox();
		leftBox.setStyle("-fx-background-color: grey;");
		leftBox.setPadding(new Insets(10, 10, 10, 10));
		leftBox.setSpacing(10);
		leftBox.setAlignment(Pos.TOP_CENTER);
		leftBox.getChildren().addAll(homeButton, logoutButton);
		
		borderPane.setTop(topBox);
		borderPane.setCenter(grid);
		borderPane.setLeft(leftBox);
		
		Scene scene = new Scene(borderPane, 400, 250);
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
				
				if(dataValues[7].equals(Menu.username))
				{
					Booking.add(new Booking(dataValues[0], dataValues[1], dataValues[2], dataValues[3], dataValues[4], dataValues[5], dataValues[6], dataValues[7], dataValues[8]));
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
	
	//Displays customer bookings
	public static Scene bookingsMenu()
	{
		MenuGUI.window.setTitle("ABS - View My Bookings");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("View My Bookings");
		
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
		table.setPlaceholder(new Label("No bookings have been made"));
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
}
