package dev.buchstabet.signalbox.coordinates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import java.awt.*;

public interface PositionData {

  void draw(Graphics graphics);

  void handleClick();

  byte getCurrentSet();

  void set(byte b);

  void setSet(boolean b);

  boolean isSet();

  Position getPosition();

  boolean isSettable();

  void setSettable(boolean b);

  void setOccupied(Minecart minecart);

  boolean isOccupied();

  Location getLocation();

  Material getMaterial();

  default void handleSettable() {
    if (getMaterial() != Material.RAILS) return;
    switch (getCurrentSet()) {
      case 5: case 2: case 3: case 4:
        setSettable(false);
        break;
      default:
        setSettable(true);
    }
  }

}
