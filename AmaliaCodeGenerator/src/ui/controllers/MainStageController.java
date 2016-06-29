package ui.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import exceptions.ParserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.Cake;
import main.Project;
import main.Utils;
import structure.domain_model.DomainModel;
import structure.user_interface.InteractionSpace;
import ui.properties.CodeTypeProperty;
import ui.properties.FileProperty;
import ui.properties.InteractionSpaceProperty;
import xml.parser.DomainModelXMLParserJDOM;
import xml.parser.InteractionSpaceBuilder;
import xtend_templates.rubyonrails.RubyScriptTemplate;

public class MainStageController extends Parent implements Initializable {
	// Objects in the FXML document
	@FXML
	private Tab tabDomainModel;
	@FXML
	private Button btChooseDomainModel;
	@FXML
	private Button btLoadDomainModel;
	@FXML
	private TextField tfDomainModelFilePath;
	@FXML
	private TextArea taOutputDM;
	@FXML
	private Tab tabUserInterface;
	@FXML
	private Button btChooseUserInterfaceModels;
	@FXML
	private Button btLoadUserInterface;
	@FXML
	private TableView<FileProperty> tvUIfiles;
	@FXML
	private TableColumn<FileProperty, String> tcFileName;
	@FXML
	private TableColumn<FileProperty, String> tcPath;
	@FXML
	private TextArea taOutputUI;
	@FXML
	private Tab tabGenerateScript;
	@FXML
	private Text lbStatus;
	@FXML
	private MenuItem actionDeleteFile;
	@FXML
	private Button btGenerateScript;
	@FXML
	private TextArea taOutputScript;
	@FXML
	private Button btDownloadScript;
	@FXML
	private Menu menuFile;
	@FXML
	private MenuItem menuitemClose;
	@FXML
	private Menu menuHelp;
	@FXML
	private MenuItem menuitemAbout;
	@FXML
	private ComboBox<InteractionSpaceProperty> cbMainInteractionSpace;
	@FXML
	private Label lbChooseMainInteractionSpace;
	@FXML
	private Button btResetTable;
	@FXML
	private ComboBox<CodeTypeProperty> cbCodeType;
	@FXML
	private Label lbCodeType;

	private Stage mainStage;
	private ResourceBundle rb;
	private File selectedDMFile = null; // Selected Domain Model File
	private Project project;
	private ObservableList<FileProperty> uiFiles;
	private ObservableList<InteractionSpaceProperty> interactionSpaces;
	private ObservableList<CodeTypeProperty> codeType;
	private boolean interfaceRead = false; // Check if the user interface has already been read.
	private Cake cake;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.rb = resourceBundle;
		populateCodeType();
		btLoadDomainModel.setDisable(true);
		btChooseDomainModel.setOnAction(a -> openDMFile());
		btLoadDomainModel.setOnAction(e -> readDomainModel());
		btLoadUserInterface.setOnAction(a -> readUserInterface());
		btChooseUserInterfaceModels.setOnAction(e -> openUIFiles());
		btResetTable.setOnAction(action -> resetTable());
		actionDeleteFile.setOnAction(a -> deleteSelectedFile());
		btGenerateScript.setOnAction(a -> generateCode());
		project = new Project();
		uiFiles = FXCollections.observableArrayList();
		tcFileName.setCellValueFactory(dados -> dados.getValue().getNameProperty());
		tcPath.setCellValueFactory(dados -> dados.getValue().getPathProperty());
		btDownloadScript.setOnAction(a -> {
			String selectedCodeType = cbCodeType.getSelectionModel().getSelectedItem().getType().getValue();
			switch (selectedCodeType) {
			case "ruby":
				downloadCodeRuby();
				break;
			case "cakephp":
				downloadCodeCake();
				break;
			default:
				break;
			}
		});
		menuitemAbout.setOnAction(a -> Utils.alertBox(Alert.AlertType.INFORMATION, rb.getString("alert.about.title"),
				rb.getString("alert.about.content"), rb.getString("alert.about.header"), false));
		cbMainInteractionSpace.valueProperty().addListener((observable, oldValue, newValue) -> {
			cbInteractionSpaceUpdated();
		});
		
		cbCodeType.valueProperty().addListener((observable, oldValue, newValue) -> {
			cbCodeTypeUpdated();
		});
		
		// Close the application
		menuitemClose.setOnAction(e -> mainStage.close());
	}

	private void resetTable() {
		if (uiFiles != null)
			uiFiles.clear();
		taOutputUI.clear();
		tabGenerateScript.setDisable(true);
		btLoadUserInterface.setDisable(true);
		resetTabGenerateScript();
		statusMessage(rb.getString("status.user.interface.cleantable"), false);
	}

	private void resetTabGenerateScript() {
		taOutputScript.clear();
		btDownloadScript.setDisable(false);
		btGenerateScript.setDisable(false);
		cbMainInteractionSpace.getSelectionModel().select(0);
	}

	public void setUp(Stage stage) {
		this.mainStage = stage;
	}

	public void cbCodeTypeUpdated(){
		btDownloadScript.setDisable(true);
		taOutputScript.clear();		
	}
	
	public void cbInteractionSpaceUpdated() {
		InteractionSpaceProperty newValue = cbMainInteractionSpace.getSelectionModel().getSelectedItem();
		if (newValue.isDummy()) {
			btGenerateScript.setDisable(true);
			cbCodeType.setDisable(true);
		} else {
			btGenerateScript.setDisable(false);
			cbCodeType.setDisable(false);
		}

		btDownloadScript.setDisable(true);
		taOutputScript.clear();
	}

	public void populateInteractionSpaces() {
		// Initialize the Observable List
		interactionSpaces = FXCollections.observableArrayList();
		// Get all the Interaction Spaces without parameters
		List<InteractionSpace> is = project.getInteractionSpacesWithoutParams();
		if (is != null) {
			// Insert a dummy object
			interactionSpaces.add(new InteractionSpaceProperty(rb.getString("combobox.selectedall.text")));
			// Adiciona os graus ao array observavel como propriedades
			is.stream().forEach(g -> interactionSpaces.add(new InteractionSpaceProperty(g)));
		}
		cbMainInteractionSpace.getItems().setAll(interactionSpaces);

		// Seleciona a primeira opção
		cbMainInteractionSpace.getSelectionModel().selectFirst();
	}

	public void populateCodeType() {
		codeType = FXCollections.observableArrayList();
		codeType.addAll(new CodeTypeProperty(rb.getString("code.type.rubyonrails"), "ruby"),
				new CodeTypeProperty(rb.getString("code.type.cakephp"), "cakephp"));
		cbCodeType.getItems().setAll(codeType);
		cbCodeType.getSelectionModel().selectFirst();
	}

	private void downloadCodeRuby() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save script");
		fileChooser.setInitialFileName("script");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("text", "*.txt"),
				new ExtensionFilter("ruby file", "*.rb"));
		File savedFile = fileChooser.showSaveDialog(mainStage);

		if (savedFile != null) {
			try {
				// Creates a new file and writes the txtArea contents into it
				String txt = taOutputScript.getText();
				savedFile.createNewFile();
				FileWriter writer = new FileWriter(savedFile);
				writer.write(txt);
				writer.close();
			} catch (IOException e) {
				// e.printStackTrace();
				Utils.alertBox(Alert.AlertType.ERROR, rb.getString("alert.error.title"),
						rb.getString("status.save.file.error"), null, false);
				// statusMessage(rb.getString("status.save.file.error"), true);
				return;
			}

			statusMessage(rb.getString("status.file.saved") + ": " + savedFile.toString(), false);
		} else
			statusMessage(rb.getString("status.file.save.cancelled"), false);
	}

	private void downloadCodeCake() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Cake");
		fileChooser.setInitialFileName("cake");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("zip", "*.zip"));
		File savedFile = fileChooser.showSaveDialog(mainStage);

		if (savedFile != null) {
			try {
				cake.bakeCake(savedFile);
			} catch (IOException e) {
				// e.printStackTrace();
				Utils.alertBox(Alert.AlertType.ERROR, rb.getString("alert.error.title"),
						rb.getString("status.save.file.error"), null, false);
				return;
			}

			statusMessage(rb.getString("status.file.saved") + ": " + savedFile.toString(), false);
		} else
			statusMessage(rb.getString("status.file.save.cancelled"), false);
	}

	private void generateCode() {
		String selectedCodeType = cbCodeType.getSelectionModel().getSelectedItem().getType().getValue();
		switch (selectedCodeType) {
		case "ruby":
			generateCodeRuby();
			break;
		case "cakephp":
			generateCodeCakePHP();
			break;
		default:
			generateCodeRuby();
			break;
		}
	}

	private void generateCodeRuby() {
		RubyScriptTemplate t = new RubyScriptTemplate();
		project.setMainInteractionSpace(
				cbMainInteractionSpace.getSelectionModel().getSelectedItem().getInteractionSpace());
		CharSequence result = t.generate(project);
		taOutputScript.setText(result.toString());
		statusMessage(rb.getString("status.script.generated.ok"), false);
		btDownloadScript.setDisable(false);
	}

	private void generateCodeCakePHP() {
		cake = new Cake(project);
		taOutputScript.setText(cake.generate());
		statusMessage(rb.getString("status.script.generated.ok"), false);
		btDownloadScript.setDisable(false);
		
	}

	private void readUserInterface() {
		if (uiFiles.size() > 0) {
			if (interfaceRead)
				project.getInteractionSpaces().clear();
			else
				interfaceRead = true;
			InteractionSpaceBuilder builder = new InteractionSpaceBuilder(rb);
			taOutputUI.clear();
			try {
				builder.buildFile(project, FileProperty.filePropertyToFile(uiFiles));
				String info = "";
				for (InteractionSpace iSpace : project.getInteractionSpaces().values())
					info += iSpace.getInfo();
				taOutputUI.setText(info);
				tabGenerateScript.setDisable(false);
				populateInteractionSpaces();
				statusMessage(rb.getString("status.user.interface.load.ok"), false);
			} catch (ParserException e) {
				Utils.alertBox(Alert.AlertType.ERROR, rb.getString("alert.error.title"), e.getMessage(), null, false);
				// statusMessage(e.getMessage(), true);
				// e.printStackTrace();
			}
		} else
			statusMessage("No files selected", true);
	}

	/**
	 * Delete the file selected in the table view
	 */
	private void deleteSelectedFile() {
		FileProperty selected = tvUIfiles.getSelectionModel().getSelectedItem();
		if (selected != null) {
			statusMessage(selected.getNameProperty().getValue() + " " + rb.getString("status.user.interface.removed"),
					false);
			uiFiles.remove(selected);
			if (uiFiles.size() == 0)
				btResetTable.setDisable(true);
		}
	}

	/**
	 * Choose the user interface files
	 */
	private void openUIFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(rb.getString("filechooser.window.userinterface"));
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(rb.getString("filechooser.extensionfilter.amaliafiles"), "*.XML", "*.xml"));

		List<FileProperty> files = FileProperty.fileToFileProperty(fileChooser.showOpenMultipleDialog(mainStage));

		if (files != null && files.size() > 0) {
			uiFiles.addAll(files);
			tabGenerateScript.setDisable(true);
			taOutputUI.clear();
			btLoadUserInterface.setDisable(false);
			statusMessage(uiFiles.size() + " " + rb.getString("status.user.interface.quantity"), false);
			btResetTable.setDisable(false);
			System.out.println(uiFiles.size() + " user interface model files chosen");
			tvUIfiles.setItems(uiFiles);
		}
	}

	/**
	 * Open the file chooser so the user can choose a User Interface Model file
	 */
	private void openDMFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(rb.getString("filechooser.window.domainmodel"));
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(rb.getString("filechooser.extensionfilter.amaliafiles"), "*.XML", "*.xml"));
		selectedDMFile = fileChooser.showOpenDialog(mainStage);

		if (selectedDMFile != null) {
			resetStage();
			tfDomainModelFilePath.setText(selectedDMFile.getAbsolutePath());
			statusMessage(rb.getString("status.domainmodel.chosen") + ": " + selectedDMFile.getName(), false);
			System.out.println("File OK! Path: " + selectedDMFile.getAbsolutePath());
		}
	}

	private void resetStage() {
		tabUserInterface.setDisable(true);
		tabGenerateScript.setDisable(true);
		taOutputDM.clear();
		taOutputUI.clear();
		uiFiles.clear();
		btLoadDomainModel.setDisable(false);
	}

	private void readDomainModel() {
		if (selectedDMFile != null) {
			project = new Project();
			DomainModelXMLParserJDOM parser = new DomainModelXMLParserJDOM(rb);

			DomainModel dm;
			try {
				dm = parser.readXML(selectedDMFile.getAbsolutePath());
				taOutputDM.setText(dm.getInfo());
				tabUserInterface.setDisable(false);
				project.setDomainModel(dm);
				statusMessage(rb.getString("status.domainmodel.read.ok"), false);
			} catch (ParserException e) {
				Utils.alertBox(Alert.AlertType.ERROR, rb.getString("alert.error.title"), e.getMessage(), null, false);
			}
		} else
			Utils.alertBox(Alert.AlertType.ERROR, rb.getString("alert.error.title"),
					rb.getString("status.domainmodel.unrecognized"), null, false);
		// statusMessage(rb.getString("status.domainmodel.unrecognized"), true);
	}

	private void statusMessage(String message, boolean errorMode) {
		lbStatus.setText(message);
		if (errorMode)
			lbStatus.setFill(Color.RED);
		else
			lbStatus.setFill(Color.BLACK);
	}
}