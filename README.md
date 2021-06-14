[![npm version](https://badge.fury.io/js/capacitor-android-shortcuts.svg)](https://badge.fury.io/js/capacitor-android-shortcuts)

# capacitor-android-shortcuts

This plugin provides the feature to add a dynamic and pinned shortcut in Android apps. See more [in the Android docs](https://developer.android.com/guide/topics/ui/shortcuts).

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

// Add dynamic shortcuts
if (AndroidShortcuts.isDynamicSupported()) {
    AndroidShortcuts.addDynamic({
        items: [
            {
                id: 'myfirstid',
                shortLabel: 'My first short label',
                longLabel: 'My first long label',
                iconBitmap: 'BASE64DATA',
                data: 'I am a simple string'
            },
            {
                id: 'mysecondid',
                shortLabel: 'My first short label',
                longLabel: 'My first long label',
                iconBitmap: 'BASE64DATA',
                data: JSON.stringify({ myProperty: 'Pass a stringified JSON object' })
            },
        ],
    });
}

...

// Add pinned shortcuts
if (AndroidShortcuts.isDynamicSupported()) {
    AndroidShortcuts.addPinned(
        {
            id: 'mypinnedid',
            shortLabel: 'My pinned short label',
            longLabel: 'My pinned long label',
            iconBitmap: 'BASE64DATA',
            data: 'I am a simple string'
        }
    });
}

// Triggered when app is launched by a shortcut
AndroidShortcuts.addListener('shortcut', (response: any) => {
  // response.data contains the content of the 'data' property of the created shortcut
});
```

See also [Wiki: Icon Example](https://github.com/NePheus/capacitor-android-shortcuts/wiki/Icon-Example)

## API

<docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isDynamicSupported()

```typescript
isDynamicSupported() => any
```

Checks if dynamic shortcuts are supported on the device

**Returns:** <code>any</code>

---

### isPinnedSupported()

```typescript
isPinnedSupported() => any
```

Checks if pinned shortcuts are supported on the device

**Returns:** <code>any</code>

---

### addDynamic(...)

```typescript
addDynamic(options: { items: { id: string; shortLabel: string; longLabel: string; iconBitmap: string; data: string; }[]; }) => any
```

Created dynamic shortcuts

| Param         | Type                        | Description                                      |
| ------------- | --------------------------- | ------------------------------------------------ |
| **`options`** | <code>{ items: {}; }</code> | An items array with the options of each shortcut |

**Returns:** <code>any</code>

---

### addPinned(...)

```typescript
addPinned(options: { id: string; shortLabel: string; longLabel: string; iconBitmap: string; data: string; }) => any
```

Created a pinned shortcut

| Param         | Type                                                                                                  | Description                              |
| ------------- | ----------------------------------------------------------------------------------------------------- | ---------------------------------------- |
| **`options`** | <code>{ id: string; shortLabel: string; longLabel: string; iconBitmap: string; data: string; }</code> | An option object for the pinned shortcut |

**Returns:** <code>any</code>

---

### addListener(...)

```typescript
addListener(eventName: 'shortcut', listenerFunc: MessageListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Add a listener to a shortcut tap event

| Param              | Type                                    |
| ------------------ | --------------------------------------- |
| **`eventName`**    | <code>"shortcut"</code>                 |
| **`listenerFunc`** | <code>(response: any) =&gt; void</code> |

**Returns:** <code>any</code>

---

### Interfaces

#### PluginListenerHandle

| Prop         | Type                      |
| ------------ | ------------------------- |
| **`remove`** | <code>() =&gt; any</code> |

</docgen-api>
