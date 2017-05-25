package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

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
	public static Scene displayMenu()
	{		
		MenuGUI.window.setTitle("ABS - Customer Menu");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Welcome --customer name--");
		
		Button b1 = new Button("Make a Booking");
		//b1.setOnAction(e -> MenuGUI.window.setScene(displayEmployeeManagement()));
		b1.setMaxWidth(Double.MAX_VALUE);
		Button b2 = new Button("View My Bookings");
		//b2.setOnAction(e ->	MenuGUI.window.setScene(bookingsMenu()));
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
	
	public static Scene serviceListMenu()
	{
		MenuGUI.window.setTitle("ABS - Service List");
		BorderPane borderPane = new BorderPane();
		Label heading = new Label("Welcome --customer name--");
		
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
		
//		for(int i = 0; i < days.length ; i++)
//		{
//			dayLabel[i] = new Label(days[i]);
//			GridPane.setConstraints(dayLabel[i], 0, i);
//		}
//		
//		for(int i = 0; i < days.length ; i++)
//		{
//			if(i < numberOfDays.size())
//			{
//				if(i == numberOfDays.get(i).getValue())
//				{
//					openingTimeLabel[i-1] = new Label(openingTimes.get(i).toString());
//					GridPane.setConstraints(openingTimeLabel[i-1], 1, i-1);
//				}
//				else
//				{
//					openingTimeLabel[i-1] = new Label("CLOSED");
//					GridPane.setConstraints(openingTimeLabel[i-1], 1, i-1);
//				}
//			}
//		}
//		
//		for(int i = 0; i < days.length ; i++)
//		{
//			if(i < numberOfDays.size())
//			{
//				if(i == numberOfDays.get(i).getValue())
//				{
//					closingTimeLabel[i-1] = new Label(closingTimes.get(i).toString());
//					GridPane.setConstraints(closingTimeLabel[i-1], 2, i-1);
//				}
//				else
//				{
//					closingTimeLabel[i-1] = new Label();
//					GridPane.setConstraints(closingTimeLabel[i-1], 2, i-1);
//				}
//			}
//		}
//		
//		for(int i = 0; i < 7; i++)
//		{
//			grid.getChildren().add(dayLabel[i]);
//			grid.getChildren().add(openingTimeLabel[i]);
//			grid.getChildren().add(closingTimeLabel[i]);
//		}
		
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
}
