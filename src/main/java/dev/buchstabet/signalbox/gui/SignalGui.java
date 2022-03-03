package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.helpbuttons.FHT;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

@Getter
public class SignalGui extends JFrame {

  private final Coordinates coordinates;
  @Getter
  private static SignalGui instance;
  private final BufferedImage icon;

  private final JScrollBar barVertical = new JScrollBar(JScrollBar.VERTICAL, 250, 40, 0, 500);
  private final JScrollBar barHorizontal = new JScrollBar(JScrollBar.HORIZONTAL, 250, 20, 0, 500);

  private final JPanel panel;
  private final FHT fht;
  private final JTextField jTextField = new JTextField("Zoom: " + Coordinates.COORDINATE_SIZE);


  public SignalGui(Coordinates coordinates, BufferedImage icon) {
    this.coordinates = coordinates;

    instance = this;

    this.icon = icon;

    jTextField.setEditable(false);
    jTextField.setBackground(Color.white);

    setTitle("Signalbox");
    setSize(1700, 900);


    Position.xMove = calculateSize(barHorizontal.getValue());
    Position.yMove = calculateSize(barVertical.getValue());

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setIconImage(icon);

    panel = new JPanel();
    panel.setBackground(Color.black);

    add(panel);

    java.util.Timer timer = new java.util.Timer();

    addMouseWheelListener(e -> {
      Coordinates.COORDINATE_SIZE = Math.max(5, Coordinates.COORDINATE_SIZE + e.getWheelRotation());
      jTextField.setText("Zoom: " + Coordinates.COORDINATE_SIZE);
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          repaint();
        }
      }, 100);
    });

    {

      getContentPane().add(barVertical, BorderLayout.EAST);
      barVertical.addAdjustmentListener(e -> {
        Position.yMove = calculateSize(e.getValue());
        repaint();
      });
    }

    {
      getContentPane().add(barHorizontal, BorderLayout.SOUTH);
      barHorizontal.addAdjustmentListener(e -> {
        Position.xMove = calculateSize(e.getValue());
        repaint();
      });
    }

    panel.setLayout(null);

    new SignalMouseListener(this);
    // addMouseWheelListener(e -> Coordinates.COORDINATE_SIZE = Math.max(Coordinates.COORDINATE_SIZE + e.getWheelRotation(), 5));

    panel.add(jTextField);
    jTextField.setBounds(50, 50, 70, 30);
    jTextField.setBackground(Color.YELLOW);
    jTextField.setHorizontalAlignment(JTextField.CENTER);

    fht = new FHT(50, 85, "FHT");
    panel.add(fht);
  }

  public int calculateSize(int value) {
    return (500 - (value * 20 - 250 * 20));
  }

  @SneakyThrows
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    panel.setBounds(0, 0, barVertical.getX(), barHorizontal.getY());
    coordinates.draw(panel.getGraphics());
  }
}
