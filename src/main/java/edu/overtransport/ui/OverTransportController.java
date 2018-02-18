package edu.overtransport.ui;

import static org.junit.Assert.fail;


import edu.overtransport.DestinationDB;
import edu.overtransport.TransportService;
import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.vehicles.Chariot;
import edu.overtransport.model.vehicles.RacingCar;
import edu.overtransport.model.vehicles.OffRoad;
import edu.overtransport.model.vehicles.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OverTransportController {
	
	//Add Items to Combobox
	ObservableList<String> destinationList = FXCollections.observableArrayList("Scotland", "Liverpool", "Christians Farm");
	
	ObservableList<String> vehicleList = FXCollections.observableArrayList("Chariot", "Racing Car", "SUV");
	
	DestinationDB db = new DestinationDB();
	
	private Trip selectedDestination;
	
	private Vehicle selectedVehicle;

    @FXML
    private Button refuelButton;

    @FXML
    private Button startButton;

    @FXML
    private Button accelerateButton;
    
    @FXML
    private Label informationLabel;
    
    @FXML
    private ComboBox<String> destinationComboBox;
    
    @FXML
    private ComboBox<String> vehicleComboBox;
    
    @FXML
    private TextField vehicleNameTextField;

    @FXML
    public void initialize() {  
    	
    	vehicleComboBox.setValue("Chariot");
    	vehicleComboBox.setItems(vehicleList);
    	
    	destinationComboBox.setValue("Scotland");
    	destinationComboBox.setItems(destinationList);  
    	
    	setCurrentTrip();    	
    }
    
    @FXML
    void startJourney(ActionEvent event)
    {
    	TransportService service = new TransportService();  
    	service.startTrip(selectedVehicle, selectedDestination);
    	
    	try {
			//selectedVehicle.accelerate();
			if (service.hasMoreSegments()) {
				
				service.driveSegment();
				informationLabel.setText(service.printState());
			}
			else
			{
				informationLabel.setText("You have arrived!");
				
				//Start over.
				initialize();
			}
			
		} catch (TicketingException e) {
			e.printStackTrace();
			fail("SUV is slow enough");
		} catch (UnsuitableVehicleException e) {
			e.printStackTrace();
			fail("SUV is good for this trip");
		} catch (LackOfResourcesException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void setCurrentTrip() 
    {
    	//set current trip based on selection in combo box.
    	String destination = destinationComboBox.getSelectionModel().getSelectedItem();
    	String vehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
    	String vehicleName = vehicleNameTextField.getText() != "" ? vehicleNameTextField.getText() : "No Name";   	
    	
    	switch(destination.trim())
    	{
	    	case "Scotland" :
	    	{
	    		selectedDestination = db.getTrip(DestinationDB.LONG_TRIP);
	    		break;
	    	}
	    	
	    	case "Liverpool" :
	    	{
	    		selectedDestination = db.getTrip(DestinationDB.SHORT_TRIP);
	    		break;
	    	}
	    	
	    	case "Christians Farm":
	    	{
	    		selectedDestination = db.getTrip(DestinationDB.TO_A_FARM);
	    		break;
	    	}
	    	
	    	default:
	    	{
	    		selectedDestination = db.getTrip(DestinationDB.LONG_TRIP);
	    		break;
	    	}
    	}
    	   	
    	
    	
    	switch(vehicle.trim())
    	{
	    	
	    	case "Chariot":
	    	{
	    		Chariot suv = new Chariot(1);
	    		selectedVehicle = suv;	   
	    		break;
	    	}
	    	
	    	case "Racing Car":
	    	{
	    		RacingCar suv = new RacingCar(vehicleName);
	    		selectedVehicle = suv;	   
	    		break;
	    	}
	    	
	    	case "SUV":
	    	{
	    		OffRoad suv = new OffRoad(vehicleName);
	    		selectedVehicle = suv;	    		
	    		break;
	    	}
    	}
    	
    	
    	
    }
    

}



