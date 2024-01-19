package pl.psi.specialfields;

public enum SpecialFieldsStatistics implements SpecialFieldIf {
    LAVA("Lava", true, false, true, false, false, 0, 10, 0),
    ROCK("Rock", false, false, false, false, false, 0, 0, 0),
    GUARD("Guard", true, true, false, false,false, 5, 10, 3),
    SWAMP("Swamp", false, false, true, true,false, 0, 0, 0),
    FOUNTAIN("Fountain", false, false, true, false,true, 0, 0, 0);

    private final String name;
    private final boolean isAttackPossible;

    private final boolean isAttackable;
    private final boolean isMovePossible;

    private final boolean isMoveRangeDebuffPossible;
    private final boolean isMoveRangeBuffPossible;

    private final int hp;
    private final int damage;
    private final int amount;

    SpecialFieldsStatistics(String name, boolean isAttackPossible, boolean isAttackable, boolean isMovePossible, boolean isMoveRangeDebuffPossible, boolean isMoveRangeBuffPossible, int hp, int damage, int amount) {
        this.name = name;
        this.isAttackPossible = isAttackPossible;
        this.isAttackable = isAttackable;
        this.isMovePossible = isMovePossible;
        this.isMoveRangeDebuffPossible = isMoveRangeDebuffPossible;
        this.isMoveRangeBuffPossible = isMoveRangeBuffPossible;
        this.hp = hp;
        this.damage = damage;
        this.amount = amount;
    }
    @Override
    public boolean isMovePossible() {
        return isMovePossible;
    }

    @Override
    public boolean isAttackPossible() {
        return isAttackPossible;
    }

    @Override
    public boolean isAttackable() {
        return isAttackable;
    }

    @Override
    public boolean isMoveRangeDebuffPossible() { return isMoveRangeDebuffPossible; }

    @Override
    public boolean isMoveRangeBuffPossible() { return isMoveRangeBuffPossible; }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getAmount() { return amount; }
}
