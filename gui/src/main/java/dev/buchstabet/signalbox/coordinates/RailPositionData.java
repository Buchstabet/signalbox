package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class RailPositionData implements PositionData {

  private final boolean vertical;

  @Override
  public void draw(Position position, Graphics graphics) {

    graphics.drawLine(
            position.getX() * Coordinates.COORDINATE_SIZE,
            position.getY() * Coordinates.COORDINATE_SIZE,
            position.getX() * Coordinates.COORDINATE_SIZE + (!vertical ? Coordinates.COORDINATE_SIZE : 0),
            position.getY() * Coordinates.COORDINATE_SIZE + (vertical ? Coordinates.COORDINATE_SIZE : 0));

  }

}
