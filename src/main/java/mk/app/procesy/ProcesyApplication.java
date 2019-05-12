package mk.app.procesy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mk.app.procesy.configuration.AppConfig;

@SpringBootApplication
public class ProcesyApplication extends Application {

	private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ProcesyApplication.class, args);
		
		launch(args);
	}
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		//TODO: not working, 
		loader.setControllerFactory(applicationContext::getBean);
		
		Parent root = loader.load(getClass().getResource("/fxml/Main.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setTitle("Procesy");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
