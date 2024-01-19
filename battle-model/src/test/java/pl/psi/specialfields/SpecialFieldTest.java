package pl.psi.specialfields;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;

import static org.assertj.core.api.Assertions.assertThat;

public class SpecialFieldTest {

    private static final Range<Integer> NOT_IMPORTANT_DMG = Range.closed(0, 0);

    @Test
    void creatureShouldReceive10DamageFromLava() {
        Creature creature = createSampleCreature(NOT_IMPORTANT_DMG);

        SpecialField lavaField = SpecialFieldFactory.create(SpecialFieldsStatistics.LAVA);

        creature.applyObstacleDamage(lavaField.getDamage());

        assertThat(creature.getCurrentHp()).isEqualTo(90);

    }

    @Test
    void creatureShouldReceive15DamageFromGuardAndGiveHimDamage() {
        Creature creature = createSampleCreature(Range.closed(10, 10));

        SpecialField guardField = SpecialFieldFactory.create(SpecialFieldsStatistics.GUARD);

        assertThat(guardField.getAmount()).isEqualTo(3);

        creature.attackSpecialField(guardField, creature);

        assertThat(creature.getCurrentHp()).isEqualTo(85);
        assertThat(guardField.getAmount()).isEqualTo(1);
        assertThat(guardField.getHp()).isEqualTo(5);
    }

    @Test
    void creatureShouldReceiveAMovementBuffFromFountain() {
        Creature creature = createSampleCreature(NOT_IMPORTANT_DMG);

        SpecialField fountainField = SpecialFieldFactory.create(SpecialFieldsStatistics.FOUNTAIN);

        creature.setMoveRange(5);
        creature.applyBuffOrDebuff(fountainField);

        assertThat(creature.getMoveRange()).isEqualTo(10);
    }

    @Test
    void creatureShouldReceiveAMovementDebuffFromSwamp() {
        Creature creature = createSampleCreature(NOT_IMPORTANT_DMG);

        SpecialField swampField = SpecialFieldFactory.create(SpecialFieldsStatistics.SWAMP);

        creature.setMoveRange(10);
        creature.applyBuffOrDebuff(swampField);

        assertThat(creature.getMoveRange()).isEqualTo(5);
    }

    private Creature createSampleCreature(Range<Integer> damage) {
        return new Creature.Builder()
                .statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(damage)
                        .build())
                .build();
    }
}
