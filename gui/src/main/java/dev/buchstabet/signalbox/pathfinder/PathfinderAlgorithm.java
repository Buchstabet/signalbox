package dev.buchstabet.signalbox.pathfinder;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

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

        if (foundTarget == null) return;

        SignalGui.getInstance().getGraphics().setColor(Color.BLUE);
        while (foundTarget.getFrom() != null) {
            // Position position = foundTarget.toCoordinate();
            // SignalGui.getInstance().getGraphics().drawRect(position.getX() + Coordinates.COORDINATE_SIZE / 2, position.getY() + Coordinates.COORDINATE_SIZE / 2, 6, 6);

            PathPosition pathPosition = foundTarget;
            foundTarget = foundTarget.getFrom();

            PathPosition targetFrom = pathPosition.getFrom();
            if (targetFrom == null) break;
            PathPosition targetFromFrom = targetFrom.getFrom();
            if (targetFromFrom == null) continue;

            Optional<PositionData> from = pathPosition.getPositionData();
            Optional<PositionData> through = targetFrom.getPositionData();
            Optional<PositionData> to = targetFromFrom.getPositionData();

            if (!from.isPresent() || !to.isPresent() || !through.isPresent()) continue;

            PositionData fromData = from.get();
            PositionData throughData = through.get();
            PositionData toData = to.get();

            throughData.set(fromToCheck(fromData.getPosition(), throughData.getPosition(), toData.getPosition()));
            throughData.setSet(true);
        }

        SignalGui.getInstance().getGraphics().setColor(Color.BLACK);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SignalGui.getInstance().repaint();
            }
        }, 3000);
    }

    private byte fromToCheck(Position from, Position through, Position to) {
        if (from.getX() == through.getX() && from.getY() - 1 == through.getY() &&
                from.getX() + 1 == to.getX() && from.getY() - 1 == to.getY()
                || from.getX() -1 == through.getX() && from.getY() == through.getY() &&
                from.getX() - 1 == to.getX() && from.getY() + 1 == to.getY()) {
            return (byte) 6;
        } else if (from.getX() + 1 == through.getX() && from.getY() == through.getY() &&
                from.getX() + 1 == to.getX() && from.getY() + 1 == to.getY()
                || from.getX() == through.getX() && from.getY() - 1 == through.getY() &&
                from.getX() - 1 == to.getX() && from.getY() - 1 == to.getY()) {
            return (byte) 7;
        } else if (from.getX() == through.getX() && from.getY() + 1 == through.getY() &&
                from.getX() - 1 == to.getX() && from.getY() + 1 == to.getY()
                || from.getX() + 1== through.getX() && from.getY() == through.getY() &&
                from.getX() + 1 == to.getX() && from.getY() - 1 == to.getY()) {
            return (byte) 8;
        } else if (from.getX() - 1 == through.getX() && from.getY() == through.getY() &&
                from.getX() - 1 == to.getX() && from.getY() - 1 == to.getY()
                || from.getX() == through.getX() && from.getY() + 1 == through.getY() &&
                from.getX() + 1 == to.getX() && from.getY() + 1 == to.getY()) {
            return (byte) 9;
        } else if (from.getX() == through.getX() && through.getX() == to.getX() ) {
            return (byte) 0;
        } else if (from.getY() == through.getY() && through.getY() == to.getY() ) {
            return (byte) 1;
        }

        return 0;
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

        Optional<PositionData> any = coordinates.getPositions().keySet().stream().filter(position1 -> position1.equals(position)).map(position1 -> coordinates.getPositions().get(position1)).findAny();
        if (any.isPresent() && any.get().isSet()) return;

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
