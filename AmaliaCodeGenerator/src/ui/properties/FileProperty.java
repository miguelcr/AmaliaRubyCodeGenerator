package ui.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sérgio GOnçalves
 */
public class FileProperty {
    private StringProperty name;
    private StringProperty path;
    private StringProperty status;
    private File file;
    
    public FileProperty(File file){
        this.file = file;
        this.name = new SimpleStringProperty(file.getName());
        this.path = new SimpleStringProperty(file.getPath());
        this.status = new SimpleStringProperty("Wainting for parser");
    }     
    
    public StringProperty getNameProperty(){
        return name;
    }
    
    public StringProperty getPathProperty(){
        return path;
    }
    
    public File getFile(){
        return file;
    }
    
	public StringProperty getStatusProperty() {
		return status;
	}
	
	public void setStatus(String status){
		this.status = new SimpleStringProperty(status);
	}
	
    @Override
    public String toString() {
        return name.getValue();
    }
    
    public static List<FileProperty> fileToFileProperty(List<File> files){
    	if(files == null || files.size() == 0)
    		return null;
    	List<FileProperty> properties = new ArrayList<>();    	
    	files.forEach(f -> properties.add(new FileProperty(f)));    	
    	return properties;
    }
    
    public static List<File> filePropertyToFile(List<FileProperty> filesProperties){
    	List<File> files = new ArrayList<>();
    	filesProperties.forEach(f -> files.add(f.getFile()));
    	return files;
    }
}