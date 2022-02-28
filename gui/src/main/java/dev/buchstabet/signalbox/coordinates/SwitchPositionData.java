package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class SwitchPositionData implements PositionData {

  private final Position from, to;

  @Override
  public void draw(Position position, Graphics graphics) {

    graphics.drawLine(
            position.getX() * Coordinates.COORDINATE_SIZE + this.from.getX() * Coordinates.COORDINATE_SIZE,
            position.getY() * Coordinates.COORDINATE_SIZE + this.from.getY() * Coordinates.COORDINATE_SIZE,
            position.getX() * Coordinates.COORDINATE_SIZE + this.to.getX() * Coordinates.COORDINATE_SIZE,
            position.getY() * Coordinates.COORDINATE_SIZE + this.to.getY() * Coordinates.COORDINATE_SIZE);
  }

}
