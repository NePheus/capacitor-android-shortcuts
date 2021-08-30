package nepheus.capacitor.androidshortcuts;

public class ShortcutIcon {
    private final ShortcutIconEnum type;

    private final String name;

    public ShortcutIcon(ShortcutIconEnum type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Returns the type of the icon as an enum.
     * 
     * @return type of the icon
     */
    public ShortcutIconEnum getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}
