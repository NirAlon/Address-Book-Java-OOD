
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Q1 extends Application {

	private OriginatorColorCircle c = new OriginatorColorCircle(100);

	public Pane createPane() {
		Stack<OriginatorColorCircle.Memento> stack = new Stack<>();
		Button b1 = new Button("Color");
		b1.setOnAction(e -> {
			stack.push(c.saveToMemento());
			c.changeToRndColor();
		});
		Button b2 = new Button("Undo");
		b2.setOnAction(e -> {
			if (!stack.isEmpty())
				c.restoreFromMemento(stack.pop());
		});
		HBox box = new HBox(20, b1, b2);
		box.setPadding(new Insets(30));
		StackPane pane = new StackPane(c, box);
		return pane;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = createPane();
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.setTitle("TarKita");
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
