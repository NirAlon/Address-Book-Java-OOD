import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OriginatorColorCircle extends Circle {
	private Color prevColor;

	public void changeToRndColor() {
		this.setFill(Color.color(Math.random(), Math.random(), Math.random()));
	}

	public OriginatorColorCircle(double radius) {
		super(radius);
	}

	public Memento saveToMemento() {
		prevColor = (Color) this.getFill();
		return new Memento(prevColor);
	}

	public void restoreFromMemento(Memento m) {
		prevColor = m.getPrevColor();
		this.setFill(prevColor);
	}

	// -------------------------------------------
	/** Class Memento */

	public class Memento {
		private Color prevColor;

		private Memento(Color prevColor) {
			this.prevColor = prevColor;
		}

		private Color getPrevColor() {
			return prevColor;
		}
	}
}
