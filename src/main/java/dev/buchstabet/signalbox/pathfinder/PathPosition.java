package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    Optional<PositionData> positionData = getPositionData();
    if (positionData.isPresent()) {
      if (positionData.get().getMaterial() == Material.RAILS)
        return 1;
    }

    return 2;
  }
}
