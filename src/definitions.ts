import type { PluginListenerHandle } from '@capacitor/core';

export type AvailableIconTypes = "Bitmap" | "Resource";

export type ShortcutItem = {
  /**
   * ID of the shortcut
   */
  id: string;
  /**
   * Sets the short title of a shortcut.
   * This is a mandatory field when publishing a new shortcut with ShortcutManager.addDynamicShortcuts(List) or ShortcutManager.setDynamicShortcuts(List).
   * This field is intended to be a concise description of a shortcut.
   * The recommended maximum length is 10 characters.
   */
  shortLabel: string;
  /**
   * Sets the text of a shortcut.
   * This field is intended to be more descriptive than the shortcut title. The launcher shows this instead of the short title when it has enough space.
   * The recommend maximum length is 25 characters.
   */
  longLabel: string;
  /**
   * Defines the icon of the shortcut.
   * You can set the icon as a BASE64-Bitmap or as a Resource name
   */
  icon?: {
    /**
     * Type of the icon
     */
    type: AvailableIconTypes;
    /**
     * Name of te Resource or data of the encoded Bitmap
     */
    name: string;
  }
  /**
   * Data you will receive when the shortcut is opened
   */
  data: string;
};

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
    items: ShortcutItem[];
  }): Promise<void>;
  /**
   * Created a pinned shortcut
   * @param options An option object for the pinned shortcut
   */
  addPinned(options: ShortcutItem): Promise<void>;
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

export type MessageListener = (response: { data: string }) => void;
