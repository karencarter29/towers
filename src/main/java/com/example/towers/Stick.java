package com.example.towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.towers.TowersApplication.*;

@Slf4j
public class Stick extends ImageView {
    public static final int MAX_AMOUNT_OF_BAGELS = 8;

    private int amountOfBagels;
    private final Deque<Bagel> bagelStack = new ArrayDeque<>();

    public Stick(double x, double y) throws FileNotFoundException {
        this.setImage(new Image(new FileInputStream("src\\main\\resources\\images\\stick.png")));
        this.setX(x);
        this.setY(y);
        this.setFitHeight(265);
        this.setFitWidth(183);
        setMouseActionForStick(this);
    }

    public boolean addBagel(Bagel bagel) {
        if (bagelStack.peekFirst() == null || bagelStack.peekFirst().getNumber() > bagel.getNumber()) {
            turnOffWarning();
            bagelStack.addFirst(bagel);
            amountOfBagels++;
            bagel.setX(this.getX() + this.getFitWidth() / 2 - bagel.getFitWidth() / 2);
            bagel.setY(this.getY() + 30 + (MAX_AMOUNT_OF_BAGELS - amountOfBagels) * 25);
            emptyEventObject();
            return true;
        } else {
            turnOnWarning();
            log.info("You cannot put bigger bagel on smaller one");
            return false;
        }
    }

    public Bagel removeBagel() {
        amountOfBagels--;
        Bagel removedBagel;
        try {
            removedBagel = bagelStack.removeFirst();
        } catch (NoSuchElementException e) {
            log.warn("You cannot remove bagel from empty stick!");
            amountOfBagels++;
            return null;
        }
        removedBagel.setX(WINDOW_WIDTH / 2 - removedBagel.getFitWidth() / 2);
        removedBagel.setY(80);
        return removedBagel;
    }
    
    public int getAmountOfBagels() {
        return amountOfBagels;
    }

    private static void setMouseActionForStick(Stick stick) {
        stick.setOnMouseClicked(mouseEvent -> {
            Optional<Bagel> eventObject = getEventObject();
            if (eventObject.isPresent()) {
                if (stick.addBagel(eventObject.get())) {
                    increaseSteps();
                }
                STEPS_LABEL.setText("Кроки: " + getSteps());
            } else {
                setEventObject(stick.removeBagel());
            }
        });
    }
}
