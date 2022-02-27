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
  }

  @Override
  public void paint(Graphics g) {
    coordinates.draw(this, g);
  }
}
