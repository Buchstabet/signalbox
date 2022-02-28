package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.coordinates.Coordinates;
import dev.buchstabet.signalbox.minecraftadapter.MinecraftAdapter;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

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
    setSize(1700, 900);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);



    new SignalMouseListener(this);

    addMouseWheelListener(e -> Coordinates.COORDINATE_SIZE = Math.max(Coordinates.COORDINATE_SIZE + e.getWheelRotation(), 5));
  }

  @SneakyThrows
  @Override
  public void paint(Graphics g) {
    try {

      URL url = SignalGui.class.getClassLoader().getResource("logo.png");
      URLConnection urlConnection = url.openConnection();
      urlConnection.setUseCaches(false);
      BufferedImage read = ImageIO.read(urlConnection.getInputStream());
      g.drawImage(read, 50, 40, null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    coordinates.draw(g, this);
    g.drawString("Size: " + Coordinates.COORDINATE_SIZE, (int) getSize().getWidth() - 90, 90);
  }
}
