import { PluginListenerHandle } from '@capacitor/core';

export interface AndroidShortcutsPlugin {
  /**
   * Checks if dynamic shortcuts are supported on the device
   */
  isDynamicSupported(): Promise<{ result: boolean }>;
  /**
   * Checks if pinned shortcuts are supported on the device
   */
  isPinnedSupported(): Promise<{ result: boolean }>;
  /**
   * Created dynamic shortcuts
   * @param options An items array with the options of each shortcut
   */
  addDynamic(options: {
    items: {
      id: string;
      shortLabel: string;
      longLabel: string;
      iconBitmap: string;
      data: string;
    }[];
  }): Promise<void>;
  /**
   * Created a pinned shortcut
   * @param options An option object for the pinned shortcut
   */
  addPinned(options: {
    id: string;
    shortLabel: string;
    longLabel: string;
    iconBitmap: string;
    data: string;
  }): Promise<void>;
  /**
   * Add a listener to a shortcut tap event
   * @param eventName
   * @param listenerFunc
   */
  addListener(
    eventName: 'shortcut',
    listenerFunc: MessageListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}

export type MessageListener = (response: any) => void;
