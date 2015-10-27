package editor.gui.filter;

import editor.gui.filter.editor.EmptyFilterPanel;
import editor.gui.filter.editor.FilterEditorPanel;
import editor.gui.filter.editor.ManaCostFilterPanel;
import editor.gui.filter.editor.TypeLineFilterPanel;
import editor.gui.filter.editor.colors.CardColorFilterPanel;
import editor.gui.filter.editor.colors.ColorIdentityFilterPanel;
import editor.gui.filter.editor.number.CMCFilterPanel;
import editor.gui.filter.editor.number.CardNumberFilterPanel;
import editor.gui.filter.editor.number.LoyaltyFilterPanel;
import editor.gui.filter.editor.number.PowerFilterPanel;
import editor.gui.filter.editor.number.ToughnessFilterPanel;
import editor.gui.filter.editor.options.DefaultsFilterPanel;
import editor.gui.filter.editor.options.multi.LegalFilterPanel;
import editor.gui.filter.editor.options.multi.SubtypeFilterPanel;
import editor.gui.filter.editor.options.multi.SupertypeFilterPanel;
import editor.gui.filter.editor.options.multi.TypeFilterPanel;
import editor.gui.filter.editor.options.singleton.BlockFilterPanel;
import editor.gui.filter.editor.options.singleton.ExpansionFilterPanel;
import editor.gui.filter.editor.options.singleton.RarityFilterPanel;
import editor.gui.filter.editor.text.ArtistFilterPanel;
import editor.gui.filter.editor.text.FlavorFilterPanel;
import editor.gui.filter.editor.text.NameFilterPanel;
import editor.gui.filter.editor.text.OracleTextFilterPanel;

/**
 * This enum represents a type of filter that can be used to filter Cards.
 * 
 * @author Alec Roelke
 */
public enum FilterType
{
	NAME("Name", "n", NameFilterPanel.class),
	MANA_COST("Mana Cost", "m", ManaCostFilterPanel.class),
	CMC("CMC", "cmc", CMCFilterPanel.class),
	COLOR("Color", "c", CardColorFilterPanel.class),
	COLOR_IDENTITY("Color Identity", "ci", ColorIdentityFilterPanel.class),
	TYPE_LINE("Type Line", "type", TypeLineFilterPanel.class),
	SUPERTYPE("Supertype", "super", SupertypeFilterPanel.class),
	TYPE("Type", "cardtype", TypeFilterPanel.class),
	SUBTYPE("Subtype", "sub", SubtypeFilterPanel.class),
	EXPANSION("Expansion", "x", ExpansionFilterPanel.class),
	BLOCK("Block", "b", BlockFilterPanel.class),
	RARITY("Rarity", "r", RarityFilterPanel.class),
	RULES_TEXT("Rules Text", "o", OracleTextFilterPanel.class),
	FLAVOR_TEXT("Flavor Text", "f", FlavorFilterPanel.class),
	POWER("Power", "p", PowerFilterPanel.class),
	TOUGHNESS("Toughness", "t", ToughnessFilterPanel.class),
	LOYALTY("Loyalty", "l", LoyaltyFilterPanel.class),
	ARTIST("Artist", "a", ArtistFilterPanel.class),
	CARD_NUMBER("Card Number", "#", CardNumberFilterPanel.class),
	FORMAT_LEGALITY("Format Legality", "legal", LegalFilterPanel.class),
	DEFAULTS("Defaults", "", DefaultsFilterPanel.class),
	NONE("<No Card>", "0", EmptyFilterPanel.class);
	
	/**
	 * Get a FilterType from a String.
	 * 
	 * @param c String to parse
	 * @return The FilterType corresponding to the String.
	 */
	public static FilterType fromCode(String c)
	{
		for (FilterType filterType: FilterType.values())
			if (c.equalsIgnoreCase(filterType.code))
				return filterType;
		throw new IllegalArgumentException("Illegal filter type string " + c);
	}
	
	/**
	 * Name of this FilterType.
	 */
	private final String name;
	/**
	 * Panel to create for this FilterType.
	 */
	private final Class<? extends FilterEditorPanel> panel;
	/**
	 * Code for this FilterType to figure out which panel to set content for.
	 */
	public final String code;
	
	/**
	 * Create a new FilterType.
	 * 
	 * @param n Name of the new FilterType.
	 * @param c Code of the new FilterType.
	 * @param f Filter panel class to create.
	 */
	private FilterType(String n, String c, Class<? extends FilterEditorPanel> f)
	{
		name = n;
		code = c;
		panel = f;
	}
	
	/**
	 * @return A new instance of this FilterType's panel.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public FilterEditorPanel newInstance() throws InstantiationException, IllegalAccessException
	{
		return panel.newInstance();
	}
	
	/**
	 * @return A String representation of this FilterType, which is it's name.
	 */
	@Override
	public String toString()
	{
		return name;
	}
}