import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AddressBookNew1 extends Application implements AddressBookNew1Finals {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AddressBookPane pane = new AddressBookPane();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(STYLES_CSS);
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}
}

class AddressBookPane extends GridPane implements AddressBookNew1Finals, AddressBookEvent1 {
	private RandomAccessFile raf;
	private TextField jtfName = new TextField();
	private TextField jtfStreet = new TextField();
	private TextField jtfCity = new TextField();
	private TextField jtfState = new TextField();
	private TextField jtfZip = new TextField();
	private ObservableList<Integer> ob = FXCollections.observableArrayList();//למה בלי new????
	private ComboBox<Integer> comboallAddress1 = new ComboBox<>(); // the comboboxes
	private ComboBox<Integer> comboallAddress2 = new ComboBox<>();
	private SwitchButton switchCB;
	private AddButton jbtAdd;
	private FirstButton jbtFirst;
	private NextButton jbtNext;
	private PreviousButton jbtPrevious;
	private LastButton jbtLast;
	private ClearButton jbtClear;
	private ReverseButton jbtReverse;
	private int numcontacts=0;
	// private EventHandler<ActionEvent> ae =
	// e -> ((Command) e.getSource()).Execute();
	private EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
			((Command) arg0.getSource()).Execute();
		}
	};

	public AddressBookPane() throws IOException {
		
		try {
			raf = new RandomAccessFile(FILE_NAME, FILE_MODE);
		} catch (IOException ex) {
			System.out.println(ex);
			System.exit(0);
		}
		
		updatList();
		
		
		jtfState.setAlignment(Pos.CENTER_LEFT);
		jtfState.setPrefWidth(25);
		jtfZip.setPrefWidth(60);
		jbtAdd = new AddButton(this, raf);
		jbtFirst = new FirstButton(this, raf);
		jbtNext = new NextButton(this, raf);
		jbtPrevious = new PreviousButton(this, raf);
		jbtLast = new LastButton(this, raf);
		jbtClear = new ClearButton(this, raf);
		jbtReverse = new ReverseButton(this, raf);
		switchCB = new SwitchButton(this, raf);		
		Label state = new Label(STATE);
		Label zp = new Label(ZIP);
		Label name = new Label(NAME);
		Label street = new Label(STREET);
		Label city = new Label(CITY);
		GridPane p1 = new GridPane();
		p1.add(name, 0, 0);
		p1.add(street, 0, 1);
		p1.add(city, 0, 2);
		p1.setAlignment(Pos.CENTER_LEFT);
		p1.setVgap(8);
		p1.setPadding(new Insets(0, 2, 0, 2));
		GridPane.setVgrow(name, Priority.ALWAYS);
		GridPane.setVgrow(street, Priority.ALWAYS);
		GridPane.setVgrow(city, Priority.ALWAYS);
		GridPane adP = new GridPane();
		adP.add(jtfCity, 0, 0);
		adP.add(state, 1, 0);
		adP.add(jtfState, 2, 0);
		adP.add(zp, 3, 0);
		adP.add(jtfZip, 4, 0);
		adP.setAlignment(Pos.CENTER_LEFT);
		GridPane.setHgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfCity, Priority.ALWAYS);
		GridPane.setVgrow(jtfState, Priority.ALWAYS);
		GridPane.setVgrow(jtfZip, Priority.ALWAYS);
		GridPane.setVgrow(state, Priority.ALWAYS);
		GridPane.setVgrow(zp, Priority.ALWAYS);
		GridPane p4 = new GridPane();
		p4.add(jtfName, 0, 0);
		p4.add(jtfStreet, 0, 1);
		p4.add(adP, 0, 2);
		p4.setVgap(1);
		GridPane.setHgrow(jtfName, Priority.ALWAYS);
		GridPane.setHgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setHgrow(adP, Priority.ALWAYS);
		GridPane.setVgrow(jtfName, Priority.ALWAYS);
		GridPane.setVgrow(jtfStreet, Priority.ALWAYS);
		GridPane.setVgrow(adP, Priority.ALWAYS);
		GridPane jpAddress = new GridPane();
		jpAddress.add(p1, 0, 0);
		jpAddress.add(p4, 1, 0);
		GridPane.setHgrow(p1, Priority.NEVER);
		GridPane.setHgrow(p4, Priority.ALWAYS);
		GridPane.setVgrow(p1, Priority.ALWAYS);
		GridPane.setVgrow(p4, Priority.ALWAYS);
		jpAddress.setStyle(STYLE_COMMAND);
		FlowPane jpButton = new FlowPane();
		jpButton.setHgap(5);
		if (eventType.ADD.getDoEvent())
			jpButton.getChildren().add(jbtAdd);
		if (eventType.FIRST.getDoEvent())
			jpButton.getChildren().add(jbtFirst);
		if (eventType.NEXT.getDoEvent())
			jpButton.getChildren().add(jbtNext);
		if (eventType.PREVIOUS.getDoEvent())
			jpButton.getChildren().add(jbtPrevious);
		if (eventType.LAST.getDoEvent())
			jpButton.getChildren().add(jbtLast);
		if (eventType.CLEAR.getDoEvent())
			jpButton.getChildren().add(jbtClear);
		if (eventType.REVERSE.getDoEvent())
			jpButton.getChildren().add(jbtReverse);
		jpButton.getChildren().addAll(comboallAddress1,comboallAddress2,switchCB);
		jpButton.setAlignment(Pos.CENTER);
		GridPane.setVgrow(jpButton, Priority.NEVER);
		GridPane.setVgrow(jpAddress, Priority.ALWAYS);
		GridPane.setHgrow(jpButton, Priority.ALWAYS);
		GridPane.setHgrow(jpAddress, Priority.ALWAYS);
		this.setVgap(5);
		this.add(jpAddress, 0, 0);
		this.add(jpButton, 0, 1);
		jbtAdd.setOnAction(ae);
		jbtFirst.setOnAction(ae);
		jbtNext.setOnAction(ae);
		jbtPrevious.setOnAction(ae);
		jbtLast.setOnAction(ae);
		jbtClear.setOnAction(ae);
		jbtReverse.setOnAction(ae);
		switchCB.setOnAction(ae);
		jbtFirst.Execute();
	}
	

	
	public void updatList() throws IOException {
		numcontacts=0;
		for(int i=0;i<(int)(raf.length()/(2*RECORD_SIZE));i++){
			ob.add(i);
			numcontacts++;
		}
		comboallAddress1.getItems().addAll(ob);
		comboallAddress2.getItems().addAll(ob);
	}



	public void SetName(String text) {
		jtfName.setText(text);
	}

	public void SetStreet(String text) {
		jtfStreet.setText(text);
	}

	public void SetCity(String text) {
		jtfCity.setText(text);
	}

	public void SetState(String text) {
		jtfState.setText(text);
	}

	public void SetZip(String text) {
		jtfZip.setText(text);
	}

	public String GetName() {
		return jtfName.getText();
	}

	public String GetStreet() {
		return jtfStreet.getText();
	}

	public String GetCity() {
		return jtfCity.getText();
	}

	public String GetState() {
		return jtfState.getText();
	}

	public String GetZip() {
		return jtfZip.getText();
	}

	public void clearTextFields() {
		jtfName.setText("");
		jtfStreet.setText("");
		jtfCity.setText("");
		jtfState.setText("");
		jtfZip.setText("");
	}
	
	
	public void incriceList(){
		comboallAddress1.getItems().add(numcontacts);
		comboallAddress2.getItems().add(numcontacts++);

	}
	public void delitFromList(){
		comboallAddress1.getItems().remove(numcontacts);
		comboallAddress2.getItems().remove(numcontacts);

	}
	
	public int ShowFromList(){
		return comboallAddress1.getValue();
	}
	
	public void switchCB(){
		if(comboallAddress1.getValue()!=null&&comboallAddress2.getValue()!=null){
		int select1 = comboallAddress1.getValue();
		int select2 = comboallAddress2.getValue();
		
		comboallAddress1.getItems().remove(select1);
		comboallAddress2.getItems().remove(select2);
		comboallAddress1.getItems().add(select2, select2);
		comboallAddress2.getItems().add(select1, select1);
		}
	}
	
	public ComboBox<?> getComboBox1(){
		return comboallAddress1;
	}
	
	public ComboBox<?> getComboBox2(){
		return comboallAddress2;
	}



	public void clearComboBoxes() {
		ob.clear();
		comboallAddress1.setItems(ob);
		comboallAddress2.setItems(ob);
		numcontacts=0;
	}
}

interface Command {
	public void Execute();
}

class CommandButton extends Button implements Command, AddressBookNew1Finals {
	private AddressBookPane p;
	private RandomAccessFile raf;

	public CommandButton(AddressBookPane pane, RandomAccessFile r) {
		super();
		p = pane;
		raf = r;
	}

	public AddressBookPane getPane() {
		return p;
	}

	public RandomAccessFile getFile() {
		return raf;
	}

	public void setPane(AddressBookPane p) {
		this.p = p;
	}

	@Override
	public void Execute() {
	}

	public void writeAddress(long position) {
		try {
			getFile().seek(position);
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetName(), NAME_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetStreet(), STREET_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetCity(), CITY_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetState(), STATE_SIZE, getFile());
			FixedLengthStringIO1.writeFixedLengthString(getPane().GetZip(), ZIP_SIZE, getFile());
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void readAddress(long position) throws IOException {
		getFile().seek(position);
		String name = FixedLengthStringIO1.readFixedLengthString(NAME_SIZE, getFile());
		String street = FixedLengthStringIO1.readFixedLengthString(STREET_SIZE, getFile());
		String city = FixedLengthStringIO1.readFixedLengthString(CITY_SIZE, getFile());
		String state = FixedLengthStringIO1.readFixedLengthString(STATE_SIZE, getFile());
		String zip = FixedLengthStringIO1.readFixedLengthString(ZIP_SIZE, getFile());
		getPane().SetName(name);
		getPane().SetStreet(street);
		getPane().SetCity(city);
		getPane().SetState(state);
		getPane().SetZip(zip);
	}
}

class AddButton extends CommandButton {
	public AddButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(ADD);
	}

	@Override
	public void Execute() {
		try {
			writeAddress(getFile().length());
			getPane().incriceList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class SwitchButton extends CommandButton{

	public SwitchButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(SWITCH);
	}
	@Override
	public void Execute() {
		try {
			long temp = getFile().length();
			
			int select1 = (int) getPane().getComboBox1().getValue();
			int select2 = (int) getPane().getComboBox2().getValue();
			
			readAddress(select1*2*RECORD_SIZE);
			writeAddress(temp);
			
			readAddress(select2 * 2 * RECORD_SIZE);
			writeAddress(select1 * 2 * RECORD_SIZE);
			
			readAddress(temp);
			writeAddress(select2*2*RECORD_SIZE);
			
			getFile().setLength(temp);
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}

class NextButton extends CommandButton {
	public NextButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(NEXT);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer();
			if (currentPosition < getFile().length())
				readAddress(currentPosition);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class PreviousButton extends CommandButton {
	public PreviousButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(PREVIOUS);
	}

	@Override
	public void Execute() {
		try {
			long currentPosition = getFile().getFilePointer();
			if (currentPosition - 2 * 2 * RECORD_SIZE >= 0)
				readAddress(currentPosition - 2 * 2 * RECORD_SIZE);
			else
				;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class LastButton extends CommandButton {
	public LastButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(LAST);
	}

	@Override
	public void Execute() {
		try {
			long lastPosition = getFile().length();
			if (lastPosition > 0)
				readAddress(lastPosition - 2 * RECORD_SIZE);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class FirstButton extends CommandButton {
	public FirstButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(FIRST);
	}

	@Override
	public void Execute() {
		try {
			if (getFile().length() > 0)
				readAddress(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

class ClearButton extends CommandButton {
	public ClearButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(CLEAR);
	}

	@Override
	public void Execute() {
		try {
			getFile().setLength(0);
			//getPane().updatList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getPane().clearTextFields();
		getPane().clearComboBoxes();
	}
}

class ReverseButton extends CommandButton {
	public ReverseButton(AddressBookPane pane, RandomAccessFile r) {
		super(pane, r);
		this.setText(REVERSE);
	}

	@Override
	public void Execute() {
		try {
			long originalRafLength = getFile().length();
			long numberOfRecords = (getFile().length()) / (2 * RECORD_SIZE);
			if (numberOfRecords <= 1)
				return;
			for (int i = 0; i < numberOfRecords / 2; i++) {
				readAddress(i * 2 * RECORD_SIZE);
				writeAddress(originalRafLength);
				readAddress(originalRafLength - (i + 1) * 2 * RECORD_SIZE);
				writeAddress(i * 2 * RECORD_SIZE);
				readAddress(originalRafLength);
				writeAddress(originalRafLength - (i + 1) * 2 * RECORD_SIZE);
				getFile().setLength(originalRafLength);
			}
			readAddress(0);
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
