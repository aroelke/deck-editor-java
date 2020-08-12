package editor.database;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents the constraints on building a deck for a particular format.
 * 
 * @author Alec Roelke
 */
public class FormatConstraints
{
    /**
     * Mapping of format names onto their deckbulding constraints.
     */
    public static final Map<String, FormatConstraints> CONSTRAINTS = Map.ofEntries(
        new SimpleImmutableEntry<>("brawl", new FormatConstraints(60, true, 1, true)),
        new SimpleImmutableEntry<>("commander", new FormatConstraints(100, true, 1, true)),
        new SimpleImmutableEntry<>("duel", new FormatConstraints(100, true, 1, true)),
        new SimpleImmutableEntry<>("future", new FormatConstraints()),
        new SimpleImmutableEntry<>("historic", new FormatConstraints()),
        new SimpleImmutableEntry<>("legacy", new FormatConstraints()),
        new SimpleImmutableEntry<>("modern", new FormatConstraints()),
        new SimpleImmutableEntry<>("oldschool", new FormatConstraints()),
        new SimpleImmutableEntry<>("pauper", new FormatConstraints()),
        new SimpleImmutableEntry<>("penny", new FormatConstraints()),
        new SimpleImmutableEntry<>("pioneer", new FormatConstraints()),
        new SimpleImmutableEntry<>("standard", new FormatConstraints()),
        new SimpleImmutableEntry<>("vintage", new FormatConstraints())
    );

    /**
     * List of supported format names, in alphabetical order.
     */
    public static final List<String> FORMAT_NAMES = CONSTRAINTS.keySet().stream().sorted().collect(Collectors.toList());

    /**
     * List of types of each of the deckbuilding constraints.
     */
    public static final List<Class<?>> CLASSES = List.of(
        String.class,
        Integer.class,
        Boolean.class,
        Integer.class,
        Boolean.class
    );

    /**
     * The name of each type of deckbuilding constraint: name of the format,
     * number of cards allowed in the deck, whether that number is a minimum
     * number or exact number, number of copies of any card allowed in a deck
     * (not counting restricted cards, basic lands, or other cards that ignore
     * this restriction), and whether or not the format has a commander.
     */
    public static final List<String> DATA_NAMES = List.of(
        "Name",
        "Deck Size",
        "Exact?",
        "Max Card Count",
        "Has Commander?"
    );

    /** Number of cards the deck should have. */
    public final int deckSize;
    /** Whether {@link #deckSize} represents an exact count or minimum. */
    public final boolean isExact;
    /** Maximum number of copies of a card in a deck. */
    public final int maxCopies;
    /** Whether or not the format has a commander. */
    public final boolean hasCommander;
    
    /**
     * Create a new set of deckbuilding constraints.  Since this is immutable and
     * not customizable by the user, this constructor isn't visible outside this class.
     * 
     * @param size number of cards in a deck
     * @param exact whether or not <code>size</code> is an exact count
     * @param copies number of copies of a card
     * @param commander whether or not the format has a commander
     */
    private FormatConstraints(int size, boolean exact, int copies, boolean commander)
    {
        deckSize = size;
        isExact = exact;
        maxCopies = copies;
        hasCommander = commander;
    }

    /**
     * Create a default set of deckbuilding constraints, which is for a 60-card-
     * minimum-sized deck with 4 copies of any card and no commander.
     */
    public FormatConstraints()
    {
        this(60, false, 4, false);
    }

    /**
     * @param name name of the format
     * @return An array containig the elements of this set of deckbuilding constraints
     * with the name of the format prepended, in the order specified by {@link #DATA_NAMES}.
     */
    public Object[] toArray(String name)
    {
        return new Object[] { name, deckSize, isExact, maxCopies, hasCommander };
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof FormatConstraints))
            return false;
        FormatConstraints o = (FormatConstraints)other;
        return o.deckSize == deckSize && o.isExact == isExact && o.maxCopies == maxCopies && o.hasCommander == hasCommander;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(deckSize, isExact, maxCopies, hasCommander);
    }
}