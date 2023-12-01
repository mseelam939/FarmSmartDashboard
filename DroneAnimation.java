package application;

import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DroneAnimation {
    private ImageView droneImage;

    public DroneAnimation(ImageView droneImage) {
        this.droneImage = droneImage;
    }

    public void visitDroneAnimation(int xCord, int yCord, int droneX, int droneY) {
        TranslateTransition tt1 = createTranslateTransition(0, 0, 1000);
        RotateTransition rt1 = createRotateTransition(360, 2000);
        TranslateTransition tt2 = createTranslateTransition(xCord - droneX, yCord - droneY, 1000);

        SequentialTransition st = new SequentialTransition(tt1, rt1, tt2);
        st.play();
    }

    public void scanWholeFarm() {
        TranslateTransition s1 = createTranslateTransition(0, 0, 1000);
        RotateTransition rt0 = createRotateTransition(90, 500);
        TranslateTransition s2 = createTranslateTransition(520, 40, 1000);
        RotateTransition rt1 = createRotateTransition(90, 500);
        TranslateTransition s7 = createTranslateTransition(520, 190, 500);
        RotateTransition rt6 = createRotateTransition(90, 500);
        TranslateTransition s8 = createTranslateTransition(-380, 190, 1000);
        RotateTransition rt7 = createRotateTransition(-90, 500);
        TranslateTransition s9 = createTranslateTransition(-380, 300, 500);
        RotateTransition rt8 = createRotateTransition(-90, 500);
        TranslateTransition s10 = createTranslateTransition(520, 300, 1000);
        RotateTransition rt9 = createRotateTransition(90, 500);
        TranslateTransition s11 = createTranslateTransition(520, 350, 500);
        RotateTransition rt10 = createRotateTransition(90, 500);
        TranslateTransition s12 = createTranslateTransition(-380, 350, 1000);
        RotateTransition rt15 = createRotateTransition(90, 500);
        TranslateTransition s17 = createTranslateTransition(0, 0, 1500);

        SequentialTransition s = new SequentialTransition(
                s1, rt0, s2, rt1, s7, rt6, s8, rt7, s9, rt8, s10, rt9, s11, rt10, s12, rt15, s17);
        s.play();
    }

    private TranslateTransition createTranslateTransition(double toX, double toY, double durationMillis) {
        TranslateTransition transition = new TranslateTransition();
        transition.setToX(toX);
        transition.setToY(toY);
        transition.setDuration(Duration.millis(durationMillis));
        transition.setNode(droneImage);
        return transition;
    }

    private RotateTransition createRotateTransition(double byAngle, double durationMillis) {
        RotateTransition transition = new RotateTransition();
        transition.setByAngle(byAngle);
        transition.setDuration(Duration.millis(durationMillis));
        transition.setNode(droneImage);
        return transition;
    }
}
