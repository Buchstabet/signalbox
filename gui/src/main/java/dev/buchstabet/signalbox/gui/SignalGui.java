package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@RequiredArgsConstructor
public class SignalGui extends JFrame {

  private final Coordinates coordinates;

  public void display() {
    setTitle("Signalbox");
    setSize(1700, 700);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        setVisible(true);
      }
    });

    addMouseWheelListener(e -> {
      Coordinates.COORDINATE_SIZE = Math.max(Coordinates.COORDINATE_SIZE + e.getWheelRotation(), 10);
      // SwingUtilities.updateComponentTreeUI(this);
      // paint(getGraphics());

      setVisible(true);
    });
  }

  @Override
  public void paint(Graphics g) {
    coordinates.draw(g);
    g.drawString("Size: " + Coordinates.COORDINATE_SIZE, (int) getSize().getWidth() - 100, 40);

  }
}
