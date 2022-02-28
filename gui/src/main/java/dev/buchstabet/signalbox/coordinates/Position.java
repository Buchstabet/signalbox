package dev.buchstabet.signalbox.coordinates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

@Getter
@RequiredArgsConstructor
public class Position {
  private final int x, y;

  public Position toCoordinate(JFrame jFrame) {
    return new Position((int) (Coordinates.COORDINATE_SIZE * x + jFrame.getSize().getWidth() / 2), (int) (Coordinates.COORDINATE_SIZE * y + jFrame.getSize().getHeight() / 2));
  }

}
