package dev.buchstabet.signalbox.coordinates;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;

import java.awt.*;
import java.util.Objects;

public interface PositionData {

  default void draw(Graphics graphics) {
    Position coordinate = getPosition().toCoordinate();
    int startX = 0;
    int startY = 0;

    int targetX = 0;
    int targetY = 0;

    RailPosition railPosition = RailPosition.getFromId(getCurrentSet(), getMaterial());

    switch (Objects.requireNonNull(railPosition)) {
      case HORIZONTAL:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case VERTICAL:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);
        break;

      case SOUTH_EAST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case NORTH_EAST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.75);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;

      case SOUTH_WEST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.75);
        break;

      case NORTH_WEST:
        startX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.50);
        startY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.25);

        targetX = (int) (coordinate.getX() + Coordinates.COORDINATE_SIZE * 0.25);
        targetY = (int) (coordinate.getY() + Coordinates.COORDINATE_SIZE * 0.50);
        break;
    }

    graphics.setColor(getMinecart() != null ? Color.RED : isSet() ? Color.GREEN : Color.YELLOW);
    graphics.drawLine(startX, startY, targetX, targetY);
  }

  Minecart getMinecart();

  default void handleClick() {}

  byte getCurrentSet();

  default void set(byte b) {}

  void setSet(boolean b);

  boolean isSet();

  Position getPosition();

  void setOccupied(Minecart minecart);

  boolean isOccupied();

  Location getLocation();

  Material getMaterial();

  default boolean isSettable() {
    return getMaterial() == Material.RAIL;
  }

}
