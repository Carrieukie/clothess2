package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddcustController implements Initializable {


	@FXML
	private JFXButton addcust;
	@FXML
	private JFXButton cust;
	@FXML
	private JFXTextField name;
	@FXML
	private JFXTextField phoneno;
	@FXML
	private JFXTextArea measurements;
	@FXML
	private JFXDatePicker pick;
	@FXML
	private JFXRadioButton male;
	@FXML
	private JFXRadioButton female;
	@FXML
	private JFXComboBox<String> clothtype;
	@FXML
	private JFXButton insert;
	@FXML
	private JFXButton fabric;
	@FXML
	private Text dyna;

	@FXML
	private ImageView image;
	private byte[] data;
	private byte[] stimage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clothtype.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			System.out.println(newValue);
			dyna.setText(newValue + "'s Measurements" );
		});

	}

	@FXML
	private void getimage() {
		stimage = filechoose();
	}

	@FXML
	private void setmale() {
		ObservableList<String> genders = FXCollections.observableArrayList("Trouser", "Shirt");
		clothtype.setValue("Trouser");
		clothtype.setItems(genders);
	}

	@FXML
	private void setfemale() {
		ObservableList<String> genders = FXCollections.observableArrayList("Dress", "Blouse", "Skirt");
		clothtype.setValue("Dress");
		clothtype.setItems(genders);
	}
	
	

	@FXML
	private void insert() {

		String Name = "", Phoneno = "", Clothtype = "", Measurements = "", Pick = "";

		if (name != null) {
			Name = name.getText();
		}

		if (phoneno != null) {
			Phoneno = phoneno.getText();
		}
		
		if (clothtype != null) {
			Clothtype = (String) clothtype.getValue();
		}

		if (measurements != null) {
			Measurements =  measurements.getText().replaceAll("\n", System.getProperty("line.separator"));
		}

		if (pick.getValue() != null) {
			LocalDate date = pick.getValue();
			Date dateFormat = null;
			try {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
				Pick = new SimpleDateFormat("MMMM dd ,yyyy ").format(dateFormat);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(dateFormat));

			database.insert(Name, Phoneno, Clothtype, Measurements, Pick, stimage);
			try {

				insert.getScene().getWindow().hide();
				Stage signup = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("viewcust.fxml"));
				Scene scene = new Scene(root);
				signup.setScene(scene);
				signup.getIcons().add(new Image("/image/icon.png"));
				signup.setTitle("Fashion Desktop app");
				signup.show();
				signup.setResizable(false);

			} catch (Exception e) {
				System.out.println(" Error signup : " + e);
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText(" Date to pick not entered");
			alert.showAndWait();
		}

	}

	@FXML
	public byte[] filechoose() {
		FileChooser fc = new FileChooser();
		Stage signup = new Stage();
		fc.getExtensionFilters().addAll(//
				new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		File selectedFile = fc.showOpenDialog(signup);

		if (selectedFile != null) {
			System.out.println(selectedFile.getAbsolutePath());
			Image sel = new Image("file:" + selectedFile.getAbsolutePath());
		 	image.setImage(sel);
			data = readFile(selectedFile.getAbsolutePath());
			;
		}

		return data;
	}

	private byte[] readFile(String file) {
		ByteArrayOutputStream bos = null;
		try {
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream();
			for (int len; (len = fis.read(buffer)) != -1;) {
				bos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e2) {
			System.err.println(e2.getMessage());
		}
		return bos != null ? bos.toByteArray() : null;
	}
	
	@FXML
	public void addCust() {
		try {

			cust.getScene().getWindow().hide();
			Stage signup = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Addcust.fxml"));
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

	@FXML
	public void viewCust() {
		try {

			cust.getScene().getWindow().hide();
			Stage signup = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Viewcust.fxml"));
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
