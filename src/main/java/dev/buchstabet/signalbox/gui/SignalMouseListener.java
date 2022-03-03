package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SignalMouseListener implements MouseListener {

    private final SignalGui signalGui;

    private Position start;

    public SignalMouseListener(SignalGui jFrame) {
        jFrame.addMouseListener(this);
        this.signalGui = jFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (true)
            return;

        Position position = Position.fromXY(e.getX(), e.getY());

        if (signalGui.getCoordinates().getPositions().keySet().stream().noneMatch(position1 -> position1.getX() == position.getX() && position1.getY() == position.getY())) return;

        if (start == null) {
            start = position;

            System.out.println("Start set " + position.getX() + " " + position.getY());
        } else {
            System.out.println("Target set " + position.getX() + " " + position.getY());

            signalGui.getGraphics().drawOval(start.toCoordinate().getX() + 5, start.toCoordinate().getY()+ 5, 2, 2);
            signalGui.getGraphics().drawOval(position.toCoordinate().getX() + 5, position.toCoordinate().getY()+ 5, 2, 2);

            PathfinderAlgorithm pathfinderAlgorithm = new PathfinderAlgorithm(start, position, SignalGui.getInstance().getCoordinates());
            pathfinderAlgorithm.paintBestWay();
            start = null;
        }

        // signalGui.getCoordinates().findPositionData(e.getX() - Position.xMove, e.getY() - Position.yMove).ifPresent(positionData -> positionData.handleClick(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
