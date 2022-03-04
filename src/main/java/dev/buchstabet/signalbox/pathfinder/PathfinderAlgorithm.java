package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.coordinates.StartPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.helpbuttons.FfRT;
import dev.buchstabet.signalbox.signal.SignalPosition;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class PathfinderAlgorithm {

  private final Position start, target;
  private final LinkedList<PathPosition> openList = new LinkedList<>(), closedList = new LinkedList<>();
  private final Coordinates coordinates;
  private PathPosition foundTarget;
  private boolean frt;

  public CompletableFuture<Collection<PathPosition>> paintBestWay() {
    return CompletableFuture.supplyAsync(() -> {
      List<PathPosition> positions = new ArrayList<>();

      if (start.equals(target)) return positions;

      check(start, null);
      while (!openList.isEmpty() && foundTarget == null) {
        run();
      }

      if (foundTarget == null) return positions;

      positions.add(foundTarget);
      SignalGui.getInstance().getGraphics().setColor(Color.BLUE);

      while (foundTarget.getFrom() != null) {
        // Position position = foundTarget.toCoordinate();
        // SignalGui.getInstance().getGraphics().drawRect(position.getX() + Coordinates.COORDINATE_SIZE / 2, position.getY() + Coordinates.COORDINATE_SIZE / 2, 6, 6);

        PathPosition pathPosition = foundTarget;
        foundTarget = foundTarget.getFrom();
        positions.add(foundTarget);
        Optional<PositionData> from = pathPosition.getPositionData();

        if (foundTarget.getFrom() != null) {
          foundTarget.getPositionData().ifPresent(data -> data.setSet(true));
        }

        PathPosition targetFrom = pathPosition.getFrom();
        if (targetFrom == null) break;
        PathPosition targetFromFrom = targetFrom.getFrom();
        if (targetFromFrom == null) continue;

        Optional<PositionData> through = targetFrom.getPositionData();
        Optional<PositionData> to = targetFromFrom.getPositionData();

        if (!to.isPresent() || !through.isPresent() || !from.isPresent()) continue;

        PositionData fromData = from.get();
        PositionData throughData = through.get();
        PositionData toData = to.get();

        throughData.set(fromToCheck(fromData.getPosition(), throughData.getPosition(), toData.getPosition()));
        if (throughData instanceof StartPositionData) {
          StartPositionData startPositionData = (StartPositionData) throughData;
          startPositionData.setSignalPosition(SignalPosition.DRIVE);
        }
      }
      if (frt) {
        SignalGui.getInstance().getFfRT().use();
      }
      start.getPositionData().ifPresent(data -> ((StartPositionData) data).setSignalPosition(SignalPosition.DRIVE));

      if (positions.size() > 1) {
        positions.get(positions.size() - 1).getPositionData().ifPresent(data -> positions.get(positions.size() - 2).getPositionData().ifPresent(data1 -> {
          if (data instanceof StartPositionData) {
            ((StartPositionData) data).setNext(data1.getLocation());
          }
        }));
      }

      return positions;
    });
  }

  private byte fromToCheck(Position from, Position through, Position to) {
    if (from.getX() == through.getX() && from.getY() - 1 == through.getY() &&
            from.getX() + 1 == to.getX() && from.getY() - 1 == to.getY()
            || from.getX() - 1 == through.getX() && from.getY() == through.getY() &&
            from.getX() - 1 == to.getX() && from.getY() + 1 == to.getY()) {
      return (byte) 6;
    } else if (from.getX() + 1 == through.getX() && from.getY() == through.getY() &&
            from.getX() + 1 == to.getX() && from.getY() + 1 == to.getY()
            || from.getX() == through.getX() && from.getY() - 1 == through.getY() &&
            from.getX() - 1 == to.getX() && from.getY() - 1 == to.getY()) {
      return (byte) 7;
    } else if (from.getX() == through.getX() && from.getY() + 1 == through.getY() &&
            from.getX() - 1 == to.getX() && from.getY() + 1 == to.getY()
            || from.getX() + 1 == through.getX() && from.getY() == through.getY() &&
            from.getX() + 1 == to.getX() && from.getY() - 1 == to.getY()) {
      return (byte) 8;
    } else if (from.getX() - 1 == through.getX() && from.getY() == through.getY() &&
            from.getX() - 1 == to.getX() && from.getY() - 1 == to.getY()
            || from.getX() == through.getX() && from.getY() + 1 == through.getY() &&
            from.getX() + 1 == to.getX() && from.getY() + 1 == to.getY()) {
      return (byte) 9;
    } else if (from.getX() == through.getX() && through.getX() == to.getX()) {
      return (byte) 0;
    } else if (from.getY() == through.getY() && through.getY() == to.getY()) {
      return (byte) 1;
    }

    return 0;
  }

  private void run() {
    PathPosition lastBest = getBest();
    openList.remove(lastBest);
    if (lastBest.equals(target)) {
      foundTarget = lastBest;
      return;
    }

    closedList.add(lastBest);

    check(new Position(lastBest.getX() + 1, lastBest.getY()), lastBest);
    check(new Position(lastBest.getX() - 1, lastBest.getY()), lastBest);
    check(new Position(lastBest.getX(), lastBest.getY() + 1), lastBest);
    check(new Position(lastBest.getX(), lastBest.getY() - 1), lastBest);
  }

  private void check(Position position, PathPosition from) {
    if (openList.stream().anyMatch(pathPosition -> pathPosition.getX() == position.getX() && pathPosition.getY() == position.getY())
            || closedList.stream().anyMatch(pathPosition -> pathPosition.getX() == position.getX() && pathPosition.getY() == position.getY()))
      return;


    if (coordinates.getPositions().keySet().stream().anyMatch(position1 -> position.getX() == position1.getX() && position.getY() == position1.getY())) {
      if (coordinates.getPositions().keySet().stream().anyMatch(position1 -> position.getX() == position1.getX() && position.getY() == position1.getY())) {
        Optional<PositionData> positionData = position.getPositionData();
        if (positionData.isPresent()) {
          if (positionData.get().isSet()) return;
          if (positionData.get().isOccupied()) {
            if (FfRT.pressed) {
              frt = true;
            } else if(!position.equals(start)) return;
          }
        } else return;
        openList.add(new PathPosition(position.getX(), position.getY(), from, calculateDistance(position, target)));
      }
    }
  }

  private PathPosition getBest() {
    PathPosition pathPosition = openList.get(0);
    for (int i = 1; i < openList.size(); i++) {
      PathPosition position = openList.get(i);
      if (pathPosition.getTotalPrice() > position.getTotalPrice()) pathPosition = position;
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
