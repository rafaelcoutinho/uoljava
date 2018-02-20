package edu.overtransport.ui;
import static org.junit.Assert.fail;

import javax.swing.JOptionPane;

import edu.overtransport.DestinationDB;
import edu.overtransport.TransportService;
import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.vehicles.AnimalPoweredVehicle;
import edu.overtransport.model.vehicles.Car;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class OverTransportController {
	
	//Add Items to Combobox
	ObservableList<String> destinationList = FXCollections.observableArrayList("Scotland", "Liverpool", "Christians Farm");	
	ObservableList<String> vehicleList = FXCollections.observableArrayList("Chariot", "Racing Car", "SUV");
	TransportService service = new TransportService();  
	
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
    private Button breakButton;
    
    @FXML
    private Label informationLabel;
    
    @FXML
    private Label labelTripInformation;
    
    @FXML
    private ComboBox<String> destinationComboBox;
    
    @FXML
    private ComboBox<String> vehicleComboBox;
    
    @FXML
    private ProgressBar fuelProgressBar;    

    @FXML
    private TextField vehicleNameTextField;    
 
    
    @FXML
    public void initialize() {  
    	
    	//reset combobox to index 0
    	vehicleComboBox.setValue("Chariot");
    	vehicleComboBox.setItems(vehicleList);
    	
    	destinationComboBox.setValue("Scotland");
    	destinationComboBox.setItems(destinationList);  
    	
    	//clear
    	vehicleNameTextField.setText("");
    	informationLabel.setText("");
    	labelTripInformation.setText("");
    	
    	setCurrentTrip();    	
    }
    
    @FXML
    void startJourney(ActionEvent event)
    {   	
    	String segmentInfo;
    	
    	service.startTrip(selectedVehicle, selectedDestination);
    	
    	try {
			selectedVehicle.accelerate();
			if (service.hasMoreSegments()) {				
				service.driveSegment();								
				informationLabel.setText(service.printState());				
				setFuelProgress(false);	
				
				segmentInfo = "Speed Limit: " + Integer.toString(service.getCurrentSegment().getSpeedLimit())+ "\n";	
				
				labelTripInformation.setText(segmentInfo);				
			}
			else
			{
				informationLabel.setText("You have arrived!");				
				//Start over.
				initialize();
			}
			
		} catch (TicketingException e) {
			JOptionPane.showMessageDialog(null, "You just got a ticket for speeding. Please reduce your speed.", "Speeding Ticket", JOptionPane.WARNING_MESSAGE);
			
		} catch (UnsuitableVehicleException e) {
			
			JOptionPane.showMessageDialog(null, "This vehicle type is not suitable for the journey. " + e.getMessage(), "Unsuitable Vehicle", JOptionPane.WARNING_MESSAGE);
			
			//start over
			initialize();
			
		} catch (LackOfResourcesException e) {
			
			String refuelMessage = "";
			String refuelTitle = "";
			String refuelOption = "";
			
			if (selectedVehicle instanceof Car) {
				refuelMessage = "You ran out of fuel! What would you like to do?";
				refuelTitle = "Out of Fuel";
				refuelOption = "Refuel";
			} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
				refuelMessage = "Poor horse! Your horse needs rest. What do you want to do?";
				refuelTitle = "Horse is tired!";
				refuelOption = "Feed the Horse";
			}
			
			
			
			Object[] refueloptions = { refuelOption , "Cancel Trip"};
		    
		    int result = JOptionPane.showOptionDialog(null, refuelMessage, refuelTitle,
		            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		            null, refueloptions, null);
		    
		    if (result == JOptionPane.YES_OPTION){
		    	refuel();
		    }
		    else
		    {
		    	//start over
		    	initialize();
		    }
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
    	setFuelProgress(true);
    }
    
    
    @FXML
    void refuelVehicle(ActionEvent even) {
    	refuel();
    }
    
    void refuel()
    {
    	if (selectedVehicle instanceof Car) {
    		((Car) selectedVehicle).refuel();
    		setFuelProgress(false);    		
			
		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			((AnimalPoweredVehicle) selectedVehicle).feed();
			setFuelProgress(false);
		}
    }
    
    
    void setFuelProgress(Boolean reset)
    {
    	
    	double fuelPercentage = 0.0;    	
    	
    	if (selectedVehicle instanceof Car) {
			fuelPercentage = ((Car) selectedVehicle).getFuelStatus();										
			
		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			fuelPercentage = ((AnimalPoweredVehicle) selectedVehicle).getTiredness();					
		}
    	
		fuelProgressBar.setProgress(fuelPercentage/100);
		
		//update information
		
		if (!reset)
    	{
			informationLabel.setText(service.printState());
    	}
		
    }

    @FXML
    void accelerateButtonPressed(ActionEvent event)
    {
    	accelerate();    	
		informationLabel.setText(service.printState());    	
    }
    
    
    @FXML
    void brakeButtonPressed(ActionEvent even)
    {
    	brake();
    	informationLabel.setText(service.printState());
    }
    
    void accelerate()
    {
    	if (selectedVehicle instanceof Car) {
			((Car) selectedVehicle).accelerate();										
			
		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			((AnimalPoweredVehicle) selectedVehicle).accelerate();					
		}
    }
    
    void brake()
    {
    	if (selectedVehicle instanceof Car) {
			((Car) selectedVehicle).brake();										
			
		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			((AnimalPoweredVehicle) selectedVehicle).brake();					
		}
    }
    
}



