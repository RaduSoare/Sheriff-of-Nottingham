package com.tema1.player;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardCreator {
  /**
   * Creeaza si afiseaza un clasament al jucatorilor in functie de profit.
   * @param players
   */
  public void getLeaderboard(final Map<Integer, Player> players) {
    // creaza un hash cu id-ul jucatorilor si profitul lor
    HashMap<Integer, Integer> profitHash = new HashMap<Integer, Integer>();
    int index = 0;
    for (Player x : players.values()) {
      profitHash.put(index, x.getCoins());
      index++;
    }

    HashMap<Integer, Integer> sortedMap = FinalScoreCalculator.sortByValue(profitHash);
    for (Integer x : sortedMap.keySet()) {

      System.out.println(x + " " + players.get(x).getType().toUpperCase() + " " + sortedMap.get(x));
    }

  }

}
