package editor.app;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import editor.gui.MainFrame;

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

        SwingUtilities.invokeLater(() -> new MainFrame(Arrays.stream(args).map(File::new).filter(File::exists).collect(Collectors.toList())).setVisible(true));
    }    
}
