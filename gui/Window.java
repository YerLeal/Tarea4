package gui;

import domain.Consumer;
import domain.Producer;
import domain.Product;
import domain.SynchronizedBuffer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Window extends Application implements Runnable {

    private Thread thread;
    private Pane pane;
    private Scene scene;
    private Canvas canvas;
    private Image fondo;

    private Producer producer;
    private Consumer consumer;
    private Product product;

    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tarea 4 - Yerlin Leal");
        initComponents(primaryStage);
        primaryStage.setOnCloseRequest(exit);
        primaryStage.show();
    } // start

    private void initComponents(Stage primaryStage) {
        try {
            this.pane = new Pane();
            this.scene = new Scene(this.pane, 1200, 700);
            this.canvas = new javafx.scene.canvas.Canvas(1200, 700);
            this.gc = this.canvas.getGraphicsContext2D();
            this.fondo = new Image(new FileInputStream("src/assets/fondo.jpg"));
            this.pane.getChildren().add(this.canvas);
            primaryStage.setScene(this.scene);

            SynchronizedBuffer sharedLocation = new SynchronizedBuffer();

            this.producer = new Producer(sharedLocation, -50, 575);
            this.consumer = new Consumer(sharedLocation, 1200, 555);
            this.product = new Product(sharedLocation, 0, 595);

            this.producer.start();
            this.consumer.start();
            this.product.start();

            this.thread = new Thread(this);
            this.thread.start();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // initComponents

    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };

    @Override
    public void run() {
        long start;
        long elapsed;
        long wait;
        int fps = 20;
        long time = 1000 / fps;

        while (true) {
            try {
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                Thread.sleep(wait);

                draw(this.gc);
            } catch (InterruptedException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } // run

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 1200, 700);
        gc.drawImage(this.fondo, 0, 0, 1200, 700);
        gc.drawImage(this.producer.getImage(), this.producer.getX(), this.producer.getY(), 60, 60);
        gc.drawImage(this.consumer.getImage(), this.consumer.getX(), this.consumer.getY(), 50, 80);
        gc.drawImage(this.product.getImage(), this.product.getX(), this.product.getY(), 40, 40);
    } // draw

} // fin de la clase
