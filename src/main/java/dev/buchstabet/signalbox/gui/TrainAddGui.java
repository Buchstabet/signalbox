package dev.buchstabet.signalbox.gui;

import dev.buchstabet.signalbox.Signalbox;
import dev.buchstabet.signalbox.coordinates.SignalPositionData;
import dev.buchstabet.signalbox.pathfinder.PathPosition;
import dev.buchstabet.signalbox.pathfinder.PathfinderAlgorithm;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class TrainAddGui extends JFrame {

  private final SignalPositionData signalPositionData;

  public TrainAddGui(SignalPositionData signalPositionData) {
    this.signalPositionData = signalPositionData;
    SignalGui signalGui = SignalGui.getInstance();
    signalGui.getLogFrame().log("CodeNumber was pressed");

    JPanel jPanel = new JPanel();


    setSize(400, 300);
    jPanel.setSize(400, 300);
    setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    // jPanel.setIconImage(icon);
    setResizable(false);
    setAlwaysOnTop(true);

    jPanel.setLayout(null);

    JTextField identifier = new JTextField(Integer.toString(new Random().nextInt(99)));
    identifier.setBounds(20, 50, 170, 50);
    identifier.setBackground(Color.lightGray);
    jPanel.add(identifier);

    JButton ok = new JButton("Zug erstellen");
    ok.setBounds(20, 150, 170, 30);

    ok.addActionListener(e1 -> {

      setVisible(false);
      int identifierInt;

      try {
        identifierInt = Integer.parseInt(identifier.getText());
      } catch (NumberFormatException exception) {
        exception.printStackTrace();
        return;
      } finally {
        setVisible(false);
      }


      Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Signalbox.class), () -> {
        Minecart minecart = (Minecart) JavaPlugin.getPlugin(Signalbox.class).getStart().getWorld().spawnEntity(signalPositionData.getLocation(), EntityType.MINECART);
        SignalPositionData.REGISTERED_MINECART.put(minecart.getUniqueId(), identifierInt);
        signalPositionData.setOccupied(minecart);

        signalGui.
                getCodeNumberLoader().
                getCodeNumbers().
                getCodeNumbers().
                stream().
                filter(codeNumber -> codeNumber.getIdentifier() == SignalPositionData.REGISTERED_MINECART.get(minecart.getUniqueId())).
                findAny().flatMap(codeNumber -> codeNumber.getRailRoutes().stream().filter(railRoute -> railRoute.getStart().getPosition().equals(signalPositionData.getPosition())).findAny()).
                ifPresent(railRoute -> {
                  CompletableFuture<Collection<PathPosition>> future = new PathfinderAlgorithm(railRoute.getStart().getPosition(), railRoute.getTarget().getPosition(), signalGui.getCoordinates()).paintBestWay();
                  future.thenAccept(pathPositions -> {
                    signalGui.repaint();
                    signalGui.getStart().setBackground(Color.GREEN);
                    if (!pathPositions.isEmpty()) signalGui.getLogFrame().log("Der Selbststellbetrieb wurde aktiv");
                  });
                });
      });
    });
    ok.setBackground(Color.green);

    jPanel.add(ok);

    add(jPanel);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawString("Kennziffer", 20, 60);
  }
}
