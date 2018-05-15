package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class Consumer extends Character {

    private SynchronizedBuffer sharedLocation;

    public Consumer(SynchronizedBuffer shared, int x, int y) throws FileNotFoundException {
        super(x, y);
        this.sharedLocation = shared;
        setSprite();
    } // constructor

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 0; i < 7; i++) {
            sprite.add(new Image(new FileInputStream("src/assets/consumidor" + i + ".png")));
        }
    } // setSprite

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        int movimiento = 1200;
        int bandera = 0, sum = 0, image = 1;
        int ram = ThreadLocalRandom.current().nextInt(1, 20);

        while (true) {
            try {
                this.sleep(ram);
                super.setX(movimiento);
                if (movimiento >= 600 && bandera == 0) {
                    movimiento -= 1;
                    if (image == 4) {
                        image = 1;
                    }
                    super.setImage(sprite.get(image));
                    image++;
                    if (movimiento == 600) {
                        bandera = 1;
                        super.setImage(sprite.get(0));
                        sum += this.sharedLocation.get();
                        this.sharedLocation.setPaint(false);
                        image = 4;
                    }
                } else if (movimiento <= 1200 && bandera == 1) {
                    movimiento += 1;
                    if (image == 7) {
                        image = 4;
                    }
                    super.setImage(sprite.get(image));
                    image++;
                    if (movimiento == 1200) {
                        bandera = 0;
                        ram = ThreadLocalRandom.current().nextInt(1, 20);
                        System.out.println("Consumer: " + ram);
                        image = 1;
                    }
                }
            } catch (InterruptedException ex) {
            }
        } // while
    } //  run

} // fin de la clase
