package dev.buchstabet.signalbox.helpbuttons;

import dev.buchstabet.signalbox.coordinates.Position;
import dev.buchstabet.signalbox.coordinates.PositionData;
import dev.buchstabet.signalbox.gui.SignalGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FHT extends HelpButton {

  public static boolean pressed;

  public FHT(int x, int y, String text) {
    super(x, y, text);
    setBackground(Color.GREEN);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    pressed = !pressed;
    setBackground(pressed ? Color.RED : Color.GREEN);
  }

  public static void fht(Position position) {
    new FHTTask().start(position);
    pressed = false;
    SignalGui signalGui = SignalGui.getInstance();
    signalGui.getFht().setBackground(Color.GREEN);
    signalGui.repaint();
  }

  private static class FHTTask {

    private final LinkedList<Position> left = new LinkedList<>();
    private final List<Position> closed = new ArrayList<>();

    public void start(Position position) {
      checkPosition(position);

      while (!left.isEmpty()) {
        handlePosition();
      }
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
        if (data.get().isSet()) {
          data.get().setSet(false);
        } else return;
      } else return;

      checkPosition(position);
    }

    private void check(Position position) {
      if (closed.stream().anyMatch(position1 -> position1.getX() == position.getX() && position1.getY() == position.getY())) {
        return;
      }

      left.add(position);
    }


  }

}
