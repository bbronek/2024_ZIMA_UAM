package pl.psi.gui;

import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.psi.creatures.Creature;
import pl.psi.specialfields.SpecialField;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MainBattleController implements PropertyChangeListener {
    private final GameEngine gameEngine;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    public MainBattleController(final Hero aHero1, final Hero aHero2) {
        gameEngine = new GameEngine(aHero1, aHero2);
    }

    @FXML
    private void initialize() {
        refreshGui();
        gameEngine.addObserver(this);
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> gameEngine.pass());
    }

    private void refreshGui() {
        gridMap.getChildren()
                .clear();
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 10; y++) {
                Point currentPoint = new Point(x, y);
                Optional<Creature> creature = gameEngine.getCreature(currentPoint);
                Optional<SpecialField> specialField = gameEngine.getSpecialField(currentPoint);

                final MapTile mapTile = new MapTile();
                creature.ifPresent(mapTile::setCreature);
                specialField.ifPresent(mapTile::setSpecialField);
                if (gameEngine.isCurrentCreature(currentPoint)) {
                    mapTile.setBackground(Color.GREENYELLOW);
                }
                if (gameEngine.canMove(currentPoint)) {
                    mapTile.setBackground(Color.GREY);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        gameEngine.move(currentPoint);
                    });
                }
                if (gameEngine.canAttack(currentPoint)) {
                    mapTile.setBackground(Color.RED);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        specialField.ifPresentOrElse(
                                gameEngine::attackSpecialField,
                                () -> gameEngine.attack(currentPoint)
                        );
                    });
                }
                if (gameEngine.isSpecialFieldADamageObstacle(currentPoint)) {
                    AtomicInteger damage = new AtomicInteger(0);
                    specialField.ifPresent(field -> damage.set(field.getDamage()));

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        gameEngine.applySpecialFieldAttack(currentPoint, damage.get());
                    });
                }
                if (gameEngine.isSpecialFieldABuffOrDebuff(currentPoint)) {
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        specialField.ifPresent(sf -> gameEngine.applyBuffOrDebuff(currentPoint, sf));
                    });
                }
                gridMap.add(mapTile, x, y);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshGui();
    }
}
