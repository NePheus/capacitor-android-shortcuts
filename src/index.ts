import { registerPlugin } from '@capacitor/core';

import type { AndroidShortcutsPlugin } from './definitions';

const AndroidShortcuts = registerPlugin<AndroidShortcutsPlugin>(
  'AndroidShortcuts',
  {
    web: () => import('./web').then(m => new m.AndroidShortcutsWeb()),
  },
);

export * from './definitions';
export { AndroidShortcuts };
