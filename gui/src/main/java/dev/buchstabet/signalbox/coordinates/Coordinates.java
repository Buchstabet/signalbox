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

  public static final int  POSITION_SIZE = 2;

  private final Map<Position, PositionData> positions;
  private final Lock lock = new ReentrantLock();

  public Optional<PositionData> findPositionData(Position position) {
    return Optional.ofNullable(positions.get(position));
  }

  public void setPositionData(Position position, PositionData data) {
    positions.putIfAbsent(position, data);
  }

  public void draw(JFrame jFrame, Graphics graphics) {
    lock.lock();
    positions.forEach((position, data) -> data.draw(position, jFrame, graphics));
    lock.unlock();
  }

}
