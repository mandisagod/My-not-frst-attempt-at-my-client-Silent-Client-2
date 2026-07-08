# Silent Client

A lightweight, **100% client-side** mod menu for Minecraft **1.21.11** (Fabric), inspired by the convenience of Lunar Client's mod menu. Press a key, get a clean toggle list, done.

Silent Client only draws HUD text and reads your own player state. It doesn't touch combat, world state, or anything the server can see - so it's safe to run on any server that allows client-side mods.

## Features

- **Silent Menu** - press `Right Shift` in-game to open the Silent menu overlay
- **Armor Durability** - average remaining durability of your worn armor, e.g. `Armor: 75%`
- **Coordinates** - your current X/Y/Z position on the HUD
- **FPS Display** - a simple frames-per-second counter
- **Potion Timers** - active potion effects with time remaining, e.g. `Speed II - 1:24`
- Settings are saved automatically to `config/silentclient.json`

More modules are easy to add - see [Contributing](#contributing) below.

### Screenshots

> _Add screenshots here once you've built and run the mod:_
> - `docs/screenshots/menu.png` - the Silent menu open
> - `docs/screenshots/hud.png` - HUD modules active in-game

## Installation (for players)

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for Minecraft **1.21.11**
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for **1.21.11** and place it in your `mods` folder
3. Download `silent-client-1.0.0.jar` (see [Building](#building) below, or from the project's Releases/Actions page) and place it in your `mods` folder
4. Launch Minecraft with the Fabric profile
5. In-game, press **Right Shift** to open the Silent menu and toggle modules on/off

## Building

You do **not** need Gradle installed - it's bundled via the Gradle Wrapper. You do need **Java 21**.

### One-click (recommended)

- **Windows:** install [Java 21](https://adoptium.net/temurin/releases/?version=21), then double-click `BUILD_MOD_WINDOWS.bat`
- **Mac/Linux:** install Java 21, then run `./build_mod_mac_linux.sh`

The first build downloads Minecraft's libraries and can take several minutes; later builds are fast. When it finishes, your jar is at:

```
build/libs/silent-client-1.0.0.jar
```

### Manual

```bash
./gradlew build        # Mac/Linux
gradlew.bat build       # Windows
```

### Via GitHub Actions (no local install needed at all)

Every push to this repository triggers `.github/workflows/build.yml`, which compiles the mod on GitHub's servers and uploads the resulting jar as a downloadable **Artifact**. Push your code, open the **Actions** tab, wait for the green checkmark, then download `silent-client-mod` from the finished run.

### Dev testing

```bash
./gradlew runClient
```

## Project structure

```
Silent-Client/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew / gradlew.bat
├── gradle/wrapper/
├── .github/workflows/build.yml
├── src/
│   ├── main/                          # common (client+server) entrypoint
│   │   ├── java/com/silentclient/SilentClientMod.java
│   │   └── resources/
│   │       ├── fabric.mod.json
│   │       └── assets/silentclient/{icon.png, lang/en_us.json}
│   └── client/                        # client-only code (this mod is client-only)
│       ├── java/com/silentclient/client/
│       │   ├── SilentClient.java              # entrypoint: keybind, HUD registration
│       │   ├── config/SilentConfig.java       # JSON persistence
│       │   ├── gui/
│       │   │   ├── SilentMenuScreen.java      # the "Silent" menu GUI
│       │   │   └── SilentHudRenderer.java     # stacks enabled HUD modules
│       │   ├── module/
│       │   │   ├── Module.java                # base class for all modules
│       │   │   ├── ModuleRegistry.java        # central module list
│       │   │   └── impl/                      # individual modules
│       │   └── mixin/MinecraftClientMixin.java
│       └── resources/silentclient.client.mixins.json
└── LICENSE / README.md / .gitignore
```

## Contributing

New modules are the easiest way to contribute:

1. Create a class in `src/client/java/com/silentclient/client/module/impl/` that extends `Module`
2. Implement a `render(GuiGraphics, LocalPlayer, int x, int y, int textColor)` method that draws your module's HUD line(s) and returns how many pixels tall it drew (so the next module stacks correctly)
3. Register an instance of it in `ModuleRegistry`
4. Wire it into `SilentHudRenderer.render(...)` next to the existing modules
5. Open a pull request!

**Please keep contributions client-side and detection-safe.** Silent Client intentionally does not include and will not accept combat automation (killaura, reach, autoclickers), map-reveal features (x-ray, ESP), or anything else that reads as cheating to anti-cheat systems or violates typical server rules. HUD information, visual/cosmetic tweaks, and quality-of-life conveniences are all welcome.

## License

MIT - see [LICENSE](LICENSE).
