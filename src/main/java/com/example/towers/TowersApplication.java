package com.example.towers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.*;

import static com.example.towers.TimeCount.createTimeCount;
import static com.example.towers.TimeCount.stopTimer;

public class TowersApplication extends Application {
    public static final Integer MIN_STEPS = 255;

    private static final double WINDOW_HEIGHT = 500;
    public static final double WINDOW_WIDTH = 800;
    private static Integer steps = 0;

    public static final Label STEPS_LABEL = new Label("Кроки: 0");
    public static final Label WARNING = new Label("Не можна класти більше кільце на меньше");

    private static Bagel eventObject;
    private static Stick thirdStick;

    private final Bagel first = new Bagel("src\\main\\resources\\images\\1.png", 1, 45);
    private final Bagel second = new Bagel("src\\main\\resources\\images\\2.png", 2, 65);
    private final Bagel third = new Bagel("src\\main\\resources\\images\\3.png", 3, 85);
    private final Bagel fourth = new Bagel("src\\main\\resources\\images\\4.png", 4, 100);
    private final Bagel fifth = new Bagel("src\\main\\resources\\images\\5.png", 5, 120);
    private final Bagel sixth = new Bagel("src\\main\\resources\\images\\6.png", 6, 140);
    private final Bagel seventh = new Bagel("src\\main\\resources\\images\\7.png", 7, 160);
    private final Bagel eighth = new Bagel("src\\main\\resources\\images\\8.png", 8, 180);

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        List<Node> objects = new ArrayList<>(generateObjects());
        Scene scene = new Scene(new Group(objects), WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.rgb(247, 201, 247));
        stage.setTitle("Ханойські вежі");
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> stopTimer());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private List<Node> generateObjects() throws FileNotFoundException {
        Stick stick1 = new Stick(50, 150);
        Stick stick2 = new Stick(WINDOW_WIDTH / 2 - stick1.getFitWidth() / 2, 150);
        stick1.setX(stick2.getX() - stick1.getFitWidth() - 50);
        Stick stick3 = new Stick(stick2.getX() + stick1.getFitWidth() + 50, 150);
        setThirdStick(stick3);

        stick1.addBagel(eighth);
        stick1.addBagel(seventh);
        stick1.addBagel(sixth);
        stick1.addBagel(fifth);
        stick1.addBagel(fourth);
        stick1.addBagel(third);
        stick1.addBagel(second);
        stick1.addBagel(first);

        STEPS_LABEL.relocate(20, 20);
        STEPS_LABEL.setFont(new Font("Arial", 20));
        WARNING.setFont(new Font("Arial", 20));
        WARNING.relocate(50, 450);
        turnOffWarning();

        Label timerLabel = createTimeCount();

        return new ArrayList<>(List.of(stick1, stick2, stick3, first, second, third, fourth,
                               fifth, sixth, seventh, eighth, STEPS_LABEL, WARNING, timerLabel));
    }

    public static void increaseSteps() {
        steps++;
    }

    public static Integer getSteps() {
        return steps;
    }

    public static Optional<Bagel> getEventObject() {
        return Optional.ofNullable(eventObject);
    }

    public static void setEventObject(Bagel bagel) {
        eventObject = bagel;
    }

    public static void emptyEventObject() {
        eventObject = null;
    }

    public static void setThirdStick(Stick stick) {
        thirdStick = stick;
    }

    public static Stick getThirdStick() {
        return thirdStick;
    }

    public static void turnOnWarning() {
        WARNING.setOpacity(1);
    }

    public static void turnOffWarning() {
        WARNING.setOpacity(0);
    }
}