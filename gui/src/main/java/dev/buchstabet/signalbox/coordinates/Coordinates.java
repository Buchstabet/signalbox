package dev.buchstabet.signalbox.coordinates;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class Coordinates {

  public static int COORDINATE_SIZE = 5;

  private final Map<Position, PositionData> positions;
  private final Lock lock = new ReentrantLock();

  public Optional<PositionData> findPositionData(Position position) {
    return Optional.ofNullable(positions.get(position));
  }

  public Optional<PositionData> findPositionData(int x, int y) {
    return positions.keySet().stream().filter(position -> isIn(x, position.getX() * COORDINATE_SIZE) && isIn(y, position.getY() * COORDINATE_SIZE)).map(positions::get).findAny();
  }

  private boolean isIn(int i, int i2) {
    return i >= i2 && i < i2 + COORDINATE_SIZE;
  }

  public void setPositionData(Position position, PositionData data) {
    positions.putIfAbsent(position, data);
  }

  public void draw(Graphics graphics, JFrame jFrame) {
    lock.lock();
    positions.forEach((position, data) -> data.draw(position, graphics, jFrame));
    lock.unlock();
  }

}
