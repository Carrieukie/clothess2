package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.stage.Stage;

public class LogInController implements Initializable {
	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXButton login;

	@FXML
	private JFXButton loginact;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void loginAction(ActionEvent event) {

		String Username, Password;

		try (Connection conn = DriverManager.getConnection(database.url); Statement stmt = conn.createStatement()) {
			Username = username.getText();
			Password = password.getText();
			String sql = "select * from admins where name = '" + Username + "'and password = '" + Password + "'";
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				System.out.println("login is ok ");
				login.getScene().getWindow().hide();
				Stage home = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("Addcust.fxml"));
					Scene scene = new Scene(root);
					home.getIcons().add(new Image("/image/icon.png"));
					home.setTitle("Fashion Desktop app");
					home.setScene(scene);
					home.show();
					home.setResizable(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println("Error username or password not correct");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(" username or password not correct ...");
				alert.showAndWait();

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@FXML
	public void SignupAction(ActionEvent event) {
		try {

			loginact.getScene().getWindow().hide();
			Stage signup = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("FXSignup.fxml"));
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
