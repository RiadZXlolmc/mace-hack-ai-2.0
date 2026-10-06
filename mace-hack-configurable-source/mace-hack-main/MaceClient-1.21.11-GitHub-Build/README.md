# Mace Client — Minecraft 1.21.11

Fabric client with a simple click GUI. Open with **Right Shift**.

Modules included:
- ClickTP — client-side block target teleport test
- MaceDamage — configurable virtual fall-distance training mode
- Fly — movement/no-gravity client module
- BoatFly — removes gravity from your vehicle client-side
- Tracker — nearest-player HUD message
- ESP — module placeholder for a 1.21.11 render hook
- Speed — movement multiplier
- Reach — configurable reach setting placeholder

Important: on multiplayer servers the server is authoritative. A client changing its own state does not guarantee that a server will accept teleportation, fake mace fall distance, fly, reach, or speed.

Build:
./gradlew build
Jar: build/libs/
