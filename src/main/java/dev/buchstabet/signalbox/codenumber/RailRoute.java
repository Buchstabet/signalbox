package dev.buchstabet.signalbox.codenumber;

import dev.buchstabet.signalbox.coordinates.PositionData;
import lombok.Data;

@Data
public class RailRoute {
  private final PositionData start, target;
}
