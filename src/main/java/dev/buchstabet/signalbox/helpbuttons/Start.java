package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.coordinates.SwitchPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Start extends HelpButton {

  public Start(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    setBackground(Color.GREEN);
    SwitchPositionData.start = null;
    SignalGui.getInstance().getLogFrame().log("Start was pressed");
  }

}
