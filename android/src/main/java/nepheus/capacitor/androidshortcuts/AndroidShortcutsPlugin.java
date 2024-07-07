package nepheus.capacitor.androidshortcuts;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import org.json.JSONException;
import org.json.JSONObject;

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
    public void setDynamic(PluginCall call) {
        JSArray items = call.getArray("items");

        if (items == null) {
            call.reject("'items' array must be set");
            return;
        }

        try {
            implementation.setDynamic(this.getBridge(), items);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }

        call.resolve();
    }

    @PluginMethod
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pin(PluginCall call) {
        String id = call.getString("id");
        String shortLabel = call.getString("shortLabel");
        String longLabel = call.getString("longLabel");
        String data = call.getString("data");

        ShortcutIcon shortcutIcon = null;
        try {
            JSONObject iconObject = call.getObject("icon");
            if (iconObject == null) {
                call.reject("'icon' object must be set");
                return;
            }
            shortcutIcon = new ShortcutIcon(ShortcutIconEnum.valueOf(iconObject.getString("type")), iconObject.getString("name"));
        } catch (JSONException e) {
            call.reject("'icon' object is not parsable");
            return;
        }

        try {
            Icon icon = implementation.generateIcon(bridge, shortcutIcon);
            implementation.pin(this.getBridge(), id, shortLabel, longLabel, icon, data);
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

        if (Intent.EXTRA_SHORTCUT_INTENT.equals(intent.getAction())) {
            JSObject ret = new JSObject();
            ret.put("data", intent.getStringExtra("data"));
            notifyListeners("shortcut", ret, true);
        }
    }
}
