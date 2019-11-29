package com.tema1.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;

public class ShareGoods {

  private Map<Integer, Player> players;

  public ShareGoods() {
    this.players = new HashMap<Integer, Player>();
  }
/**
 *
 * @param gameInput
 * @return hash cu caracteristicile initiale ale fiecarui jucator
 */
  public Map<Integer, Player> initialShare(final GameInput gameInput) {
    // creeaza un hash initial de jucatori
    GoodsFactory goodsfactoryInstance = GoodsFactory.getInstance();
    int index = 0;
    int capacity = Constants.getHandCapacity();

    for (int i = 0; i < gameInput.getPlayerNames().size(); i++) {
      LinkedList<Goods> playersGoods = new LinkedList<Goods>();
      String playerType = gameInput.getPlayerNames().get(i);
      // initial jucatorul 0 este serif
      // in afara de serif fiecare primeste 10 carti
      if (i > 0) {
        for (int j = index; j < capacity; j++) {

          Goods temp = goodsfactoryInstance.getGoodsById(gameInput.getAssetIds().get(j));
          playersGoods.add(temp);
        }
        index += Constants.getHandCapacity();
        capacity += Constants.getHandCapacity();
      }

      Player tempPlayer = null;
      if (playerType.equals("basic")) {
        if (i == 0) {
          tempPlayer = new BasePlayer(i, Constants.getInitialCoins(), 0, playerType,
              null, null, null, -1, 0);
        } else {
          tempPlayer = new BasePlayer(i, Constants.getInitialCoins(), 0, playerType,
              playersGoods, null, null, -1, 0);
        }

      }
      if (playerType.equals("greedy")) {
        if (i == 0) {
          tempPlayer = new GreedyPlayer(i, Constants.getInitialCoins(), 0, playerType,
              null, null, null, -1, 0);
        } else {
          tempPlayer = new GreedyPlayer(i, Constants.getInitialCoins(), 0, playerType,
              playersGoods, null, null, -1, 0);
        }

      }
      if (playerType.equals("bribed")) {
        if (i == 0) {
          tempPlayer = new BribedPlayer(i, Constants.getInitialCoins(), 0, playerType,
              null, null, null, -1, 0);
        } else {
          tempPlayer = new BribedPlayer(i, Constants.getInitialCoins(), 0, playerType,
              playersGoods, null, null, -1, 0);
        }

      }
      players.put(i, tempPlayer);

    }

    // elimina bunurile impartite
    int numOfAssets = Constants.getHandCapacity() * (gameInput.getPlayerNames().size() - 1);
    for (int i = 0; i < numOfAssets; i++) {
      gameInput.getAssetIds().remove(0);
    }

    return this.players;
  }

}
