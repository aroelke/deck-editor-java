package editor.database.card;

import static editor.database.card.CardLayout.ADVENTURE;
import static editor.database.card.CardLayout.AFTERMATH;
import static editor.database.card.CardLayout.SPLIT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import editor.util.Lazy;

/**
 * This class represents a card that has several faces all printed on the front.
 *
 * @author Alec Roelke
 */
public class SplitCard extends MultiCard
{
    /**
     * Mana value of this SplitCard.
     */
    private Lazy<Double> manaValue;

    /**
     * Create a new SplitCard with the given Cards as faces.
     *
     * @param f cards to use as faces
     */
    public SplitCard(Card... f)
    {
        this(Arrays.asList(f));
    }

    /**
     * Create a new SplitCard with the given Cards as faces.  They should all indicate
     * with their layouts that they are split cards.
     *
     * @param f list of Cards representing faces
     */
    public SplitCard(List<? extends Card> f)
    {
        super(f.get(0).layout(), f);
        for (Card face : f)
            if (face.layout() != SPLIT && face.layout() != AFTERMATH && face.layout() != ADVENTURE)
                throw new IllegalArgumentException("can't create split cards out of non-split cards");
        for (Card face : f)
            if (face.layout() != f.get(0).layout())
                throw new IllegalArgumentException("all faces of a split card must be of the same type");

        manaValue = new Lazy<>(() -> f.stream().mapToDouble((c) -> c.manaValue()).sum());
    }

    /**
     * {@inheritDoc}
     * The mana value of a split card is the sum of those of all of its faces.
     */
    @Override
    public double manaValue()
    {
        return manaValue.get();
    }

    /**
     * {@inheritDoc}
     * All of the faces of a SplitCard are on the front, so there is only one image for it.
     */
    @Override
    public List<String> imageNames()
    {
        return Collections.singletonList(super.imageNames().get(0));
    }

    /**
     * {@inheritDoc}
     * All of the faces of a SplitCard are on the front, so only one multiverseid is necessary.
     */
    @Override
    public List<Integer> multiverseid()
    {
        return Collections.singletonList(super.multiverseid().get(0));
    }
}
