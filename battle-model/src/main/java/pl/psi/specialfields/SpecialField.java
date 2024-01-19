package pl.psi.specialfields;

public class SpecialField implements SpecialFieldIf {
    private final boolean isMovePossible;
    private final boolean isAttackPossible;
    private final boolean isAttackable;
    private final boolean isMoveRangeDebuffPossible;

    private int hp;
    private final int damage;

    private int amount;
    private final String name;

    private SpecialField(boolean isMovePossible, boolean isAttackPossible, boolean isAttackable, boolean isMoveRangeDebuffPossible, int hp, int damage, int amount, String name) {
        this.isMovePossible = isMovePossible;
        this.isAttackPossible = isAttackPossible;
        this.isAttackable = isAttackable;
        this.isMoveRangeDebuffPossible = isMoveRangeDebuffPossible;
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
    public boolean isMoveRangeDebuffPossible() { return isMoveRangeDebuffPossible; }

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

    public void setCurrentHp(final int currentHp) {
        hp = currentHp;
    }

    public void setAmount(final int currentAmount) {
        amount = currentAmount;
    }

    public void applyCreatureDamage(final SpecialField aSpecialField, final int aDamage) {
        int hpToSubstract = aDamage % aSpecialField.getHp();
        int amountToSubstract = Math.round((float) aDamage / aSpecialField.getHp());
        int hp = aSpecialField.getHp() - hpToSubstract;

        if (hp <= 0) {
            aSpecialField.setAmount(aSpecialField.getAmount() - 1);
        }
        else{
            aSpecialField.setCurrentHp(hp);
        }
        aSpecialField.setAmount(aSpecialField.getAmount() - amountToSubstract);
    }

    public boolean isAlive() {
        return getAmount() > 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isMovePossible;
        private boolean isAttackPossible;

        private boolean isAttackable;

        private boolean isMoveRangeDebuffPossible;

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

        public Builder setMoveRangeDebuff(boolean isMoveRangeDebuffPossible) {
            this.isMoveRangeDebuffPossible = isMoveRangeDebuffPossible;
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
            return new SpecialField(isMovePossible, isAttackPossible, isAttackable, isMoveRangeDebuffPossible, hp, damage, amount, name);
        }
    }

}
