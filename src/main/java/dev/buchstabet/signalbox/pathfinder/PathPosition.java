package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Position;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class PathPosition extends Position {

  @Getter @Nullable private final PathPosition from;
  private final double distance;

  public PathPosition(int x, int y, @Nullable PathPosition from, double distance) {
    super(x, y);
    this.from = from;
    this.distance = distance;
  }

  // https://www.youtube.com/watch?v=4cIkP9Yw7hw&t=196s&ab_channel=LetsGameDev
  public double getDistance() {
    return getPrice() + 1 + getDistance();
  }

  private int getPrice() {
    if (this.getFrom() == null) return 0;
    return this.getFrom().getPrice() + 1;
  }
}
