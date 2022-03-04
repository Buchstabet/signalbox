package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.coordinates.StartPositionData;
import dev.buchstabet.signalbox.gui.SignalGui;
import dev.buchstabet.signalbox.signal.SignalPosition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FRT extends HelpButton {

  public static boolean pressed;

  public FRT(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pressed = !pressed;
    setBackground(pressed ? Color.RED : Color.GREEN);

    SignalGui.getInstance().getLogFrame().log("FRT was pressed");
  }

  public static void frt(Position position) {
    new FRTTask().start(position);
    pressed = false;
    SignalGui signalGui = SignalGui.getInstance();
    signalGui.getFRT().setBackground(Color.GREEN);
    SignalGui.getInstance().getLogFrame().log("FRT was used");
  }

  private static class FRTTask {

    private final LinkedList<Position> left = new LinkedList<>();
    private final List<Position> closed = new ArrayList<>();

    public void start(Position position) {
      checkPosition(position);

      while (!left.isEmpty()) {
        handlePosition();
      }

      position.getPositionData().ifPresent(data -> data.draw(SignalGui.getInstance().getPanel().getGraphics()));
    }

    private void checkPosition(Position position) {
      check(new Position(position.getX() + 1, position.getY()));
      check(new Position(position.getX() - 1, position.getY()));
      check(new Position(position.getX(), position.getY() + 1));
      check(new Position(position.getX(), position.getY() - 1));
    }

    private void handlePosition() {
      Position position = left.removeFirst();
      closed.add(position);
      Optional<PositionData> data = position.getPositionData();
      if (data.isPresent()) {
        if (data.get().isSet() || data.get() instanceof StartPositionData && ((StartPositionData) data.get()).getSignal().equals(SignalPosition.DRIVE)) {
          data.get().setSet(false);
          data.get().draw(SignalGui.getInstance().getPanel().getGraphics());
        } else return;
      } else return;

      checkPosition(position);
    }

    private void check(Position position) {
      Optional<PositionData> positionData = position.getPositionData();
      if (!(positionData.isPresent() && positionData.get() instanceof StartPositionData) && closed.stream().anyMatch(position1 -> position1.getX() == position.getX() && position1.getY() == position.getY())) {
        return;
      }

      left.add(position);
    }


  }

}
