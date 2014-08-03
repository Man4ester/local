package it.source.model;

import javafx.beans.property.SimpleStringProperty;

public class FileSource {

	private SimpleStringProperty name;
	private SimpleStringProperty type;
	private SimpleStringProperty size;
	private SimpleStringProperty date;

	public FileSource(String name, String type, String size, String date) {
		this.name = new SimpleStringProperty(name);
		this.type = new SimpleStringProperty(type);
		this.size = new SimpleStringProperty(size);
		this.date = new SimpleStringProperty(date);
	}

	public String getName() {
		return name.get();
	}

	public String getType() {
		return type.get();
	}

	public String getSize() {
		return size.get();
	}

	public String getDate() {
		return date.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public void setSize(String size) {
		this.size.set(size);
	}

	public void setDate(String date) {
		this.date.set(date);
	}

}
