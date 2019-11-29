package com.tema1.main;

import java.util.Map;

import com.tema1.player.FinalScoreCalculator;
import com.tema1.player.LeaderboardCreator;
import com.tema1.player.Player;
import com.tema1.player.ShareGoods;

public class GameLogic {

  public final Map<Integer, Player> createPlayers(final GameInput gameInput) {
    ShareGoods sg = new ShareGoods();
    Map<Integer, Player> players = sg.initialShare(gameInput);
    return players;
  }

  public final void gameEvolution(final int roundsNumber, final Map<Integer, Player> players,
      final GameInput gameInput) {
    int serif = 0;
    for (int round = 1; round <= roundsNumber; round++) {
      for (int subround = 0; subround < players.size(); subround++) {
        // seriful corespunde cu numarul rundei
        serif = subround;
        // fiecare jucator "arde" cartile din mana pentru a primi altele noi
        if (round != 1 || subround != 0) {
          for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getGoodsInHand() != null) {
              players.get(i).getGoodsInHand().clear();
            }
            if (i != serif) {
              players.get(i).handRefill(gameInput);
            }
          }
        }
        // fiecare jucator declara bunurile pe care sa le bage in sac
        for (Player x : players.values()) {
          x.goodsDeclaration(round);
        }
        // seriful inspecteaza jucatorii
        players.get(serif).inspect(players, gameInput);
      }
    }

    // calcul profit pentru bunurile de pe taraba
    FinalScoreCalculator scoreCalculator = new FinalScoreCalculator();
    for (Player x : players.values()) {
      scoreCalculator.calculateProfit(x);
    }
    // adaugare bonusuri de king si queen
    scoreCalculator.decideKingAndQueen(players);
    LeaderboardCreator leaderboard = new LeaderboardCreator();
    // creeaza si afiseaza clasamentul jucatorilor in functie de banii detinuti
    leaderboard.getLeaderboard(players);
  }
}
