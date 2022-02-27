package dev.buchstabet.signalbox.coordinates;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Position {
  private final int x, y;

  @Override
  public String toString() {
    return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
