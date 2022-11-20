package com.example.towers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
public class Bagel extends ImageView {

    private final int number;

    public Bagel(String filepath, int number, double fitWidth) {
        try {
            Image image = new Image(new FileInputStream(filepath));
            this.setImage(image);
            this.setFitHeight(25);
            this.setFitWidth(fitWidth);
        } catch (FileNotFoundException e) {
            log.warn(e.getMessage());
        }
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
