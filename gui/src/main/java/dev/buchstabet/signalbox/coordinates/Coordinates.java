package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class Coordinates {

  public static int COORDINATE_SIZE = 20;

  private final Map<Position, PositionData> positions;
  private final Lock lock = new ReentrantLock();

  public Optional<PositionData> findPositionData(Position position) {
    return Optional.ofNullable(positions.get(position));
  }

  public void setPositionData(Position position, PositionData data) {
    positions.putIfAbsent(position, data);
  }

  public void draw(Graphics graphics) {
    lock.lock();
    positions.forEach((position, data) -> data.draw(position, graphics));
    lock.unlock();
  }

}
