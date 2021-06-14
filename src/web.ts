import { WebPlugin } from '@capacitor/core';

import { MessageListener, AndroidShortcutsPlugin } from './definitions';

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
    items: {
      id: string;
      shortLabel: string;
      longLabel: string;
      iconBitmap: string;
      data: string;
    }[];
  }): Promise<void> {
    console.error("Method 'add' not implemented.", JSON.stringify(options));
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addPinned(options: {
    id: string;
    shortLabel: string;
    longLabel: string;
    iconBitmap: string;
    data: string;
  }): Promise<void> {
    console.error("Method 'add' not implemented.", options);
    return Promise.reject("Method 'add' not implemented.") as any;
  }
  addListener(eventName: 'shortcut', listenerFunc: MessageListener) {
    listenerFunc(null);
    return Promise.reject(
      `Listener for '${eventName}' not implemented.`,
    ) as any;
  }
}
