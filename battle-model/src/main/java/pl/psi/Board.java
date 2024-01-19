package pl.psi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.psi.creatures.Creature;
import pl.psi.specialfields.SpecialField;
import pl.psi.specialfields.SpecialFieldFactory;
import pl.psi.specialfields.SpecialFieldGenerator;
import pl.psi.specialfields.SpecialFieldsStatistics;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class Board
{
    private static final int MAX_WITDH = 14;
    private final BiMap< Point, Creature > map = HashBiMap.create();
    private final HashMap< Point, SpecialField> specialFieldsMap = new HashMap<Point, SpecialField>();

    public Board( final List< Creature > aCreatures1, final List< Creature > aCreatures2 )
    {
        addCreatures( aCreatures1, 0 );
        addCreatures( aCreatures2, MAX_WITDH );
//        specialFieldsMap.put(new Point(2, 3), SpecialFieldFactory.create(SpecialFieldsStatistics.LAVA));
//        specialFieldsMap.put(new Point(2, 5), SpecialFieldFactory.create(SpecialFieldsStatistics.ROCK));
//        specialFieldsMap.put(new Point(5, 5), SpecialFieldFactory.create(SpecialFieldsStatistics.GUARD));
//        specialFieldsMap.put(new Point(8, 7), SpecialFieldFactory.create(SpecialFieldsStatistics.SWAMP));
//        specialFieldsMap.put(new Point(7, 9), SpecialFieldFactory.create(SpecialFieldsStatistics.FOUNTAIN));

        SpecialFieldGenerator.generateRandomSpecialFields(specialFieldsMap, map);
    }

    private void addCreatures( final List< Creature > aCreatures, final int aXPosition )
    {
        for( int i = 0; i < aCreatures.size(); i++ )
        {
            map.put( new Point( aXPosition, i * 2 + 1 ), aCreatures.get( i ) );
        }
    }

    Optional< Creature > getCreature( final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }
    Optional< SpecialField > getSpecialField(final Point aPoint ) { return Optional.ofNullable( specialFieldsMap.get( aPoint ) ); }

    void move( final Creature aCreature, final Point aPoint )
    {
        if( canMove( aCreature, aPoint ) )
        {
            map.inverse()
                .remove( aCreature );
            map.put( aPoint, aCreature );
        }
    }

    boolean canMove( final Creature aCreature, final Point aPoint )
    {
        boolean canMoveOnSpecialField = true;

        if( specialFieldsMap.containsKey( aPoint)) {
            canMoveOnSpecialField = specialFieldsMap.get(aPoint).isMovePossible();
        }

        if( map.containsKey( aPoint ) || !canMoveOnSpecialField)
        {
            return false;
        }

        final Point oldPosition = getPosition( aCreature );
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < aCreature.getMoveRange();
    }

    Point getPosition( Creature aCreature )
    {
        return map.inverse()
            .get( aCreature );
    }
}
