package pl.psi.creatures;//  ******************************************************************

//
//  Copyright 2022 PSI Software AG. All rights reserved.
//  PSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms
//
//  ******************************************************************

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import lombok.Setter;
import pl.psi.TurnQueue;

import com.google.common.collect.Range;

import lombok.Getter;
import pl.psi.specialfields.SpecialField;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
@Getter
public class Creature implements PropertyChangeListener {
    private CreatureStatisticIf stats;
    @Setter
    private int amount;
    private int currentHp;
    private int counterAttackCounter = 1;
    private DamageCalculatorIf calculator;

    private int moveRange;

    Creature() {
    }

    private Creature(final CreatureStatisticIf aStats, final DamageCalculatorIf aCalculator,
                     final int aAmount) {
        stats = aStats;
        amount = aAmount;
        currentHp = stats.getMaxHp();
        calculator = aCalculator;
        moveRange = stats.getMoveRange();
    }

    public void attack(final Creature aDefender) {
        if (isAlive()) {
            final int damage = getCalculator().calculateDamage(this, aDefender);
            applyDamage(aDefender, damage);
            if (canCounterAttack(aDefender)) {
                counterAttack(aDefender);
            }
        }
    }

    public void attackSpecialField(final SpecialField aSpecialField, final Creature aCreature) {
        if (isAlive() && aSpecialField.isAlive()) {
            final int aCreatureDamage = getCalculator().calculateDamage(this, aCreature);
            final int aSpecialFieldDamage = aSpecialField.getDamage();
            applyDamage(aCreature, aSpecialFieldDamage);
            aSpecialField.applyCreatureDamage(aSpecialField, aCreatureDamage);
        }
    }

    public void applyObstacleDamage(int damage) {
        if(isAlive()) {
            applyDamage(this, damage);
        }
    }

    public void applyBuffOrDebuff(final SpecialField specialField) {
        if (isAlive()) {
            if (specialField.isMoveRangeDebuffPossible()) {
                applyMoveRangeDebuff();
            } else if (specialField.isMoveRangeBuffPossible()) {
                applyMoveRangeBuff();
            }
        }
    }

    private void applyMoveRangeDebuff() {
        setMoveRange((int) Math.floor(getMoveRange() * 0.5));
    }

    private void applyMoveRangeBuff() {
        setMoveRange((int) Math.floor(getMoveRange() * 2.0));

    }

    public boolean isAlive() {
        return getAmount() > 0;
    }

    private void applyDamage(final Creature aDefender, final int aDamage) {
        int hpToSubstract = aDamage % aDefender.getMaxHp();
        int amountToSubstract = Math.round(aDamage / aDefender.getMaxHp());

        int hp = aDefender.getCurrentHp() - hpToSubstract;
        if (hp <= 0) {
            aDefender.setCurrentHp(aDefender.getMaxHp() - hp);
            aDefender.setAmount(aDefender.getAmount() - 1);
        }
        else{
            aDefender.setCurrentHp(hp);
        }
        aDefender.setAmount(aDefender.getAmount() - amountToSubstract);
    }

    public int getMaxHp() {
        return stats.getMaxHp();
    }

    protected void setCurrentHp(final int aCurrentHp) {
        currentHp = aCurrentHp;
    }

    public void setMoveRange(final int aMoveRange) {
        moveRange = aMoveRange;
    }

    private boolean canCounterAttack(final Creature aDefender) {
        return aDefender.getCounterAttackCounter() > 0 && aDefender.getCurrentHp() > 0;
    }

    private void counterAttack(final Creature aAttacker) {
        final int damage = aAttacker.getCalculator()
                .calculateDamage(aAttacker, this);
        applyDamage(this, damage);
        aAttacker.counterAttackCounter--;
    }

    Range<Integer> getDamage() {
        return stats.getDamage();
    }

    int getAttack() {
        return stats.getAttack();
    }

    int getArmor() {
        return stats.getArmor();
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (TurnQueue.END_OF_TURN.equals(evt.getPropertyName())) {
            counterAttackCounter = 1;
        }
    }

    protected void restoreCurrentHpToMax() {
        currentHp = stats.getMaxHp();
    }

    public String getName() {
        return stats.getName();
    }

    public int getMoveRange() {
        return moveRange;
    }

    public static class Builder {
        private int amount = 1;
        private DamageCalculatorIf calculator = new DefaultDamageCalculator(new Random());
        private CreatureStatisticIf statistic;

        public Builder statistic(final CreatureStatisticIf aStatistic) {
            statistic = aStatistic;
            return this;
        }

        public Builder amount(final int aAmount) {
            amount = aAmount;
            return this;
        }

        Builder calculator(final DamageCalculatorIf aCalc) {
            calculator = aCalc;
            return this;
        }

        public Creature build() {
            return new Creature(statistic, calculator, amount);
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
