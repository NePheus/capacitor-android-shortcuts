package nepheus.capacitor.androidshortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSArray;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class AndroidShortcuts {

    public Boolean isDynamicSupported(Context context) {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1;
    }

    public Boolean isPinnedSupported(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            return shortcutManager.isRequestPinShortcutSupported();
        }

        return false;
    }

    public void addDynamic(Bridge bridge, JSArray items) throws PackageManager.NameNotFoundException, JSONException {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N_MR1) {
            throw new UnsupportedOperationException("Dynamic shortcuts are not supported on this device");
        }

        ShortcutManager shortcutManager = bridge.getContext().getSystemService(ShortcutManager.class);

        int count = items.length();
        ArrayList<ShortcutInfo> shortcuts = new ArrayList<ShortcutInfo>(count);

        for (int i = 0; i < count; ++i) {
            JSONObject item = items.getJSONObject(i);
            String id = item.getString("id");
            String shortLabel = item.getString("shortLabel");
            String longLabel = item.getString("longLabel");
            String iconBitmap = item.getString("iconBitmap");
            String data = item.getString("data");
            ShortcutInfo shortcut = buildShortcut(shortcutManager, bridge, id, shortLabel, longLabel, iconBitmap, data);
            shortcuts.add(shortcut);
        }

        shortcutManager.setDynamicShortcuts(shortcuts);
    }

    public void addPinned(Bridge bridge, String id, String shortLabel, String longLabel, String iconBitmap, String data)
        throws UnsupportedOperationException, PackageManager.NameNotFoundException {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            throw new UnsupportedOperationException("Pinned shortcuts are not supported on this device");
        }

        ShortcutManager shortcutManager = bridge.getContext().getSystemService(ShortcutManager.class);

        if (!shortcutManager.isRequestPinShortcutSupported()) {
            throw new UnsupportedOperationException("Pinned shortcuts are not supported on this device");
        }

        ShortcutInfo shortcut = buildShortcut(shortcutManager, bridge, id, shortLabel, longLabel, iconBitmap, data);

        shortcutManager.requestPinShortcut(shortcut, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private ShortcutInfo buildShortcut(
        ShortcutManager shortcutManager,
        Bridge bridge,
        String id,
        String shortLabel,
        String longLabel,
        String iconBitmap,
        String data
    ) throws InvalidParameterException, PackageManager.NameNotFoundException {
        if (id.length() == 0) {
            throw new InvalidParameterException("Parameter 'Id' invalid");
        }

        if (shortLabel.length() == 0 || longLabel.length() == 0) {
            throw new InvalidParameterException("Parameter 'shortLabel' or 'longLabel' invalid");
        }

        Context context = bridge.getContext();
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, id);

        Icon icon;
        try {
            icon = Icon.createWithBitmap(decodeBase64Bitmap(iconBitmap));
        } catch (Exception e) {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(bridge.getActivity().getPackageName(), PackageManager.GET_META_DATA);
            icon = Icon.createWithResource(bridge.getActivity().getPackageName(), applicationInfo.icon);
        }

        Intent intent = new Intent(
            Intent.EXTRA_SHORTCUT_INTENT,
            bridge.getIntentUri(),
            bridge.getContext(),
            bridge.getActivity().getClass()
        );
        intent.putExtra("data", data);

        return builder
            .setActivity(bridge.getActivity().getComponentName())
            .setIntent(intent)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            .setIcon(icon)
            .build();
    }

    private static Bitmap decodeBase64Bitmap(String input) {
        byte[] decodedString = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
