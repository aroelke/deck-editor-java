package gui.filter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import database.Card;

/**
 * This class represents a group of FilterPanels, any of which may also be FilterGroups
 * themselves (since a FilterGroup is also a FilterPanel).  The top-level FilterGroup
 * in a dialog will belong to a <code>null</code> group.
 * 
 * @author Alec Roelke
 */
@SuppressWarnings("serial")
public class FilterGroupPanel extends FilterPanel
{
	/**
	 * Parent dialog of this FilterGroup, if it is the top-level one.  Should be
	 * <code>null</code> otherwise.
	 */
	private FilterDialog parent;
	/**
	 * Combo box displaying the possible ways that filters can be combined.
	 */
	private JComboBox<Mode> modeBox;
	/**
	 * Panel containing this FilterGroup's constituents.
	 */
	private JPanel filtersPanel;
	/**
	 * Filters comprising this FilterGroup's constituents.
	 */
	private List<FilterPanel> filters;

	/**
	 * Create a new top-level FilterGroup with a single empty FilterTypePanel.
	 * 
	 * @param p Parent dialog of the new FilterGroup.
	 */
	public FilterGroupPanel(FilterDialog p)
	{
		super(null);
		parent = p;
		init(Arrays.asList(new FilterTypePanel(this)));
	}
	
	/**
	 * Create a new inner FilterGroup with a single empty FilterTypePanel.
	 * 
	 * @param g Containing group of the new FilterGroup.
	 */
	public FilterGroupPanel(FilterGroupPanel g)
	{
		super(g);
		parent = null;
		init(Arrays.asList(new FilterTypePanel(this)));
	}
	
	/**
	 * Create a new top-level FilterGroup with the specified FilterPanel as
	 * its only constituent.
	 * 
	 * @param p Parent dialog of the new FilterGroup
	 * @param panel Initial constituent of the new FilterGroup
	 */
	public FilterGroupPanel(FilterDialog p, FilterPanel panel)
	{
		this(p, null, Arrays.asList(panel));
	}

	/**
	 * Create a new inner FilterGroup with the specified FilterPanel as its
	 * only constituent.
	 * 
	 * @param g Containing group of the new FilterGroup
	 * @param panel Initial constituent of the new FilterGroup
	 */
	public FilterGroupPanel(FilterGroupPanel g, FilterPanel panel)
	{
		this(null, g, Arrays.asList(panel));
	}
	
	/**
	 * Create a new top-level FilterGroup with the specified FilterPanels as
	 * its only constituents.
	 * 
	 * @param p Parent dialog of the new FilterGroup
	 * @param panel Initial constituents of the new FilterGroup
	 */
	public FilterGroupPanel(FilterDialog p, Collection<FilterPanel> panels)
	{
		this(p, null, panels);
	}
	
	/**
	 * Create a new inner FilterGroup with the specified FilterPanels as its
	 * only constituents.
	 * 
	 * @param g Containing group of the new FilterGroup
	 * @param panel Initial constituents of the new FilterGroup
	 */
	public FilterGroupPanel(FilterGroupPanel g, Collection<FilterPanel> panels)
	{
		this(null, g, panels);
	}
	
	/**
	 * Create a new FilterGroup with the specified FilterPanels as its initial
	 * constituents.  p and g should not both be non-<code>null</code> and should
	 * not both be <code>null</code>.  If p is non-<code>null</code>, then this is
	 * a top-level FilterGroup.  Otherwise, it is an inner FilterGroup.
	 * 
	 * @param p Parent dialog of the new FilterGroup.  Should be <code>null</code>
	 * if g is not
	 * @param g Containing group of the new FilterGroup.  Should be <code>null</code>
	 * if p is not
	 * @param panels
	 */
	private FilterGroupPanel(FilterDialog p, FilterGroupPanel g, Collection<FilterPanel> panels)
	{
		super(g);
		parent = p;
		init(panels);
	}
	
	/**
	 * Initialize this FilterGroup, creating its control buttons and fill out its initial
	 * values.
	 * 
	 * @param panels Collection of panels that should be this FilterGroup's initial
	 * constituents.
	 */
	private void init(Collection<FilterPanel> panels)
	{
		setBorder(new CompoundBorder(new EmptyBorder(5, 0, 10, 5), new EtchedBorder()));
		setLayout(new BorderLayout());
		filters = new ArrayList<FilterPanel>();
		
		// Panel containing control buttons
		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		add(topPanel, BorderLayout.NORTH);
		
		// Combo box showing how the constituents' CardFilters will be combined
		JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		modeBox = new JComboBox<Mode>();
		modeBox.setModel(new DefaultComboBoxModel<Mode>(Mode.values()));
		modePanel.add(modeBox);
		topPanel.add(modePanel);
		
		// Panel containing buttons to add new FilterPanels or to remove this FilterGroup
		// entirely.  For the top-level FilterGroup, it acts as a reset button.
		JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton addButton = new JButton("+");
		addButton.addActionListener((e) -> addFilterPanel(new FilterTypePanel(this)));
		editPanel.add(addButton);
		JButton removeButton = new JButton("\u2013");
		removeButton.addActionListener((e) -> {
			if (parent != null)
				parent.reset();
			else
				getGroup().removeFilterPanel(this);
		});
		editPanel.add(removeButton);
		topPanel.add(editPanel);
		
		// Panel containing constituent FilterPanels
		filtersPanel = new JPanel();
		filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.Y_AXIS));
		for (FilterPanel panel: panels)
			addFilterPanel(panel);
		add(filtersPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Force the window to resize according to the constituents of all of the
	 * FilterPanels it contains.
	 */
	public void pack()
	{
		revalidate();
		repaint();
		if (parent != null)
			parent.pack();
		else
			getGroup().pack();
	}
	
	/**
	 * Add the specified FilterPanel to this FilterGroup.
	 * 
	 * @param panel FilterPanel to add
	 */
	public void addFilterPanel(FilterPanel panel)
	{
		filtersPanel.add(panel);
		filters.add(panel);
		panel.setGroup(this);
		pack();
	}
	
	/**
	 * Remove the specified FilterPanel from this FilterGroup, if there are other
	 * FilterPanels in it.
	 * 
	 * @param panel Panel to possibly remove
	 */
	public void removeFilterPanel(FilterPanel panel)
	{
		if (filters.size() > 1 && filters.contains(panel))
		{
			filters.remove(panel);
			filtersPanel.remove(panel);
			pack();
		}
		else if (!filters.contains(panel))
			throw new IllegalArgumentException("FilterPanel \"" + panel + "\" not found in this group");
	}
	
	/**
	 * Enclose the specified FilterPanel in a new FilterGroup.
	 * 
	 * TODO: Try to figure out a way to replace the specified FilterPanel in place
	 * 
	 * @param panel Panel to enclose
	 */
	public void groupFilterPanel(FilterPanel panel)
	{
		if (filters.contains(panel))
		{
			filters.remove(panel);
			filtersPanel.remove(panel);
			addFilterPanel(new FilterGroupPanel(this, panel));
			pack();
		}
		else
			throw new IllegalArgumentException("FilterPanel \"" + panel + "\" not found in this group");
	}
	
	/**
	 * @return The combined CardFilter of all of the FilterPanels in this
	 * FilterGroup.
	 */
	@Override
	public Predicate<Card> getFilter()
	{
		Predicate<Card> f = filters.get(0).getFilter();
		switch (modeBox.getItemAt(modeBox.getSelectedIndex()))
		{
		case AND:
			for (int i = 1; i < filters.size(); i++)
				f = f.and(filters.get(i).getFilter());
			return f;
		case OR:
			for (int i = 1; i < filters.size(); i++)
				f = f.or(filters.get(i).getFilter());
			return f;
		default:
			throw new IllegalStateException("Unknown combination mode \"" + String.valueOf(modeBox.getSelectedItem()) + "\"");
		}
	}
	
	/**
	 * Set the contents of this FilterGroup according to the specified String, and set
	 * the contents of its constituents as well.  It should be a string enclosed by <>.
	 * 
	 * @param contents String to parse for contents.
	 */
	@Override
	public void setContents(String contents)
	{
		// Find the strings for the constituents of this FilterGroup (but not its constituents' constituents)
		String[] settings = contents.substring(1, contents.length() - 1).split("\\s+", 2);
		modeBox.setSelectedItem(Mode.valueOf(settings[0]));
		List<String> constituentContents = new ArrayList<String>();
		int depth = 0;
		StringBuilder str = new StringBuilder();
		for (char c: settings[1].toCharArray())
		{
			switch (c)
			{
			case '<':
				depth++;
				if (depth == 1)
					str = new StringBuilder();
				break;
			case '>':
				if (depth == 1)
				{
					str.append('>');
					constituentContents.add(str.toString());
				}
				depth--;
				break;
			default:
				break;
			}
			if (depth > 0)
				str.append(c);
		}
		if (depth != 0)
			throw new IllegalArgumentException("Unclosed <> detected in string \"" + contents + "\"");
		
		// Remove all panels from this FilterGroup
		for (FilterPanel panel: new ArrayList<FilterPanel>(filters))
		{
			filters.remove(panel);
			filtersPanel.remove(panel);
		}
		
		// For each string, determine if it belongs to a FilterGroup or to a FilterTypePanel and then
		// create the appropriate panel, set its content, and add it
		Pattern p = Pattern.compile("^\\s*<\\s*(?:AND|OR)", Pattern.CASE_INSENSITIVE);
		for (String constituent: constituentContents)
		{
			FilterPanel panel = (p.matcher(constituent).find() ? new FilterGroupPanel(this) : new FilterTypePanel(this));
			panel.setContents(constituent);
			addFilterPanel(panel);
		}
		pack();
	}
	
	/**
	 * @return <code>true</code> if this FilterGroup has no FilterPanels in it, and
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean isEmpty()
	{
		return filters.isEmpty();
	}
	
	/**
	 * @return A String representation of this FilterGroup, which is either AND or OR
	 * followed by the String representations of each of its constituents, all of which
	 * is enclosed by <>.
	 */
	@Override
	public String toString()
	{
		StringJoiner join = new StringJoiner(" ", "<", ">");
		join.add(modeBox.getItemAt(modeBox.getSelectedIndex()) == Mode.AND ? "AND" : "OR");
		for (FilterPanel filter: filters)
			join.add(filter.toString());
		return join.toString();
	}
	
	/**
	 * This class represents a method of combining CardFilters:  Either ANDing them
	 * together or ORing them.
	 * 
	 * @author Alec Roelke
	 */
	private enum Mode
	{
		AND("all of"),
		OR("any of");
		
		/**
		 * String representation of this Mode.
		 */
		private final String mode;
		
		/**
		 * Create a new Mode.
		 * 
		 * @param m String representation of the new Mode.
		 */
		private Mode(String m)
		{
			mode = m;
		}
		
		/**
		 * @return The String representation of this Mode.
		 */
		@Override
		public String toString()
		{
			return mode;
		}
	}
}