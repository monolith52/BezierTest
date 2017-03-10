package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ApplicationController {

	@FXML private Canvas canvas;
	
	List<BezierAnimator> beziers = new ArrayList<>();
	
	@FXML
    void initialize() {
		IntStream.range(0, 10).forEach(i -> beziers.add(new BezierAnimator()));
		new Thread(this::run).start();
		beziers.forEach(bezier -> bezier.animate());
	}
	
	public void run() {
		while (true) {
			draw();
			try { Thread.sleep(1000 / 60); } catch (InterruptedException e) {}
		}
	}
	
	public void draw() {
		Platform.runLater(() -> {
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.WHITE);
			gc.fillRect(0.0d, 0.0d, canvas.getWidth(), canvas.getHeight());
			beziers.forEach(bezier -> bezier.draw(gc));
		});
	}
}
