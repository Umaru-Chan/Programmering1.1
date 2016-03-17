/**
 * Förbättrad av Niklas Te14d
 * Grön text är bättre än grå.
 **/

import javax.swing.*;
import java.awt.*;

public class Luffarschack10b {
  static String s = JOptionPane.showInputDialog(null, "Antal rutor i fönstret(X * X)");
  static int size = Integer.parseInt(s);

  public static void main(String[] args) {
    SimpleWindow w = new SimpleWindow((700), (700), "LuffarSchack");
    welcome(w);
    Square sqStart = new Square(w.getWidth() / 3, w.getHeight() / 2, w.getWidth() / 10);
    w.moveTo(w.getWidth() / 3, w.getHeight() / 2 + 20);
    w.writeText("Start");
    sqStart.draw(w);

    w.waitForMouseClick();
    w.clear();
    int mX = w.getMouseX();
    int mY = w.getMouseY();

    if (mX > sqStart.getX() && mX < (sqStart.getX() + sqStart.getSide()) && mY > sqStart.getY() && mY < (sqStart.getY() + sqStart.getSide()))
      rita(w);
    else
      System.exit(0);

  }

  /** main **/

  private static void welcome(SimpleWindow w) {
    int x = w.getWidth() / size;
    int y = w.getHeight() / size;
    w.moveTo(x, y);
    w.writeText("Välkommen till Luffarschack.");
    w.moveTo(x, y + 20);
    w.writeText("Det går ut på att få tre i rad. Du ritar själv ut kryssen.");
    w.moveTo(x, y + 40);
    w.writeText("Var fjärde musklick så byter den färg, så var varsam om dina musklick.");
    w.moveTo(x, y + 60);
    w.writeText("Den som börjar får svart färg och den andra röd.");
    w.moveTo(x, y + 80);
    w.writeText("Starta spelet genom att clicka på Start");
  }

  /** welcome **/

  private static void kryss(SimpleWindow w, Square[] sq) {
    w.setLineWidth(10/size);
    int index;
    int redBlack[] = new int[size * size];

    while (!vinst(redBlack, sq)) {
      w.waitForEvent();
      index = getIndex(w.getMouseX(), w.getMouseY());
      if (redBlack[index] == 0) {
        if (w.getLineColor() == Color.RED) {
          w.setLineColor(Color.BLACK);
          w.moveTo(sq[index].getX(), sq[index].getY());
          w.lineTo(sq[index].getX() + w.getWidth() / size, sq[index].getY() + w.getWidth() / size);
          w.moveTo(sq[index].getX() + w.getWidth() / size, sq[index].getY());
          w.lineTo(sq[index].getX(), sq[index].getY() + w.getWidth() / size);
          redBlack[index] = 1;
        } else {
          w.setLineColor(Color.RED);
          int x = sq[index].getX() + (sq[index].getSide() / 2);
          int y = sq[index].getY() + (sq[index].getSide() / 2);
          w.moveTo(x, y + w.getWidth()/3/size);
          for (int i = 0; i < 360; i++) {
            w.lineTo((int) (Math.sin(i) * w.getWidth()/3/size) + x, (int) (Math.cos(i) * w.getWidth()/3/size) + y);
          }
          redBlack[index] = -1;
        }
      }
    }/** While loop **/
  }/** Kryss metoden **/

  private static int getIndex(int x, int y) {
    return x / (700 / size) + y / (700 / size) * size;
  }

  private static void rita(SimpleWindow w) {
    Square sqList[] = new Square[size * size];
    for (int i = 0; i < sqList.length; i++) {
      sqList[i] = new Square((i % size) * w.getWidth() / size, i / size * w.getWidth() / size, w.getWidth() / size);
      sqList[i].draw(w);
    }
    kryss(w, sqList);
  }/** rita **/

  /** Vågrätt **/
  private static boolean vinst(int[] rb, Square[] sq) {
    for (int i = 0; i < rb.length - 2; i++) {
      if (Math.abs(rb[i] + rb[i + 1] + rb[i + 2]) == 3 && sq[i].getY() == sq[i + 2].getY())
        return true;
    }

    /** Lodrätt **/
    for (int i = 0; i < rb.length - size*2; i++) {
      if (Math.abs(rb[i] + rb[i + size] + rb[i + size*2]) == 3)
        return true;
    }

    /** Diagonalen åt höger **/
    for (int i = 0; i < rb.length - size*2-2; i++) {
      if (Math.abs(rb[i] + rb[i + size+1] + rb[i + size*2+2]) == 3 && sq[i].getX() == sq[i+size*2+2].getX() - sq[0].getSide()*2)
        return true;
    }

    /** Diagonalen åt vänster **/
    for (int i = 0; i < rb.length - size*2; i++) {
      if (Math.abs(rb[i] + rb[i + size-1] + rb[i + size*2-2]) == 3 && sq[i].getX() == sq[i+size*2-2].getX() + sq[0].getSide()*2)
        return true;
    }
    return false;
  }/** vinst metoden **/
} /** class**/

