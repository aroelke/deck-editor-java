module editor
{
    requires java.desktop;
    requires com.google.gson;
    requires natty;

    opens editor.gui.settings to com.google.gson;

    exports editor.gui;
}
