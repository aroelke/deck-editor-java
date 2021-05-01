package editor.database.symbol;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import editor.database.attributes.ManaType;

/**
 * This class represents a symbol representing a variable amount of generic mana
 * using the variables X, Y or Z.
 *
 * @author Alec Roelke
 */
public class VariableSymbol extends ManaSymbol
{
    /**
     * Map of variable names onto their corresponding symbols.
     */
    public static final Map<String, VariableSymbol> SYMBOLS = Collections.unmodifiableMap(
            Stream.of('X', 'Y', 'Z').collect(Collectors.toMap(String::valueOf, VariableSymbol::new)));

    /**
     * Get the VariableSymbol corresponding to the given String.
     *
     * @param x the String to look up
     * @return the VariableSymbol corresponding to the String
     * @throws IllegalArgumentException if the String doesn't correspond to a variable symbol
     */
    public static VariableSymbol parseVariableSymbol(String x)
    {
        return tryParseVariableSymbol(x).orElseThrow(() -> new IllegalArgumentException('"' + x + "\" is not a variable symbol"));
    }

    /**
     * Get the VariableSymbol corresponding to the given String.
     *
     * @param x the String to look up
     * @return the VariableSymbol corresponding to the String, or null if there
     * is no such symbol.
     */
    public static Optional<VariableSymbol> tryParseVariableSymbol(String x)
    {
        return Optional.ofNullable(SYMBOLS.get(x.toUpperCase()));
    }

    /**
     * Variable name for this VariableSymbol.
     */
    private final char var;

    /**
     * Create a new VariableSymbol with the corresponding variable.
     *
     * @param v variable name for the new VariableSymbol
     */
    private VariableSymbol(char v)
    {
        super(Character.toLowerCase(v) + "_mana.png", String.valueOf(Character.toUpperCase(v)), 0);
        var = Character.toUpperCase(v);
    }

    /**
     * {@inheritDoc}
     * {@link ManaType#COLORLESS} is mapped to 0.5 and every other color is mapped to
     * 0.
     */
    @Override
    public Map<ManaType, Double> colorIntensity()
    {
        return createIntensity(new ColorIntensity(ManaType.COLORLESS, 0.5));
    }

    @Override
    public int compareTo(ManaSymbol other)
    {
        if (other instanceof VariableSymbol s)
            return var - s.var;
        else
            return super.compareTo(other);
    }
}
