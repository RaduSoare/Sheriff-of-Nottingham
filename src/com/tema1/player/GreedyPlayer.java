package com.tema1.player;

import java.util.LinkedList;
import java.util.Map;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

public class GreedyPlayer extends BasePlayer {

  public GreedyPlayer() {
    super();
  }

  public GreedyPlayer(final int id, final int coins, final int bonus,
      final String type, final LinkedList<Goods> goodsInHand, final LinkedList<Goods> goodsOnStand,
      final LinkedList<Goods> goodsInBag, final int goodDeclared, final int bribe) {
    super(id, coins, bonus, type, goodsInHand, goodsOnStand, goodsInBag, goodDeclared,
        bribe);
  }
  /**
   * Joaca strategia de baza si in rundele pare introduce un bun ilegal.
   *
   * @param round - runda la care se afla jocul in momentul apelarii functiei
   */
  @Override
  public void goodsDeclaration(final int round) {
    // in general joaca cu strategia de baza
    super.goodsDeclaration(round);
    int size = 0;
    if (this.getGoodsInBag() == null) {
      size = 0;
    } else {
      size = this.getGoodsInBag().size();
    }
    // in runde pare, introduce un bun ilegale
    if (round % 2 == 0 && size < Constants.getBagCapacity()) {
      int index = 0;
      if (this.getGoodsInHand() == null) {
        return;
      }
      // cazul in care nu exista bunuri ilegale
      if (this.getMostProfitable(GoodsType.Illegal) == -1) {
        return;
      }
      for (Goods x : this.getGoodsInHand()) {
        if (x.getId() == this.getMostProfitable(GoodsType.Illegal)) {

          this.getGoodsInBag().add(Constants.getGoodsfactory().getGoodsById(x.getId()));
          this.getGoodsInHand().remove(index);
          break;
        }
        index++;
      }
    }

  }
  /**
   * @param players - hash cu starea fiecarui jucator
   * @param gameInput - ofera acces la cartile neimpartite
   */
  @Override
  public void inspect(final Map<Integer, Player> players, final GameInput gameInput) {

    for (Player x : players.values()) {
      if (x != this) {
        if (x.getBribe() == 0 && this.getCoins() >= Constants.getNoMoney()) {
          this.doInspection(x, gameInput);
        } else {
          this.doNotInspect(x, gameInput);
        }
      }

    }
  }

}
