package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BezierAnimator {
	
	BezierPath bezier1, bezier2;
	Color color;
	double width = 8.0d;
	
	public BezierAnimator() {
		bezier1 = initialRandBezier();
		bezier2 = nextRandBezier(bezier1);
		color = new Color(Math.random() * 0.7d + 0.3d, Math.random() * 0.7d + 0.3d, Math.random() * 0.7d + 0.3d, 1.0d);
	}

	public void animate() {
		Timeline animation = new Timeline(
				new KeyFrame(new Duration(0.0d), new KeyValue(bezier1.fromProperty(), 0.0d)),
				new KeyFrame(new Duration(500.d), new KeyValue(bezier1.fromProperty(), 1.0d)),
				new KeyFrame(new Duration(0.0d), new KeyValue(bezier2.toProperty(), 0.0d)),
				new KeyFrame(new Duration(500.d), new KeyValue(bezier2.toProperty(), 1.0d))
				);
		animation.setOnFinished(event -> {
			animation.stop();
			bezier1 = bezier2;
			bezier2 = nextRandBezier(bezier1);
			bezier2.setTo(0.0d);
			animate();
		});
		animation.playFromStart();
	}

	public void draw(GraphicsContext gc) {
		bezier1.draw(gc, color, width);
		bezier2.draw(gc, color, width);
	}
	
	private Point2D getRandPoint() {
		return new Point2D(Math.random() * 400.0d, Math.random() * 400.0d);
	}

	private BezierPath initialRandBezier() {
		Point2D p3 = getRandPoint();
		Point2D p4 = getRandPoint();
		p4 = p4.add((p3.getX()-p4.getX()) / 2, (p3.getY()-p4.getY()) / 2);
		return new BezierPath(getRandPoint(), getRandPoint(), p3, p4);
	}
	
	private BezierPath nextRandBezier(BezierPath base) {
		Point2D p2 = base.p4.add(base.p4.subtract(base.p3));
		Point2D p3 = getRandPoint();
		Point2D p4 = getRandPoint();
		p4 = p4.add((p3.getX()-p4.getX()) / 2, (p3.getY()-p4.getY()) / 2);
		return new BezierPath(base.p4, p2, p3, p4);
	}

}
