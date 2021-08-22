package nepheus.capacitor.androidshortcuts;

public class ShortcutIcon {
    private final String type;

    private final String name;

    public ShortcutIcon(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Returns the type of the icon. Can be: "Bitmap" or "Ressource"
     * @return "Bitmap" or "Ressource"
     */
    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}
