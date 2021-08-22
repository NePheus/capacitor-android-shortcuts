import { WebPlugin } from '@capacitor/core';

import { ShortcutItem, MessageListener, AndroidShortcutsPlugin } from './definitions';

export class AndroidShortcutsWeb
  extends WebPlugin
  implements AndroidShortcutsPlugin {
  isDynamicSupported(): Promise<{ result: boolean }> {
    throw new Error('Method not implemented.');
  }
  isPinnedSupported(): Promise<{ result: boolean }> {
    throw new Error('Method not implemented.');
  }
  addDynamic(options: {
    items: ShortcutItem[];
  }): Promise<void> {
    console.error("Method 'add' not implemented.", JSON.stringify(options));
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addPinned(options: ShortcutItem): Promise<void> {
    console.error("Method 'add' not implemented.", options);
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addListener(eventName: 'shortcut', listenerFunc: MessageListener) {
    listenerFunc({ data: "" });
    return Promise.reject(
      `Listener for '${eventName}' not implemented.`,
    ) as any;
  }
}
