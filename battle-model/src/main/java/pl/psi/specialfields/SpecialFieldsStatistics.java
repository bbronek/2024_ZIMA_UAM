package pl.psi.specialfields;

public enum SpecialFieldsStatistics implements SpecialFieldIf {
    LAVA("Lava", true, false, true, 0, 10, 0),
    ROCK("Rock", false, false, false, 0, 0, 0),
    GUARD("Guard", true, true, false, 5, 10, 3),
    SWAMP("Swamp", false, false, true, 0, 0, 0);

    private final String name;
    private final boolean isAttackPossible;

    private final boolean isAttackable;
    private final boolean isMovePossible;
    private final int hp;
    private final int damage;
    private final int amount;

    SpecialFieldsStatistics(String name, boolean isAttackPossible, boolean isAttackable, boolean isMovePossible, int hp, int damage, int amount) {
        this.name = name;
        this.isAttackPossible = isAttackPossible;
        this.isAttackable = isAttackable;
        this.isMovePossible = isMovePossible;
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
