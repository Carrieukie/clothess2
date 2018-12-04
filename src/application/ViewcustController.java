package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeListener;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewcustController implements Initializable {

//	ObservableList<dataobjects> oblist = FXCollections.observableArrayList();

	@FXML
	private JFXButton addcust;
	@FXML
	private JFXButton cust;
	@FXML
	private JFXListView<String> ls;
	@FXML
	private Text name;
	@FXML
	private Text phoneno;
	@FXML
	private Text orderdt;
	@FXML
	private Text cloth;
	@FXML
	private Text measurement;
	@FXML
	private TextField searchfield;
	@FXML
	private Text pickdt;
	@FXML
	private ImageView image;
	@FXML
	private JFXSpinner spinner;
	ObservableList<String> entries = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		spinner.setVisible(false);
		buildData();
		ls.setOnMouseClicked(new ListViewHandler() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				spinner.setVisible(true);

				System.out.println(ls.getSelectionModel().getSelectedItem());

				String sql = "SELECT * FROM customers where name = ?";
				try (Connection conn = DriverManager.getConnection(database.url);
						Statement stmt = conn.createStatement();
						PreparedStatement pst = conn.prepareStatement(sql);) {

					pst.setString(1, ls.getSelectionModel().getSelectedItem());
					ResultSet rs = pst.executeQuery();
					while (rs.next()) {
						name.setText(rs.getString("name"));
						phoneno.setText(rs.getString("phoneno"));
						cloth.setText(rs.getString("clothtype"));
						measurement.setText(rs.getString("measurements"));
						orderdt.setText(rs.getString("now"));
						pickdt.setText(rs.getString("datepk"));
						setimage(rs.getBytes("fabric"));
						spinner.setVisible(false);
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	private void setimage(byte[] imagedata) {
		spinner.setVisible(true);
		BufferedImage buff_img = null;
		try {
			buff_img = ImageIO.read(new ByteArrayInputStream(imagedata));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image im;

		if (buff_img != null) {
			im = SwingFXUtils.toFXImage(buff_img, null);
			image.setImage(im);
		}
		spinner.setVisible(false);
	}

	public void buildData() {
		System.out.println("Building data");
		String sql = "SELECT name FROM customers";
		try (Connection conn = DriverManager.getConnection(database.url);
				Statement stmt = conn.createStatement();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				String current = rs.getString("name");
				ObservableList<String> list = FXCollections.observableArrayList(current);
				FilteredList<String> filteredData = new FilteredList<>(list, p -> true);
				searchfield.setOnKeyReleased(e -> {
					searchfield.textProperty().addListener((observableValue, oldValue, newValue) -> {
						filteredData.setPredicate(p -> {
							search((String) oldValue, (String) newValue);
							if (newValue == null || newValue.isEmpty()) {
								return true;
							}

							String lowerCaseSearch = newValue.toLowerCase();
							return String.valueOf(p.contains(lowerCaseSearch)) != null;
						});
					});
				});

				ls.getItems().addAll(list);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private void search(String oldVal, String newVal) {

		if (oldVal != null && (newVal.length() < oldVal.length())) {
			System.out.println("Not found");
		} else {
			String value = newVal.toUpperCase();
			ObservableList<String> subentries = FXCollections.observableArrayList();
			for (Object entry : ls.getItems()) {
				System.out.println(entry);
				String entryText = (String) entry;
				if (entryText.toUpperCase().contains(value)) {
					subentries.add(entryText);
				}

			}
			ls.getItems().clear();
			ls.getItems().addAll(subentries);
		}
	}

	@FXML
	public void addCust() {
		try {
			System.out.println("Clicked");
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
			System.out.println("bdfgodfg");
			cust.getScene().getWindow().hide();
			Stage signup = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("viewcust.fxml"));
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

class ListViewHandler implements EventHandler<MouseEvent> {
	@Override
	public void handle(MouseEvent event) {

	}

}
