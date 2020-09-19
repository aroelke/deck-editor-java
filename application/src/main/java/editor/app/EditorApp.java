package editor.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import editor.gui.MainFrame;
import editor.gui.settings.SettingsDialog;

/**
 * Main class for running the application.
 * 
 * @author Alec Roelke
 */
public interface EditorApp
{
    static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

        try
        {
            Files.createDirectories(SettingsDialog.EDITOR_HOME);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Could not create directory " + SettingsDialog.EDITOR_HOME + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> new MainFrame(Arrays.stream(args).map(File::new).filter(File::exists).collect(Collectors.toList())).setVisible(true));
    }    
}
