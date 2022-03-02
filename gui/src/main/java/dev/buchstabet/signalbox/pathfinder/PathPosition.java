package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class PathPosition extends Position {

  @Getter @Nullable private final PathPosition from;
  @Getter private final double distance;


  public PathPosition(int x, int y, @Nullable PathPosition from, double distance) {
    super(x, y);
    this.from = from;
    this.distance = distance;
  }

  public Optional<PositionData> getPositionData() {
    Map<Position, PositionData> positions = SignalGui.getInstance().getCoordinates().getPositions();
    return positions.keySet().stream().filter(position -> position.getX() == getX() && position.getY() == getY()).map(positions::get).findAny();
  }

}
