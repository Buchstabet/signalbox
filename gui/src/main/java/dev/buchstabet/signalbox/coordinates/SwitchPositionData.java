package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public class SwitchPositionData implements PositionData {

  private final int railwayswitchPosition;

  @Override
  public void draw(Position position, JFrame jFrame, Graphics graphics) {
    System.out.println("Drawing: " + position);

    graphics.drawLine(
            position.getX() * Coordinates.POSITION_SIZE,
            position.getY() * Coordinates.POSITION_SIZE,
            (position.getX() + 1) * Coordinates.POSITION_SIZE,
            position.getY() * Coordinates.POSITION_SIZE);
  }

}
