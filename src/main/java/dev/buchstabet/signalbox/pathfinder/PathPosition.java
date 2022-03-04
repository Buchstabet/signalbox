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
  public double getTotalPrice() {
    return get() + getPrice() + distance;
  }

  private int get() {
    int price = 0;
    PathPosition pathPosition = this;
    while ((pathPosition = pathPosition.getFrom()) != null) {
      price += pathPosition.getPrice();
    }

    return price;
  }

  private int getPrice() {
    return 1;
  }
}
