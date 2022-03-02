package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class Coordinates {

  public static int COORDINATE_SIZE = 5;

  private final Map<Position, PositionData> positions;

  public Optional<PositionData> findPositionData(Position position) {
    return Optional.ofNullable(positions.get(position));
  }

  public Optional<PositionData> findPositionData(int x, int y) {
    return positions.keySet().stream().filter(position -> isIn(x, position.getX() * COORDINATE_SIZE) && isIn(y, position.getY() * COORDINATE_SIZE)).map(positions::get).findAny();
  }

  public Map<Position, PositionData> getPositions() {
    return positions;
  }

  private boolean isIn(int i, int i2) {
    return i >= i2 && i < i2 + COORDINATE_SIZE;
  }

  public void setPositionData(Position position, PositionData data) {
    positions.putIfAbsent(position, data);
  }

  public void draw(Graphics graphics, JFrame jFrame) {
    positions.forEach((position, data) -> data.draw(position, graphics, jFrame));
  }

}
