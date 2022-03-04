package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;
import java.awt.event.ActionEvent;

public class HaGT extends HelpButton {

  public static boolean pressed;

  public HaGT(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pressed = !pressed;
    setBackground(pressed ? Color.RED : Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("HaGT was pressed");
  }

  public void use() {
    pressed = false;
    setBackground(Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("HaGT was used");
  }

}
