package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BezierPath {
	final DoubleProperty from = new SimpleDoubleProperty(0.0d);
	final DoubleProperty to = new SimpleDoubleProperty(1.0d);
	Point2D p1;
	Point2D p2;
	Point2D p3;
	Point2D p4;
	double step = 0.02d;
	
	public BezierPath(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}
	
	private double ofX(double t) {
		double it = 1.0d - t;
		return t * t * t * p4.getX() +
			3 * t * t * it * p3.getX() +
			3 * t * it * it * p2.getX() +
			it * it * it * p1.getX();
	}
	
	private double ofY(double t) {
		double it = 1.0d - t;
		return t * t * t * p4.getY() +
			3 * t * t * it * p3.getY() +
			3 * t * it * it * p2.getY() +
			it * it * it * p1.getY();
	}
	
	public void draw(GraphicsContext gc, Color color, double width) {
//		gc.setStroke(Color.RED);
//		gc.setLineWidth(1.0d);
//		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
//		gc.setStroke(Color.BLUE);
//		gc.strokeLine(p3.getX(), p3.getY(), p4.getX(), p4.getY());
		
		gc.setStroke(color);
		gc.setLineWidth(width);
		double t2 = 0.0d;
		for (double t = from.get(); t < to.get(); t += step) {
			t2 = Math.min(t + step, to.get());
			gc.strokeLine(ofX(t), ofY(t), ofX(t2), ofY(t2));
		}
	}
	
	public double getFrom() { return from.get(); }
	public void setFrom(double v) { from.set(Math.max(0.0d, Math.min(1.0d, v))); }
	public DoubleProperty fromProperty() { return from; }
	
	public double getTo() { return to.get(); }
	public void setTo(double v) { to.set(Math.max(0.0d, Math.min(1.0d, v))); }
	public DoubleProperty toProperty() { return to; }
	
}
