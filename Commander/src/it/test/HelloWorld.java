package it.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;

public class HelloWorld extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Commander");
		List<Button> btnLst = new ArrayList<>();
		HashMap<File, String> map = getDiscs();
		Double step = 0d;
		for (Entry<File, String> button : map.entrySet()) {
			Button btn = new Button();
			btn.setText(button.getKey().getPath());
			btn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Button b = (Button) event.getSource();
					System.out.println("You press button " + b.getText());
					File directory = new File(b.getText());

					// get all the files from a directory
					File[] fList = directory.listFiles();
					if (fList != null) {
						for (File file : fList) {
							if (file.isFile()) {
								System.out.println("file_" + file.getName());
								// files.add(file);
							} else if (file.isDirectory()) {
								System.out.println("folder_" + file.getName());
								// listf(file.getAbsolutePath(), files);
							}
						}
					}
				}
			});
			btnLst.add(btn);
		}

		Group root = new Group();
		primaryStage.setScene(new Scene(root, 600, 400));
		for (Button button : btnLst) {
			button.setLayoutX(10 + step);
			step += 50;
			button.setLayoutY(20);
			root.getChildren().add(button);
		}

		primaryStage.show();

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

}
