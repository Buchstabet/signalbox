package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;
import java.awt.event.ActionEvent;

public class FfRT extends HelpButton {

  public static boolean pressed;

  public FfRT(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pressed = !pressed;
    setBackground(pressed ? Color.RED : Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("FfRT was pressed");
  }

  public void use() {
    pressed = false;
    setBackground(Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("FfRT was used");
  }

}
