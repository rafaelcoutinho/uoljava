package edu.overtransport.ui;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import edu.overtransport.DestinationDB;
import edu.overtransport.TransportService;
import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.vehicles.AnimalPoweredVehicle;
import edu.overtransport.model.vehicles.Car;
import edu.overtransport.model.vehicles.Chariot;
import edu.overtransport.model.vehicles.OffRoad;
import edu.overtransport.model.vehicles.RacingCar;
import edu.overtransport.model.vehicles.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class OverTransportController {
	
	//Add Items to Combobox
	enum DestinationNames {
		Scotland("Scotland",DestinationDB.LONG_TRIP),Liverpool("Liverpool",DestinationDB.SHORT_TRIP),Christians("Christians Farm",DestinationDB.LONG_TRIP);
		String label; String dbKey;
		DestinationNames(String label, String dbKey){
			this.label=label;
			this.dbKey=dbKey;
		}
		static DestinationNames fromLabel(String l) {
			for (int i = 0; i < values().length; i++) {
				if(values()[i].label.equals(l)) {
					return values()[i];
				}
			}
			return null;
		}
		public String getLabel() {
			return label;
		}
		public String getDbKey() {
			return dbKey;
		}
	}
	ObservableList<String> destinationList = FXCollections.observableArrayList(DestinationNames.Scotland.getLabel(),DestinationNames.Liverpool.getLabel(), DestinationNames.Christians.getLabel());	
	
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
    	
    	vehicleComboBox.setValue("Chariot");
    	vehicleComboBox.setItems(vehicleList);
    	
    	destinationComboBox.setValue(DestinationNames.Scotland.getLabel());
    	destinationComboBox.setItems(destinationList);  
    	
    	setCurrentTrip();    	
    }
    
    @FXML
    void startJourney(ActionEvent event)
    {   	
    	startButton.setText("Drive more");
    	service.startTrip(selectedVehicle, selectedDestination);
    	
    	try {
			selectedVehicle.accelerate();
			if (service.hasMoreSegments()) {				
				service.driveSegment();								
				informationLabel.setText(service.printState());				
				setFuelProgress(false);	
				
				labelTripInformation.setText("Speed Limit: " + Integer.toString(service.getCurrentSegment().getSpeedLimit())+ "\n");
				
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
    	DestinationNames dest = DestinationNames.fromLabel(destinationComboBox.getSelectionModel().getSelectedItem());
    	selectedDestination = db.getTrip(dest.getDbKey());
    	String vehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
    	String vehicleName = vehicleNameTextField.getText() != "" ? vehicleNameTextField.getText() : "No Name";    	
    	
    	
    	   	    	
    	
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

}



