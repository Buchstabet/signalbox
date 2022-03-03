package dev.buchstabet.signalbox.coordinates;

import org.bukkit.Material;

import java.awt.*;

public interface PositionData {

  void draw(Position position, Graphics graphics);

  void handleClick();

  byte getCurrentSet();

  void set(byte b);

  void setSet(boolean b);

  boolean isSet();

  Position getPosition();

  boolean isSettable();

  void setSettable(boolean b);

  void setOccupied(boolean b);

  boolean isOccupied();

  Material getMaterial();

}
