package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import database.characteristics.CardCharacteristic;

@SuppressWarnings("serial")
public class SettingsDialog extends JDialog
{
	public SettingsDialog(JFrame owner)
	{
		super(owner, "Preferences", Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		JFileChooser inventoryChooser = new JFileChooser();
		inventoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// Tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Preferences");
		DefaultMutableTreeNode inventoryNode = new DefaultMutableTreeNode("Inventory");
		root.add(inventoryNode);
		DefaultMutableTreeNode pathsNode = new DefaultMutableTreeNode("Paths");
		inventoryNode.add(pathsNode);
		DefaultMutableTreeNode inventoryAppearanceNode = new DefaultMutableTreeNode("Appearance");
		inventoryNode.add(inventoryAppearanceNode);
		DefaultMutableTreeNode editorNode = new DefaultMutableTreeNode("Editor");
		root.add(editorNode);
		
		// Settings panels
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new CardLayout());
		add(settingsPanel, BorderLayout.CENTER);
		
		// Inventory paths
		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
		inventoryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingsPanel.add(inventoryPanel, pathsNode.toString());
		
		// Inventory site
		JPanel inventorySitePanel = new JPanel();
		inventorySitePanel.setLayout(new BoxLayout(inventorySitePanel, BoxLayout.X_AXIS));
		inventorySitePanel.add(new JLabel("Inventory Site:"));
		inventorySitePanel.add(Box.createHorizontalStrut(5));
		JTextField inventorysiteField = new JTextField(15);
		inventorySitePanel.add(inventorysiteField);
		inventorySitePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, inventorySitePanel.getPreferredSize().height));
		inventoryPanel.add(inventorySitePanel);
		inventoryPanel.add(Box.createVerticalStrut(5));
		
		// Inventory file name
		JPanel inventoryFilePanel = new JPanel();
		inventoryFilePanel.setLayout(new BoxLayout(inventoryFilePanel, BoxLayout.X_AXIS));
		inventoryFilePanel.add(new JLabel("Inventory File:"));
		inventoryFilePanel.add(Box.createHorizontalStrut(5));
		JTextField inventoryFileField = new JTextField(10);
		inventoryFilePanel.add(inventoryFileField);
		inventoryFilePanel.add(Box.createHorizontalStrut(5));
		JLabel currentVersionLabel = new JLabel("(Current version: 1.0.0)");
		currentVersionLabel.setFont(new Font(currentVersionLabel.getFont().getFontName(), Font.ITALIC, currentVersionLabel.getFont().getSize()));
		inventoryFilePanel.add(currentVersionLabel);
		inventoryFilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, inventoryFilePanel.getPreferredSize().height));
		inventoryPanel.add(inventoryFilePanel);
		inventoryPanel.add(Box.createVerticalStrut(5));
		
		// Inventory file directory
		JPanel inventoryDirPanel = new JPanel();
		inventoryDirPanel.setLayout(new BoxLayout(inventoryDirPanel, BoxLayout.X_AXIS));
		inventoryDirPanel.add(new JLabel("Inventory File Location:"));
		inventoryDirPanel.add(Box.createHorizontalStrut(5));
		JTextField inventoryDirField = new JTextField(25);
		inventoryDirPanel.add(inventoryDirField);
		inventoryDirPanel.add(Box.createHorizontalStrut(5));
		JButton inventoryDirButton = new JButton("\u2026");
		inventoryDirButton.addActionListener((e) -> {
			if (inventoryChooser.showDialog(null, "Select Folder") == JFileChooser.APPROVE_OPTION)
				inventoryDirField.setText(inventoryChooser.getSelectedFile().getPath());
		});
		inventoryDirPanel.add(inventoryDirButton);
		inventoryDirPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, inventoryDirPanel.getPreferredSize().height));
		inventoryPanel.add(inventoryDirPanel);
		inventoryPanel.add(Box.createVerticalStrut(5));
		
		// Check for update on startup
		JPanel updatePanel = new JPanel(new BorderLayout());
		JCheckBox updateCheckBox = new JCheckBox("Check for update on program start");
		updatePanel.add(updateCheckBox, BorderLayout.WEST);
		updatePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, updatePanel.getPreferredSize().height));
		inventoryPanel.add(updatePanel);
		
		inventoryPanel.add(Box.createVerticalGlue());
		
		// Inventory appearance
		JPanel inventoryAppearancePanel = new JPanel();
		inventoryAppearancePanel.setLayout(new BoxLayout(inventoryAppearancePanel, BoxLayout.Y_AXIS));
		settingsPanel.add(inventoryAppearancePanel, inventoryAppearanceNode.toString());
		
		// Columns
		JPanel inventoryColumnsPanel = new JPanel(new GridLayout(0, 5));
		inventoryColumnsPanel.setBorder(new TitledBorder("Columns"));
		List<JCheckBox> columnCheckBoxes = new ArrayList<JCheckBox>();
		for (CardCharacteristic characteristic: CardCharacteristic.inventoryValues())
		{
			JCheckBox checkBox = new JCheckBox(characteristic.toString());
			columnCheckBoxes.add(checkBox);
			inventoryColumnsPanel.add(checkBox);
		}
		inventoryAppearancePanel.add(inventoryColumnsPanel);
		
		// Stripe color
		JPanel inventoryColorPanel = new JPanel(new BorderLayout());
		inventoryColorPanel.setBorder(new TitledBorder("Stripe Color"));
		JColorChooser inventoryStripeColor = new JColorChooser();
		inventoryColorPanel.add(inventoryStripeColor);
		inventoryAppearancePanel.add(inventoryColorPanel);
		
		// Editor
		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
		editorPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingsPanel.add(editorPanel, editorNode.toString());
		
		// Recent count
		JPanel recentPanel = new JPanel();
		recentPanel.setLayout(new BoxLayout(recentPanel, BoxLayout.X_AXIS));
		recentPanel.add(new JLabel("Recent file count:"));
		recentPanel.add(Box.createHorizontalStrut(5));
		JSpinner recentSpinner = new JSpinner(new SpinnerNumberModel(4, 1, Integer.MAX_VALUE, 1));
		recentPanel.add(recentSpinner);
		recentPanel.setMaximumSize(recentPanel.getPreferredSize());
		recentPanel.setAlignmentX(LEFT_ALIGNMENT);
		editorPanel.add(recentPanel);
		
		editorPanel.add(Box.createVerticalGlue());
		
		// Tree panel
		JPanel treePanel = new JPanel(new BorderLayout());
		JTree tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		((DefaultTreeCellRenderer)tree.getCellRenderer()).setLeafIcon(null);
		tree.addTreeSelectionListener((e) -> {
			((CardLayout)settingsPanel.getLayout()).show(settingsPanel, e.getPath().getLastPathComponent().toString());
		});
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.EAST);
		treePanel.setPreferredSize(new Dimension(110, 0));
		add(treePanel, BorderLayout.WEST);
		
		// Button panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JButton applyButton = new JButton("Apply");
		buttonPanel.add(applyButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> dispose());
		buttonPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> dispose());
		buttonPanel.add(cancelButton);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(owner);
	}
}
