package nepheus.capacitor.androidshortcuts;

import android.content.Intent;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AndroidShortcuts")
public class AndroidShortcutsPlugin extends Plugin {

    private AndroidShortcuts implementation = new AndroidShortcuts();

    @PluginMethod
    public void isDynamicSupported(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("result", implementation.isDynamicSupported(this.getContext()));
        call.resolve(ret);
    }

    @PluginMethod
    public void isPinnedSupported(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("result", implementation.isPinnedSupported(this.getContext()));
        call.resolve(ret);
    }

    @PluginMethod
    public void addDynamic(PluginCall call) {
        JSArray items = call.getArray("items");

        try {
            implementation.addDynamic(this.getBridge(), items);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }

        call.resolve();
    }

    @PluginMethod
    public void addPinned(PluginCall call) {
        String id = call.getString("id");
        String shortLabel = call.getString("shortLabel");
        String longLabel = call.getString("longLabel");
        String iconBitmap = call.getString("iconBitmap");
        String data = call.getString("data");

        try {
            implementation.addPinned(this.getBridge(), id, shortLabel, longLabel, iconBitmap, data);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }

        call.resolve();
    }

    /**
     * Listen for EXTRA_SHORTCUT_INTENT intents
     * @param intent
     */
    @Override
    protected void handleOnNewIntent(Intent intent) {
        super.handleOnNewIntent(intent);

        if (intent.getAction().equals(Intent.EXTRA_SHORTCUT_INTENT)) {
            JSObject ret = new JSObject();
            ret.put("data", intent.getStringExtra("data"));
            notifyListeners("shortcut", ret, true);
        }
    }
}
