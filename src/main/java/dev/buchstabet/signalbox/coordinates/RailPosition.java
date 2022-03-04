package dev.buchstabet.signalbox.coordinates;

import org.bukkit.Material;

public enum RailPosition {

  VERTICAL,
  HORIZONTAL,
  SOUTH_EAST,
  SOUTH_WEST,
  NORTH_WEST,
  NORTH_EAST;

  public static RailPosition getFromId(byte b, Material material) {

    if (material == Material.RAIL) {
      switch (b) {
        case 5: case 4: case 0: return VERTICAL;
        case 3: case 2:case 1: return HORIZONTAL;
        case 6: return SOUTH_EAST;
        case 7: return SOUTH_WEST;
        case 8: return NORTH_WEST;
        case 9: return NORTH_EAST;
      }
    } else if (material == Material.POWERED_RAIL || material == Material.ACTIVATOR_RAIL || material == Material.DETECTOR_RAIL) {
      switch (b) {
        case 0: case 8: case 12: case 13: case 4: case 5:
          return VERTICAL;
        default:
          return HORIZONTAL;
      }
    }

    return null;
  }
}
