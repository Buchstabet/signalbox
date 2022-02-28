package dev.buchstabet.signalbox.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SignalMouseListener implements MouseListener {

    private final SignalGui signalGui;

    public SignalMouseListener(SignalGui jFrame) {
        jFrame.addMouseListener(this);
        this.signalGui = jFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        signalGui.getCoordinates().findPositionData((int) (e.getX() - signalGui.getSize().getWidth() / 2), (int) (e.getY() - signalGui.getSize().getHeight() / 2)).ifPresent(positionData -> positionData.handleClick(e));
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
