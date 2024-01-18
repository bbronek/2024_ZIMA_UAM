package pl.psi.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.psi.creatures.Creature;
import pl.psi.specialfields.SpecialField;

import java.io.InputStream;

class MapTile extends StackPane {

    public static final int RECTANGLE_SIZE = 60;
    private final Rectangle rect;

    MapTile() {
        rect = new Rectangle(RECTANGLE_SIZE, RECTANGLE_SIZE);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.RED);
        getChildren().add(rect);
    }

    void setCreature(final Creature aCreature) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("creatures/" + aCreature.getName() + ".png");
        Image image = new Image(stream, 100, 100, false, false);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(RECTANGLE_SIZE);
        imageView.setFitWidth(RECTANGLE_SIZE);
        imageView.setImage(image);

        Label label = new Label(aCreature.getAmount() + " ");
        label.setAlignment(Pos.BOTTOM_RIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setStyle("-fx-font-weight: bold");

        getChildren().add(imageView);
        getChildren().add(label);
    }

    void setSpecialField(final SpecialField aSpecialField) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("fields/" + aSpecialField.getName() + ".png");
        Image image = new Image(stream, 100, 100, false, false);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(RECTANGLE_SIZE);
        imageView.setFitWidth(RECTANGLE_SIZE);
        imageView.setImage(image);

        Label label = new Label("");

        if (aSpecialField.isAttackable()) {
            label = new Label(aSpecialField.getAmount() + " ");
        }

        label.setAlignment(Pos.BOTTOM_RIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setStyle("-fx-font-weight: bold");

        getChildren().add(imageView);
        getChildren().add(label);
    }

    void setBackground(final Color aColor) {
        rect.setFill(aColor);
    }
}
