package com.example.towers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.towers.Stick.MAX_AMOUNT_OF_BAGELS;
import static com.example.towers.TowersApplication.*;

public class TimeCount {

    private TimeCount() {}

    private static final Label timerLabel = new Label("Час: ");
    private static final Timer timer = new Timer();

    public static Label createTimeCount() {
        timerLabel.setFont(new Font("Arial", 20));
        timerLabel.relocate(150, 20);

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 0;
            String text;

            public void run() {
                text = "Час: ";
                int min = i / 60;
                int sec = i - (min * 60);
                if (min < 10) {
                    text += "0" + min + ":";
                } else {
                    text += min + ":";
                }
                if (sec < 10) {
                    text += "0" + sec;
                } else {
                    text += sec;
                }
                Platform.runLater(() -> timerLabel.setText(text));
                i++;
                if (getThirdStick().getAmountOfBagels() == MAX_AMOUNT_OF_BAGELS) {
                    stopTimer();
                    if (MIN_STEPS.equals(getSteps())) {
                        Platform.runLater(() -> timerLabel.setText(text + "  Ви виграли!"));
                    } else {
                        Platform.runLater(() -> timerLabel.setText(text + "  Ви виконали завдання але програли. Забагато кроків :("));
                    }
                }
            }
        }, 0, 1000);
        return timerLabel;
    }

    public static void stopTimer() {
        timer.cancel();
    }
}
