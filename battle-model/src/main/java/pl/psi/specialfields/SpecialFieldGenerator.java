package pl.psi.specialfields;

import com.google.common.collect.BiMap;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import java.util.*;

public class SpecialFieldGenerator {
    private static final int MAX_WIDTH = 14;
    private static final int MAX_HEIGHT = 9;
    private static final int MAX_SPECIAL_FIELDS = 20;

    private static final Set<SpecialFieldsStatistics> AVAILABLE_SPECIAL_FIELDS =
            EnumSet.of(SpecialFieldsStatistics.LAVA, SpecialFieldsStatistics.ROCK, SpecialFieldsStatistics.GUARD,
                    SpecialFieldsStatistics.SWAMP, SpecialFieldsStatistics.FOUNTAIN);

    public static void generateRandomSpecialFields(HashMap<Point, SpecialField> specialFieldsMap, BiMap<Point, Creature> creaturesMap) {
        Random random = new Random();

        int numberOfSpecialFields = random.nextInt(MAX_SPECIAL_FIELDS) + 10;

        for (int i = 0; i < numberOfSpecialFields; ++i) {
            SpecialFieldsStatistics randomSpecialFieldType = getRandomSpecialFieldType(random);
            Point randomPoint = getRandomPoint(random);

            while (specialFieldsMap.containsKey(randomPoint) || creaturesMap.containsKey(randomPoint)) {
                randomPoint = getRandomPoint(random);
            }

            specialFieldsMap.put(randomPoint, SpecialFieldFactory.create(randomSpecialFieldType));
        }
    }

    private static SpecialFieldsStatistics getRandomSpecialFieldType(Random random) {
        int index = random.nextInt(AVAILABLE_SPECIAL_FIELDS.size());
        return AVAILABLE_SPECIAL_FIELDS.toArray(new SpecialFieldsStatistics[0])[index];
    }

    private static Point getRandomPoint(Random random) {
        int x = random.nextInt(MAX_WIDTH);
        int y = random.nextInt(MAX_HEIGHT);
        return new Point(x, y);
    }
}

