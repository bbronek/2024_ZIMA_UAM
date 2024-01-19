package pl.psi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import pl.psi.creatures.Creature;
import pl.psi.specialfields.SpecialField;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class GameEngine {

    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    private final TurnQueue turnQueue;
    private final Board board;
    private final PropertyChangeSupport observerSupport = new PropertyChangeSupport(this);

    public  GameEngine(final Hero aHero1, final Hero aHero2) {
        turnQueue = new TurnQueue(aHero1.getCreatures(), aHero2.getCreatures());
        board = new Board(aHero1.getCreatures(), aHero2.getCreatures());
    }

    public void attack(final Point point) {
        board.getCreature(point)
                .ifPresent(defender -> turnQueue.getCurrentCreature()
                        .attack(defender));
        pass();
    }

    public void attackSpecialField(final SpecialField aSpecialField) {
        Creature aCreature = turnQueue.getCurrentCreature();
        aCreature.attackSpecialField(aSpecialField, aCreature);
        pass();
    }

    public void applySpecialFieldAttack(final Point point, int damage) {
        board.getCreature(point)
                .ifPresent(defender -> turnQueue.getCurrentCreature()
                        .applyObstacleDamage(damage)
                );
    }

    public void applyBuffOrDebuff(final Point point, final SpecialField aSpecialField) {
        board.getCreature(point)
                .ifPresent(aCreature -> turnQueue.getCurrentCreature()
                        .applyBuffOrDebuff(aSpecialField)
                );
    }

    public boolean canMove(final Point aPoint) {
        return board.canMove(turnQueue.getCurrentCreature(), aPoint);
    }

    public void move(final Point aPoint) {
        board.move(turnQueue.getCurrentCreature(), aPoint);
        observerSupport.firePropertyChange(CREATURE_MOVED, null, aPoint);
    }

    public Optional<Creature> getCreature(final Point aPoint) {
        return board.getCreature(aPoint);
    }

    public Optional<SpecialField> getSpecialField(final Point aPoint) {
        return board.getSpecialField(aPoint);
    }

    public void pass() {
        turnQueue.next();
    }

    public void addObserver(final PropertyChangeListener aObserver) {
        observerSupport.addPropertyChangeListener(aObserver);
        turnQueue.addObserver(aObserver);
    }

    public boolean canAttack(final Point point) {
        double distance = board.getPosition(turnQueue.getCurrentCreature())
                .distance(point);
        return (board.getCreature(point)
                .isPresent() || isSpecialFieldAttackable(point))
                && distance < 2 && distance > 0;
    }

    public boolean isSpecialFieldADamageObstacle(final Point point) {
        AtomicBoolean isDamageObstacle = new AtomicBoolean(false);

        board.getSpecialField(point)
                .ifPresentOrElse(
                        specialField -> isDamageObstacle.set(specialField.isAttackPossible() && !specialField.isAttackable()),
                        () -> {}
                );

        return isDamageObstacle.get();
    }

    public boolean isSpecialFieldABuffOrDebuff(final Point point) {
        AtomicBoolean isABuffOrDebuff = new AtomicBoolean(false);

        board.getSpecialField(point)
                .ifPresentOrElse(
                        specialField -> isABuffOrDebuff.set(specialField.isMoveRangeDebuffPossible() || specialField.isMoveRangeBuffPossible()),
                        () -> {}
                );

        return isABuffOrDebuff.get();
    }


    public boolean isSpecialFieldAttackable(final Point point) {
        AtomicBoolean isSpecialFieldAttackable = new AtomicBoolean(false);

        board.getSpecialField(point)
                .ifPresentOrElse(
                        specialField -> isSpecialFieldAttackable.set(specialField.isAttackable()),
                        () -> {}
                );

        return isSpecialFieldAttackable.get();
    }

    public boolean isCurrentCreature(Point aPoint) {
        return Optional.of(turnQueue.getCurrentCreature()).equals(board.getCreature(aPoint));
    }
}
