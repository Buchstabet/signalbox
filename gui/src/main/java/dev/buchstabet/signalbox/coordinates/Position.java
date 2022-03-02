package dev.buchstabet.signalbox.coordinates;

import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Position {

  public static int xMove, yMove;

  private final int x, y;

  public Position toCoordinate() {
    return new Position(Coordinates.COORDINATE_SIZE * x + xMove, Coordinates.COORDINATE_SIZE * y + yMove);
  }

  public static Position fromXY(int x, int y) {

    int xTarget = calculate(x, SignalGui.getInstance().getSize().getWidth(), xMove);
    int yTarget = calculate(y, SignalGui.getInstance().getSize().getHeight(), yMove);
    return new Position(xTarget, yTarget);
  }

  private static int calculate(int input, double windowSize, int move) {
    int maxFields = (int) Math.floor(windowSize / (double) Coordinates.COORDINATE_SIZE);

    for (int i = 0; i < maxFields; i++) {
      if (input <= (Coordinates.COORDINATE_SIZE * i + move)) {
        return i - 1;
      }
    }

    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position)) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public String toString() {
    return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
