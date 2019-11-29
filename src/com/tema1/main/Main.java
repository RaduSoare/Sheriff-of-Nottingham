package com.tema1.main;

import java.util.Map;

import com.tema1.player.Player;

public final class Main {

  private Main() {
    // just to trick checkstyle
  }

  public static void main(final String[] args) {

    GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
    GameInput gameInput = gameInputLoader.load();
    GameLogic game = new GameLogic();
    Map<Integer, Player> players = game.createPlayers(gameInput);
    game.gameEvolution(gameInput.getRounds(), players, gameInput);

  }
}
