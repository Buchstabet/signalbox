package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public class RailPositionData implements PositionData {

  private final boolean vertical;

  @Override
  public void draw(Position position, JFrame jFrame, Graphics graphics) {
    System.out.println("Drawing: " + position);

    graphics.drawLine(
            position.getX() * Coordinates.POSITION_SIZE,
            position.getY() * Coordinates.POSITION_SIZE,
            position.getX() * Coordinates.POSITION_SIZE + (!vertical ? Coordinates.POSITION_SIZE : 0),
            position.getY() * Coordinates.POSITION_SIZE + (vertical ? Coordinates.POSITION_SIZE : 0));

  }

}
