package pl.psi.specialfields;

public class SpecialField implements SpecialFieldIf {
    private final boolean isMovePossible;
    private final boolean isAttackPossible;

    private final boolean isAttackable;
    private final int hp;
    private final int damage;

    private final int amount;
    private final String name;

    private SpecialField(boolean isMovePossible, boolean isAttackPossible, boolean isAttackable, int hp, int damage, int amount, String name) {
        this.isMovePossible = isMovePossible;
        this.isAttackPossible = isAttackPossible;
        this.isAttackable = isAttackable;
        this.hp = hp;
        this.damage = damage;
        this.amount = amount;
        this.name = name;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isMovePossible;
        private boolean isAttackPossible;

        private boolean isAttackable;
        private int hp;

        private int damage;

        private int amount;
        private String name;
        public Builder setMovePossible(boolean movePossible) {
            this.isMovePossible = movePossible;
            return this;
        }

        public Builder setAttackPossible(boolean attackPossible) {
            this.isAttackPossible = attackPossible;
            return this;
        }

        public Builder setAttackable(boolean isAttackable) {
            this.isAttackable = isAttackable;
            return this;
        }

        public Builder setHealthPoints(int healthPoints) {
            this.hp = healthPoints;
            return this;
        }

        public Builder setDamage(int damage) {
            this.damage = damage;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }


        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public SpecialField build() {
            return new SpecialField(isMovePossible, isAttackPossible, isAttackable, hp, damage, amount, name);
        }
    }

}
