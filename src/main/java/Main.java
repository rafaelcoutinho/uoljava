import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Entry point for the executable Jar
 * 
 * @author coutinho
 *
 */
public class Main extends Application {
	private Stage primaryStage;
	private GridPane mainLayout;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)  {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Driver App");
		
		showMainView();
	}

	private void showMainView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MainView.fxml"));
		
	}
}
