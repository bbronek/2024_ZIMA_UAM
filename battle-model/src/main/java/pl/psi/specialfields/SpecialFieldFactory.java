package pl.psi.specialfields;

public class SpecialFieldFactory {
    public static SpecialField create(SpecialFieldsStatistics statistics) {
        return SpecialField.builder()
                .setMovePossible(statistics.isMovePossible())
                .setAttackPossible(statistics.isAttackPossible())
                .setAttackable(statistics.isAttackable())
                .setMoveRangeDebuff(statistics.isMoveRangeDebuffPossible())
                .setMoveRangeBuff(statistics.isMoveRangeBuffPossible())
                .setName(statistics.getName())
                .setHealthPoints(statistics.getHp())
                .setDamage(statistics.getDamage())
                .setAmount(statistics.getAmount())
                .build();
    }
}
