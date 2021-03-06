package it.source.main;

import it.source.model.FileSource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;

public class Commander extends Application {

	private TableView<FileSource> table = new TableView<FileSource>();

	private Label label = new Label("source:");

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	private ObservableList<FileSource> data = FXCollections
			.observableArrayList(new FileSource("...", "", "", "", null));

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Commander");
		primaryStage.setResizable(false);
		List<Button> btnLst = new ArrayList<>();
		HashMap<File, String> map = getDiscs();
		for (Entry<File, String> button : map.entrySet()) {
			Button btn = new Button();
			btn.setText(button.getKey().getPath());
			btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Button b = (Button) event.getSource();
					label.setText(b.getText());

					data.clear();
					data.addAll(getAllFilesInside(b.getText()));
				}
			});
			btnLst.add(btn);
		}

		label.setFont(new Font("Arial", 12));

		TableColumn nameCol = new TableColumn("name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<FileSource, String>(
				"name"));

		TableColumn typeCol = new TableColumn("type");
		typeCol.setMinWidth(40);
		typeCol.setCellValueFactory(new PropertyValueFactory<FileSource, String>(
				"type"));

		TableColumn sizeCol = new TableColumn("size");
		sizeCol.setMinWidth(100);
		sizeCol.setCellValueFactory(new PropertyValueFactory<FileSource, String>(
				"size"));

		TableColumn dateCol = new TableColumn("date");
		dateCol.setMinWidth(150);
		dateCol.setCellValueFactory(new PropertyValueFactory<FileSource, String>(
				"date"));

		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		table.setItems(data);
		table.getColumns().addAll(nameCol, typeCol, sizeCol, dateCol);

		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						TableView<FileSource> tmp = (TableView<FileSource>) mouseEvent
								.getSource();
						if (tmp.getSelectionModel().getSelectedItem() == null) {
							return;
						}
						File file = tmp.getSelectionModel().getSelectedItem()
								.getSourceFile();
						String name = tmp.getSelectionModel().getSelectedItem()
								.getName();
						data.clear();
						if (file != null && file.isDirectory()
								&& file.getParentFile() != null) {
							if ("...".equals(name)) {
								data.add(new FileSource("...", "...", "...",
										null, file.getParentFile()));
								if (file.getParentFile() != null) {
									data.addAll(getAllFilesInside(file
											.getParentFile().getAbsolutePath()));
								}
								label.setText(file.getParentFile()
										.getAbsolutePath());

							} else {
								label.setText(file.getAbsolutePath());
								data.add(new FileSource("...", "...", "...",
										null, file));
								data.addAll(getAllFilesInside(file
										.getAbsolutePath()));

							}
						}

					}
				}
			}
		});

		final VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(60, 0, 0, 10));

		final HBox vboxBtn = new HBox();
		vboxBtn.setSpacing(10);
		vboxBtn.setPadding(new Insets(10, 0, 0, 10));

		Group root = new Group();
		primaryStage.setScene(new Scene(root, 500, 500));
		vboxBtn.getChildren().addAll(btnLst);
		vbox.getChildren().addAll(label, table);
		root.getChildren().addAll(vbox, vboxBtn);

		primaryStage.show();

	}

	private List<FileSource> getAllFilesInside(String path) {
		File directory = new File(path);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		List<FileSource> lst = new ArrayList<FileSource>();
		if (fList != null) {
			for (File file : fList) {
				if (file.isFile() && !file.isHidden()) {
					lst.add(new FileSource(FilenameUtils.getBaseName(file
							.getAbsolutePath()), FilenameUtils
							.getExtension(file.getAbsolutePath()), String
							.valueOf(file.length()), sdf.format(file
							.lastModified()), file));
				} else if (file.isDirectory() && !file.isHidden()) {
					lst.add(new FileSource(file.getName(), "folder", "", sdf
							.format(file.lastModified()), file));
				}
			}
		}
		return lst;
	}

	public HashMap<File, String> getDiscs() {
		HashMap<File, String> disc = new LinkedHashMap<>();
		File[] paths;
		FileSystemView fsv = FileSystemView.getFileSystemView();
		paths = File.listRoots();
		for (File path : paths) {
			disc.put(path, fsv.getSystemTypeDescription(path));
		}
		return disc;
	}

	public ObservableList<FileSource> getData() {
		return data;
	}

	public void setData(ObservableList<FileSource> data) {
		this.data = data;
	}

}
