package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Position;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public class PathPosition extends Position {

  @Getter @Nullable private final PathPosition from;
  @Getter private final double distance;


  public PathPosition(int x, int y, @Nullable PathPosition from, double distance) {
    super(x, y);
    this.from = from;
    this.distance = distance;
  }

}
