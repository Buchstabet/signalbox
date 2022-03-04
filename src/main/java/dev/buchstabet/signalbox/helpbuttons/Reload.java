package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.Signalbox;
import org.bukkit.Bukkit;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Reload extends HelpButton {

  public Reload(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Bukkit.getScheduler().runTaskLater(Signalbox.getPlugin(Signalbox.class), Bukkit::reload, 20);
  }

}
