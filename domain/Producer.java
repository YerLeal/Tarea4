package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import utility.Buffer;

public class Producer extends Character {

    private Buffer sharedLocation;

    public Producer(Buffer shared, int x, int y) throws FileNotFoundException {
        super(x, y);
        this.sharedLocation = shared;
        setSprite();
    } // constructor

    private void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        for (int i = 0; i < 5; i++) {
            sprite.add(new Image(new FileInputStream("src/assets/productor" + i + ".png")));
        }
    } // setSprite

    @Override
    public void run() {
        ArrayList<Image> sprite = super.getSprite();
        int bandera = 1, image = 1, movimiento = -50;
        int ram = ThreadLocalRandom.current().nextInt(1, 20);
        while (true) {
            try {
                this.sleep(ram);
                if (movimiento >= -50 && bandera == 0) {
                    movimiento -= 2;
                    if (image == 5) {
                        image = 3;
                    }
                    super.setImage(sprite.get(image));
                    image++;
                    if (movimiento == -50) {
                        bandera = 1;
                        ram = ThreadLocalRandom.current().nextInt(1, 20);
                        System.out.println("Producer: " + ram);
                        image = 1;
                    }
                    super.setX(movimiento);
                } else if (movimiento <= 490 && bandera == 1) {
                    movimiento += 2;
                    if (image == 3) {
                        image = 1;
                    }
                    super.setImage(sprite.get(image));
                    image++;
                    if (movimiento == 490) {
                        bandera = 0;
                        super.setImage(sprite.get(0));
                        this.sharedLocation.set(image);
                        image = 3;
                    }
                    super.setX(movimiento);
                }
            } catch (InterruptedException ex) {
            }
        } // while
    } // run

} // fin de la clase

