[![npm version](https://badge.fury.io/js/capacitor-android-shortcuts.svg)](https://badge.fury.io/js/capacitor-android-shortcuts)
[![capacitor support](https://img.shields.io/badge/capacitor%20support-v6-brightgreen?logo=capacitor)](https://capacitorjs.com/)

# capacitor-android-shortcuts

This plugin provides the feature to add dynamic and pinned shortcuts in Android apps. See more [in the Android docs](https://developer.android.com/guide/topics/ui/shortcuts). Its possible to listen to a shortcut tap action with an event listener (see `Usage`).

**Dynamic shortcut**<br/>
Tap and hold on the app icon and you will see the dynamic shortcuts in the dropdown<br/>
=> Usage: Call the `setDynamic` method one time to set the array of dynamic shortcuts

**Pinned shortcut**<br/>
You can add a pinned shortcut programmatically inside your app, i.e. let a customer add a favorite of an article/product/... to the home screen<br/>
=> Usage: Call the `pin` method where the user wants to pin something. An alert will be shown to add the pinned shortcut to the home screen.

## Supported platforms

| Platform | Supported |
| -------- | --------: |
| Android  |         ✔ |
| iOS      |         ✖ |
| Web      |         ✖ |

## Install

```bash
npm install capacitor-android-shortcuts
npx cap sync android
```

## Usage

```javascript
import { AndroidShortcuts } from 'capacitor-android-shortcuts';

...

// Set dynamic shortcuts
AndroidShortcuts.isDynamicSupported().then(({ result }) => {
    if (result) {
        AndroidShortcuts.setDynamic({
            items: [
                {
                    id: "myfirstid",
                    shortLabel: "My first short label",
                    longLabel: "My first long label",
                    icon: {
                        type: "Bitmap",
                        name: "<base64-string>"
                    },
                    data: "I am a simple string",
                },
                {
                    id: "mysecondid",
                    shortLabel: "My first short label",
                    longLabel: "My first long label",
                    icon: {
                        type: "Resource",
                        name: "<vector-asset-name>"
                    },
                    data: JSON.stringify({
                        myProperty: "Pass a stringified JSON object",
                    }),
                },
            ],
        });
    }
});
...

// Add a pinned shortcut
AndroidShortcuts.isPinnedSupported().then(({ result }) => {
    if (result) {
        AndroidShortcuts.pin({
            id: "mypinnedid",
            shortLabel: "My pinned short label",
            longLabel: "My pinned long label",
            icon: {
                type: "Bitmap",
                name: "<base64-string>"
            },
            data: "I am a simple string",
        });
    }
});

// Triggered when app is launched by a shortcut
AndroidShortcuts.addListener('shortcut', (response: any) => {
  // response.data contains the content of the 'data' property of the created shortcut
});
```

## Usage of icons

See [Wiki: Icon examples](https://github.com/NePheus/capacitor-android-shortcuts/wiki/Icon-examples)

## API

<docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isDynamicSupported()

```typescript
isDynamicSupported() => Promise<{ result: boolean; }>
```

Checks if dynamic shortcuts are supported on the device

**Returns:** <code>Promise&lt;{ result: boolean; }&gt;</code>

--------------------


### isPinnedSupported()

```typescript
isPinnedSupported() => Promise<{ result: boolean; }>
```

Checks if pinned shortcuts are supported on the device

**Returns:** <code>Promise&lt;{ result: boolean; }&gt;</code>

--------------------


### setDynamic(...)

```typescript
setDynamic(options: { items: ShortcutItem[]; }) => Promise<void>
```

Set dynamic shortcuts

| Param         | Type                                    | Description                                      |
| ------------- | --------------------------------------- | ------------------------------------------------ |
| **`options`** | <code>{ items: ShortcutItem[]; }</code> | An items array with the options of each shortcut |

--------------------


### pin(...)

```typescript
pin(options: ShortcutItem) => Promise<void>
```

Add a pinned shortcut

| Param         | Type                                                  | Description                              |
| ------------- | ----------------------------------------------------- | ---------------------------------------- |
| **`options`** | <code><a href="#shortcutitem">ShortcutItem</a></code> | An option object for the pinned shortcut |

--------------------


### addListener('shortcut', ...)

```typescript
addListener(eventName: 'shortcut', listenerFunc: (response: { data: string; }) => void) => Promise<PluginListenerHandle>
```

Add a listener to a shortcut tap event

| Param              | Type                                                  |
| ------------------ | ----------------------------------------------------- |
| **`eventName`**    | <code>'shortcut'</code>                               |
| **`listenerFunc`** | <code>(response: { data: string; }) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Removes all listeners.

--------------------


### Interfaces


#### ShortcutItem

| Prop             | Type                                                                                       | Description                                                                                                                                                                                                                                                                                                  |
| ---------------- | ------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **`id`**         | <code>string</code>                                                                        | ID of the shortcut                                                                                                                                                                                                                                                                                           |
| **`shortLabel`** | <code>string</code>                                                                        | Sets the short title of a shortcut. This is a mandatory field when publishing a new shortcut with ShortcutManager.addDynamicShortcuts(List) or ShortcutManager.setDynamicShortcuts(List). This field is intended to be a concise description of a shortcut. The recommended maximum length is 10 characters. |
| **`longLabel`**  | <code>string</code>                                                                        | Sets the text of a shortcut. This field is intended to be more descriptive than the shortcut title. The launcher shows this instead of the short title when it has enough space. The recommend maximum length is 25 characters.                                                                              |
| **`icon`**       | <code>{ type: <a href="#availableicontypes">AvailableIconTypes</a>; name: string; }</code> | Defines the icon of the shortcut. You can set the icon as a BASE64-Bitmap or as a Resource name                                                                                                                                                                                                              |
| **`data`**       | <code>string</code>                                                                        | Data that is passed to the 'shortcut' event                                                                                                                                                                                                                                                                  |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### AvailableIconTypes

<code>'Bitmap' | 'Resource'</code>

</docgen-api>
