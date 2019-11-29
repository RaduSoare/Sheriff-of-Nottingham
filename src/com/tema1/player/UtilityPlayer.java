package com.tema1.player;

import com.tema1.goods.Goods;

public final class UtilityPlayer {

  private UtilityPlayer() {

  }

  public static void acceptBribe(final Player sheriff, final Player inspected) {
    sheriff.setCoins(sheriff.getCoins() + inspected.getBribe());
    inspected.setCoins(inspected.getCoins() - inspected.getBribe());
  }

  public static void applyPenalty(final Player player1, final Player player2, final Goods good) {
    player1.setCoins(player1.getCoins() - good.getPenalty());
    player2.setCoins(player2.getCoins() + good.getPenalty());
  }
}
