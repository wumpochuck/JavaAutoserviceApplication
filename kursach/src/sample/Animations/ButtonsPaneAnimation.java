package sample.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ButtonsPaneAnimation {
    public static void moveRight(AnchorPane buttonsPane){
        TranslateTransition RegToRight = new TranslateTransition(Duration.seconds(0.5), buttonsPane);
        RegToRight.setToX(0);
        RegToRight.play();
    }

    public static void moveLeft(AnchorPane buttonsPane){
        TranslateTransition RegToRight = new TranslateTransition(Duration.seconds(0.5), buttonsPane);
        RegToRight.setToX(275);
        RegToRight.play();
    }
}
