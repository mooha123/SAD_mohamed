/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1.Segon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Anna
 */

class TestReadLine {
  public static void main(String[] args) {
    BufferedReader in = new EditableBufferedReader(
      new InputStreamReader(System.in));
    String str = null;
    try {
      str = in.readLine();
    } catch (IOException e) { e.printStackTrace(); }
    System.out.println("\nline is: " + str);
  }
}