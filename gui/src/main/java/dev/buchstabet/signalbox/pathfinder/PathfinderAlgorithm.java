package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.LinkedList;
import java.util.Optional;

@RequiredArgsConstructor
public class PathfinderAlgorithm {

  private final Position start, target;
  private final LinkedList<PathPosition> openList = new LinkedList<>(), closedList = new LinkedList<>();
  private final Coordinates coordinates;

  private PathPosition foundTarget;

  public void paintBestWay() {
    check(start, null);

    while (!openList.isEmpty() && foundTarget == null) {
      run();
    }

    if (foundTarget != null) {
      SignalGui.getInstance().getGraphics().setColor(Color.GREEN);
      while (true) {
        Position position = foundTarget.toCoordinate();
        SignalGui.getInstance().getGraphics().fillRect(position.getX(), position.getY(), 10, 10);

        PathPosition targetFrom = foundTarget.getFrom();
        if (targetFrom == null) break;
        PathPosition targetFromFrom = targetFrom.getFrom();
        if (targetFromFrom == null) break;

        Optional<PositionData> from = foundTarget.getPositionData();
        Optional<PositionData> through = targetFrom.getPositionData();
        Optional<PositionData> to = targetFromFrom.getPositionData();

        if (!from.isPresent() || !to.isPresent() || !through.isPresent()) return;

        PositionData fromData = from.get();
        PositionData throughData = through.get();
        PositionData toData = to.get();

        if (fromData.getCurrentSet() == 1 && toData.getCurrentSet() == 1) {
          throughData.set((byte) 1);
        } else if (fromData.getCurrentSet() == 1 && toData.getCurrentSet() == 0) {
          throughData.set((byte) 7);
        } else if (fromData.getCurrentSet() == 1 && toData.getCurrentSet() == 0) {
          throughData.set((byte) 7);
        }

        foundTarget = targetFrom;
      }
      SignalGui.getInstance().getGraphics().setColor(Color.BLACK);
    }
  }

  private void run() {
    PathPosition position = getBest();
    openList.remove(position);
    if (position.getDistance() == 0D) {
      foundTarget = position;
      return;
    }

    closedList.add(position);

    check(new Position(position.getX() + 1, position.getY()), position);
    check(new Position(position.getX() - 1, position.getY()), position);
    check(new Position(position.getX(), position.getY() + 1), position);
    check(new Position(position.getX(), position.getY() - 1), position);
  }

  private void check(Position position, PathPosition from) {
    if (openList.stream().anyMatch(pathPosition -> pathPosition.getX() == position.getX() && pathPosition.getY() == position.getY())
            || closedList.stream().anyMatch(pathPosition -> pathPosition.getX() == position.getX() && pathPosition.getY() == position.getY()))
      return;


    if (coordinates.getPositions().keySet().stream().anyMatch(position1 -> position.getX() == position1.getX() && position.getY() == position1.getY())) {
      openList.add(new PathPosition(position.getX(), position.getY(), from, calculateDistance(position, target)));
    }
  }

  private PathPosition getBest() {
    PathPosition pathPosition = openList.get(0);
    for (int i = 1; i < openList.size(); i++) {
      PathPosition position = openList.get(i);
      if (pathPosition.getDistance() > position.getDistance()) pathPosition = position;
    }
    return pathPosition;
  }


  private double calculateDistance(Position pos1, Position pos2) {
    return Math.sqrt(square(pos1.getX() - pos2.getX()) + square(pos1.getY() - pos2.getY()));
  }

  private int square(int i) {
    return i * i;
  }

}
