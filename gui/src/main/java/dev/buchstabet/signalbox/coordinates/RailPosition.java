package dev.buchstabet.signalbox.coordinates;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum RailPosition {

  VERTICAL((byte) 0),
  HORIZONTAL((byte) 1),
  SOUTH_EAST((byte) 6),
  SOUTH_WEST((byte) 7),
  NORTH_WEST((byte) 8),
  NORTH_EAST((byte) 9);


  @Getter
  private final byte id;

  RailPosition(byte id) {
    this.id = id;
  }

  public static Optional<RailPosition> getFromId(byte b) {
    return Arrays.stream(values()).filter(railPosition -> railPosition.getId() == b).findAny();
  }
}
