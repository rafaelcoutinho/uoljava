package edu.overtransport.ui;

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

/**
 * UI Controller responsible for the GUI of this program. Allows to select a
 * trip and construct a vehicle. Then to drive all the road segments.
 * 
 * @author chambers
 *
 */
public class OverTransportController {
	enum VehicleTypes {
		Chariot("Chariot"), RacingCar("Racing Car"), Offroad("Off-road");
		String label;

		VehicleTypes(String label) {
			this.label = label;
		}

		static VehicleTypes fromLabel(String l) {
			for (int i = 0; i < values().length; i++) {
				if (values()[i].label.equals(l)) {
					return values()[i];
				}
			}
			return null;
		}

		public String getLabel() {
			return label;
		}

	}

	ObservableList<String> vehicleList = FXCollections.observableArrayList(VehicleTypes.Chariot.getLabel(),
			VehicleTypes.RacingCar.getLabel(), VehicleTypes.Offroad.getLabel());

	enum DestinationNames {
		Scotland("Scotland", DestinationDB.LONG_TRIP), Liverpool("Liverpool",
				DestinationDB.SHORT_TRIP), Christians("Christians Farm", DestinationDB.TO_A_FARM);
		String label;
		String dbKey;

		DestinationNames(String label, String dbKey) {
			this.label = label;
			this.dbKey = dbKey;
		}

		static DestinationNames fromLabel(String l) {
			for (int i = 0; i < values().length; i++) {
				if (values()[i].label.equals(l)) {
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

	// Add Items to Combobox
	ObservableList<String> destinationList = FXCollections.observableArrayList(DestinationNames.Scotland.getLabel(),
			DestinationNames.Liverpool.getLabel(), DestinationNames.Christians.getLabel());

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
	private Button brakeButton;

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

	boolean onGoingTrip = false;

	@FXML
	public void initialize() {

		// reset combobox to index 0
		vehicleComboBox.setValue(vehicleList.get(0));
		vehicleComboBox.setItems(vehicleList);

		destinationComboBox.setValue(destinationList.get(0));
		destinationComboBox.setItems(destinationList);

		// clear
		informationLabel.setText("");
		labelTripInformation.setText("");
	}

	@FXML
	synchronized void startJourney(ActionEvent event) {

		if (!onGoingTrip) {
			if (!setCurrentTrip()) {
				return;
			}
			service.startTrip(selectedVehicle, selectedDestination);
			selectedVehicle.accelerate();// accelerate once
			onGoingTrip = true;
			String startButtonLabel = "Drive next..";
			changeUIMode(startButtonLabel, onGoingTrip);

		}

		try {

			if (service.hasMoreSegments()) {
				service.driveSegment();
				informationLabel.setText(service.printState());
				setFuelProgress(false);

				String segmentInfo = "Speed Limit: " + Integer.toString(service.getCurrentSegment().getSpeedLimit())
						+ " km per hour \n";

				labelTripInformation.setText(segmentInfo);
			} else {
				JOptionPane.showMessageDialog(null, "You have arrived!", "Trip status",
						JOptionPane.INFORMATION_MESSAGE);
				informationLabel.setText("You have arrived!");
				onGoingTrip = false;
				changeUIMode("Start", onGoingTrip);
			}

		} catch (TicketingException e) {
			JOptionPane.showMessageDialog(null, "You just got a ticket for speeding. Please reduce your speed.",
					"Speeding Ticket", JOptionPane.WARNING_MESSAGE);

		} catch (UnsuitableVehicleException e) {

			JOptionPane.showMessageDialog(null, "This vehicle type is not suitable for this road segment.",
					"Unsuitable Vehicle", JOptionPane.WARNING_MESSAGE);

			// start over
			startOver();

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

			Object[] refueloptions = { refuelOption, "Cancel Trip" };

			int result = JOptionPane.showOptionDialog(null, refuelMessage, refuelTitle, JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, refueloptions, null);

			if (result == JOptionPane.YES_OPTION) {
				refuel();
			} else {
				// start over
				startOver();
			}
			informationLabel.setText(service.printState());
			setFuelProgress(false);
		}
	}

	private void startOver() {
		// start over
		onGoingTrip = false;
		changeUIMode("Start", onGoingTrip);
	}

	private void changeUIMode(String startButtonLabel, boolean tripGoingOn) {
		startButton.setText(startButtonLabel);
		accelerateButton.setDisable(!tripGoingOn);
		brakeButton.setDisable(!tripGoingOn);
		refuelButton.setDisable(!tripGoingOn);

		labelTripInformation.setText("");

		destinationComboBox.setDisable(tripGoingOn);
		vehicleComboBox.setDisable(tripGoingOn);
	}

	@FXML
	private boolean setCurrentTrip() {
		// set current trip based on selection in combo box.
		String tripSelName = destinationComboBox.getSelectionModel().getSelectedItem();
		DestinationNames dest = DestinationNames.fromLabel(tripSelName);
		if (dest == null) {
			// show error
			JOptionPane.showMessageDialog(null, "Please select a destination", "Invalid input",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		selectedDestination = db.getTrip(dest.getDbKey());
		String vehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
		VehicleTypes vType = VehicleTypes.fromLabel(vehicle);
		if (vType == null) {
			// show error
			JOptionPane.showMessageDialog(null, "Please select a vehicle", "Invalid input", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String vehicleName = "";

		switch (vType) {

		case Chariot: {
			Integer horses = null;
			do {
				String number = JOptionPane.showInputDialog(null,
						"You have selected a Chariot, please enter the number of horses you want on it. (Maximum of 10)",
						"Horse power", JOptionPane.QUESTION_MESSAGE);
				try {
					horses = Integer.parseInt(number);
				} catch (NumberFormatException e) {
					int opt = JOptionPane.showConfirmDialog(null,
							"Please enter a valid numeric value. Want to try again?", "Speeding Ticket",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					System.out.println(opt);
					if (opt == 2) {
						return false;
					}
				}

			} while (horses == null);

			selectedVehicle = new Chariot(horses);
			break;
		}

		case RacingCar: {
			vehicleName = getVehicleName();
			selectedVehicle = new RacingCar(vehicleName);
			break;
		}

		case Offroad: {
			vehicleName = getVehicleName();
			selectedVehicle = new OffRoad(vehicleName);
			break;
		}
		default:
			// show msg
			JOptionPane.showMessageDialog(null, "Invalid vehicle", "Invalid input", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		setFuelProgress(true);
		return true;
	}

	String getVehicleName() {

		String vehicleName = JOptionPane.showInputDialog(null, "Please enter the name of the Vehicle:", "Vehicle Name",
				JOptionPane.QUESTION_MESSAGE);

		if (vehicleName.equals("")) {
			vehicleName = "No Name";
		}

		return vehicleName;
	}

	@FXML
	void refuelVehicle(ActionEvent even) {
		refuel();
		setFuelProgress(false);
	}

	void refuel() {
		if (selectedVehicle instanceof Car) {
			((Car) selectedVehicle).refuel();

		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			((AnimalPoweredVehicle) selectedVehicle).feed();
		}
		informationLabel.setText(service.printState());
	}

	void setFuelProgress(Boolean reset) {

		double fuelPercentage = 0.0;

		if (selectedVehicle instanceof Car) {
			fuelPercentage = ((Car) selectedVehicle).getFuelStatus();

		} else if (selectedVehicle instanceof AnimalPoweredVehicle) {
			fuelPercentage = ((AnimalPoweredVehicle) selectedVehicle).getTiredness();
		}

		fuelProgressBar.setProgress(fuelPercentage / 100);

		// update information

		if (!reset) {
			informationLabel.setText(service.printState());
		}

	}

	@FXML
	void accelerateButtonPressed(ActionEvent event) {
		accelerate();
		informationLabel.setText(service.printState());
	}

	@FXML
	void brakeButtonPressed(ActionEvent even) {
		brake();
		informationLabel.setText(service.printState());
	}

	void accelerate() {
		selectedVehicle.accelerate();
	}

	void brake() {
		selectedVehicle.brake();
	}

}
