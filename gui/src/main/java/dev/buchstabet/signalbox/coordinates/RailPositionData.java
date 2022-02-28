package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

@RequiredArgsConstructor
public class RailPositionData implements PositionData {

  private final boolean vertical;

  @Override
  public void handleClick(MouseEvent mouseEvent) {

  }

  @Override
  public void draw(Position position, Graphics graphics, JFrame jFrame) {

    Dimension size = jFrame.getSize();

    graphics.drawLine(
            (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE),
            (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE),
            (int) (size.getWidth() / 2 + position.getX() * Coordinates.COORDINATE_SIZE + (!vertical ? Coordinates.COORDINATE_SIZE : 0)),
            (int) (size.getHeight() / 2 + position.getY() * Coordinates.COORDINATE_SIZE + (vertical ? Coordinates.COORDINATE_SIZE : 0)));

  }

}
