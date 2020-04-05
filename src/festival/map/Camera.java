package festival.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class Camera {
	private Point2D centerPoint;
	private double zoom;
	private double rotation;
	private Point2D lastMousePos;
	private Canvas canvas;
	private Resizable resizable;
	private FXGraphics2D graphics;

	public Camera(Canvas canvas, Resizable resizable, FXGraphics2D graphics) {
		this.canvas = canvas;
		this.resizable = resizable;
		this.graphics = graphics;
		this.centerPoint = new Point2D.Double(0,0);
		this.zoom = 1;
		this.rotation = 0;

		canvas.setOnMousePressed(e -> {
			this.lastMousePos = new Point2D.Double(e.getX(), e.getY());
		});
		canvas.setOnMouseDragged(e -> mouseDragged(e));

		canvas.setOnScroll(e-> mouseScroll(e));
	}



	public AffineTransform getTransform(int windowWidth, int windowHeight)  {
		AffineTransform tx = new AffineTransform();
		tx.translate(windowWidth/2, windowHeight/2);
		//sclaes with the zoom
		tx.scale(this.zoom, this.zoom);
		//translates with the drag
		tx.translate(this.centerPoint.getX(), this.centerPoint.getY());
		tx.rotate(this.rotation);
		return tx;
	}

	public void mouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.SECONDARY) {
			this.centerPoint = new Point2D.Double(
					this.centerPoint.getX() - (this.lastMousePos.getX() - e.getX()) / this.zoom,
					this.centerPoint.getY() - (this.lastMousePos.getY() - e.getY()) / this.zoom
			);
			this.lastMousePos = new Point2D.Double(e.getX(), e.getY());

			this.resizable.draw(this.graphics);
		}
	}

	public void mouseScroll(ScrollEvent e) {
		this.resizable.draw(this.graphics);
		this.resizable.draw(this.graphics);
		//camera can't zoom further then 0.16
		if (this.zoom < 0.16){
			//scroll down will set the zoom to 0.16 if zoom is lesser than 0.16
			if(e.getDeltaY() > 0){
				this.zoom = 0.16;
			}

		//camera can't zoom closer than 2
		} else if (this.zoom > 2){
			//scroll up will set the zoom to 2 if the zoom is greater than 2
			if(e.getDeltaY() < 0) {
				this.zoom = 2;
			}

		} else {
			this.zoom *= (1 + e.getDeltaY() / 250.0f);
		}
//		System.out.println(this.zoom);


	}

	public double getZoom() {
		return zoom;
	}
}
