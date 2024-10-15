import { WebPlugin } from '@capacitor/core';
import type { PluginListenerHandle } from '@capacitor/core';

import type { ShortcutItem, AndroidShortcutsPlugin } from './definitions';

export class AndroidShortcutsWeb
  extends WebPlugin
  implements AndroidShortcutsPlugin {
  isDynamicSupported(): Promise<{ result: boolean }> {
    throw new Error('Method not implemented.');
  }
  isPinnedSupported(): Promise<{ result: boolean }> {
    throw new Error('Method not implemented.');
  }
  setDynamic(options: { items: ShortcutItem[] }): Promise<void> {
    console.error("Method 'add' not implemented.", JSON.stringify(options));
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  pin(options: ShortcutItem): Promise<void> {
    console.error("Method 'add' not implemented.", options);
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addListener(
    eventName: 'shortcut',
    listenerFunc: (response: { data: string }) => void,
  ): Promise<PluginListenerHandle> {
    listenerFunc({ data: '' });
    return Promise.reject(
      `Listener for '${eventName}' not implemented.`,
    ) as any;
  }
  addDynamic(options: { items: ShortcutItem[] }): Promise<void> {
    console.error("Method 'add' not implemented.", JSON.stringify(options));
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addPinned(options: ShortcutItem): Promise<void> {
    console.error("Method 'add' not implemented.", options);
    return Promise.reject("Method 'add' not implemented.") as any;
  }
}
