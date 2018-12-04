package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUpController implements Initializable {

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXPasswordField password1;

	@FXML
	private JFXButton login;

	@FXML
	private JFXButton add;
	@FXML
	private ImageView back;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void addadmin(ActionEvent event) {
		String Name = "", pass = "";

		if (Name != null) {
			Name = username.getText();
		}

		if (pass != null) {
			if (password.getText().equals(password1.getText())) {
				pass = password.getText();
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText("Warning");
				alert.initStyle(StageStyle.UTILITY);
				alert.setContentText(" Please make sure the passwords match ...");
				alert.showAndWait();
			}

		}
		if (username.getText() != "" && password.getText() != "") {
			database.addadmin(Name, pass);
			try {
				login.getScene().getWindow().hide();
				Stage home = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("FXLogin.fxml"));
				Scene scene = new Scene(root);
				home.getIcons().add(new Image("/image/icon.png"));
				home.setTitle("Fashion Desktop app");
				home.setScene(scene);
				home.show();
				home.setResizable(false);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.initStyle(StageStyle.UTILITY);
				alert.setContentText("Admin Added....Launch app and Log IN");
				alert.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setHeaderText("Warning");
			alert.initStyle(StageStyle.UTILITY);
			alert.setContentText("Empty Fields!!");
			alert.showAndWait();
		}
	

	}

	@FXML
	private void loginAction(ActionEvent event) {
		try {
			add.getScene().getWindow().hide();
			Stage signup = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("FXLogin.fxml"));
			Scene scene = new Scene(root);
			signup.getIcons().add(new Image("/image/icon.png"));
			signup.setTitle("Fashion Desktop app");
			signup.setScene(scene);
			signup.show();
			signup.setResizable(false);
		} catch (Exception e) {
			System.out.println(" Error signup : " + e);
		}
	}

}
