package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.minecraftadapter.MinecraftAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Getter
public class SignalGui extends JFrame {

  private final Coordinates coordinates;
  private final MinecraftAdapter minecraftAdapter;
  @Getter private static SignalGui instance;

  public SignalGui(Coordinates coordinates, MinecraftAdapter minecraftAdapter) {
    this.coordinates = coordinates;
    this.minecraftAdapter = minecraftAdapter;

    instance = this;
  }

  public void display() {
    setTitle("Signalbox");
    setSize(1700, 700);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        setVisible(true);
      }
    });

    new SignalMouseListener(this);

    addMouseWheelListener(e -> {
      Coordinates.COORDINATE_SIZE = Math.max(Coordinates.COORDINATE_SIZE + e.getWheelRotation(), 5);
      // SwingUtilities.updateComponentTreeUI(this);
      // paint(getGraphics());

      setVisible(false);
      setVisible(true);
    });
  }

  @Override
  public void paint(Graphics g) {
    coordinates.draw(g, this);
    g.drawString("Size: " + Coordinates.COORDINATE_SIZE, (int) getSize().getWidth() - 100, 40);

  }
}
