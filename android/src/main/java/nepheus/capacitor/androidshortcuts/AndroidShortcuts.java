package nepheus.capacitor.androidshortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
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

    public void setDynamic(Bridge bridge, JSArray items) throws PackageManager.NameNotFoundException, JSONException {
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
            String data = item.getString("data");

            ShortcutIcon shortcutIcon = null;
            try {
                JSONObject iconObject = item.getJSONObject("icon");
                shortcutIcon = new ShortcutIcon(ShortcutIconEnum.valueOf(iconObject.getString("type")), iconObject.getString("name"));
            } catch (JSONException e) {
                System.out.println("'icon' Object is not parsable");
            }

            Icon icon = this.generateIcon(bridge, shortcutIcon);

            ShortcutInfo shortcut = buildShortcut(bridge, id, shortLabel, longLabel, icon, data);
            shortcuts.add(shortcut);
        }

        shortcutManager.setDynamicShortcuts(shortcuts);
    }

    public void pin(Bridge bridge, String id, String shortLabel, String longLabel, Icon icon, String data)
        throws UnsupportedOperationException {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            throw new UnsupportedOperationException("Pinned shortcuts are not supported on this device");
        }

        ShortcutManager shortcutManager = bridge.getContext().getSystemService(ShortcutManager.class);

        if (!shortcutManager.isRequestPinShortcutSupported()) {
            throw new UnsupportedOperationException("Pinned shortcuts are not supported on this device");
        }

        ShortcutInfo shortcut = buildShortcut(bridge, id, shortLabel, longLabel, icon, data);

        shortcutManager.requestPinShortcut(shortcut, null);
    }

    /**
     * Generates an Icon based on the given parameter
     * @param shortcutIcon parameter of the icon that should be set
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Icon generateIcon(
            Bridge bridge, ShortcutIcon shortcutIcon) throws PackageManager.NameNotFoundException {
        if (shortcutIcon == null) {
            return this.getDefaultIcon(bridge);
        }

        try {
            if (shortcutIcon.getType().equals(ShortcutIconEnum.Bitmap)) {
                return Icon.createWithBitmap(decodeBase64Bitmap(shortcutIcon.getName()));
            } else if (shortcutIcon.getType().equals(ShortcutIconEnum.Resource)) {
                Resources activityRes = bridge.getContext().getResources();
                String activityPackage = bridge.getActivity().getPackageName();
                return Icon.createWithResource(bridge.getContext(), activityRes.getIdentifier(shortcutIcon.getName(), "drawable", activityPackage));
            } else {
                throw new InvalidParameterException("Parameter 'icon.type' is invalid");
            }
        } catch (Exception e) {
            return this.getDefaultIcon(bridge);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Icon getDefaultIcon(Bridge bridge) throws PackageManager.NameNotFoundException {
        Context context = bridge.getContext();
        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = pm.getApplicationInfo(bridge.getActivity().getPackageName(), PackageManager.GET_META_DATA);
        return Icon.createWithResource(bridge.getActivity().getPackageName(), applicationInfo.icon);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private ShortcutInfo buildShortcut(
        Bridge bridge,
        String id,
        String shortLabel,
        String longLabel,
        Icon icon,
        String data
    ) throws InvalidParameterException {
        if (id.length() == 0) {
            throw new InvalidParameterException("Parameter 'Id' invalid");
        }

        if (shortLabel.length() == 0 || longLabel.length() == 0) {
            throw new InvalidParameterException("Parameter 'shortLabel' or 'longLabel' invalid");
        }

        Context context = bridge.getContext();
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, id);

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
