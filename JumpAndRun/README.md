# Jump and Run Plugin

## Overview
Welcome to the Jump and Run plugin for Spigot 1.8.8! This plugin provides a dynamic and challenging parkour experience in Minecraft. Players will be teleported to a block within a designated area when the game starts. The blocks are automatically generated, and as players jump to the next block, the previous one disappears. The game continues indefinitely, with players being teleported back to the starting height upon reaching the maximum height.

## Usage

### Area Setup
To define the jump and run area, use the `/area` command:

- `/area set 1`: Set the first position.
- `/area set 2`: Set the second position.
- `/area confirm`: Confirm the positions and save them to the config.

**Example:**
1. Go to the first corner of your desired area and type `/area set 1`.
2. Move to the opposite corner and type `/area set 2`.
3. Confirm the area setup with `/area confirm`. This will save the positions to the config file.

### Starting the Game
To start the game, stand on or interact with the activation block/item specified in the config. 

## Configuration
After running the plugin once, a config file will be generated. This file allows you to customize various settings, including the activation block and conditions for starting the game.

### Configuration Details:

- `stackoverflowAttempts`: The number of attempts the plugin makes to find a suitable location for the next block. If the attempts exceed this limit, the game stops.
- `startHeight` and `endHeight`: Define the height range for the jump and run area.
- `activation.block`: The block used to start the jump and run area.
- `activation.underground.block`: The block placed under the activation block to enable it.
- `activation.underground.height`: The number of blocks below the activation block where the trigger block is placed.
- `area.position1` and `area.position2`: The coordinates for the two corners of the jump and run area.

### Example Config:
```yaml
Settings:
  stackoverflowAttempts: 100
  startHeight: 100
  endHeight: 110
  activation:
    block: GOLD_PLATE
    underground:
      block: REDSTONE_BLOCK
      height: 2
  area:
    position1:
      ==: org.bukkit.Location
      world: world
      x: 43.848384369430356
      y: 92.8503215752898
      z: 216.53301899693284
      pitch: 20.71648
      yaw: 22.645752
    position2:
      ==: org.bukkit.Location
      world: world
      x: -19.474248515774118
      y: 149.8408759998909
      z: 276.6230789520747
      pitch: 52.21639
      yaw: 278.088
```

## Example Scenario
- Set the activation block to a gold plate (GOLD_PLATE).
- Place a redstone block (REDSTONE_BLOCK) two blocks below the gold plate.
- When a player stands on the gold plate, the game starts

## Conclusion
Thank you for using the Jump and Run plugin! Customize it to fit your needs and enjoy an exciting parkour experience in your Minecraft world. If you have any issues or suggestions, feel free to open an issue on GitHub


