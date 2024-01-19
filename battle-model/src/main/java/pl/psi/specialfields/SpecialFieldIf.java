package pl.psi.specialfields;

public interface SpecialFieldIf {
    boolean isMovePossible();
    boolean isAttackPossible();

    boolean isAttackable();

    boolean isMoveRangeDebuffPossible();

    boolean isMoveRangeBuffPossible();

    int getHp();

    String getName();
    int getDamage();

    int getAmount();
}
