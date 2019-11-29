package com.tema1.player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

public class BribedPlayer extends BasePlayer {

  public BribedPlayer() {
    super();

  }

  public BribedPlayer(final int id, final int coins, final int bonus,
      final String type, final LinkedList<Goods> goodsInHand, final LinkedList<Goods> goodsOnStand,
      final LinkedList<Goods> goodsInBag, final int goodDeclared, final int bribe) {
    super(id, coins, bonus, type, goodsInHand, goodsOnStand, goodsInBag, goodDeclared,
        bribe);
  }

  class MyProfitComp implements Comparator<Goods> {
    /*
     * comparator pentru sortarea in functie de profit, apoi id
     */
    @Override
    public int compare(final Goods g1, final Goods g2) {
      if (g1.getProfit() == g2.getProfit() && g1.getId() < g2.getId()) {
        return 1;
      } else
        if (g1.getProfit() < g2.getProfit()) {
          return 1;
        } else {
        return -1;
      }
    }
  }
  /**
   * Daca are, declara cat mai multe bunuri ilegale, altfel joaca strategia de baza.
   *
   * @param round - runda la care se afla jocul in momentul apelarii functiei
   */
  @Override
  public void goodsDeclaration(final int round) {

    if (this.getGoodsInHand() != null && this.getGoodsInHand().size() != 0) {

      LinkedList<Goods> tempList = null;
      // verific daca are doar bunuri legale
      boolean hasOnlyLegals = true;
      for (Goods x : this.getGoodsInHand()) {
        if (x.getType() == GoodsType.Illegal) {
          hasOnlyLegals = false;
          break;
        }
      }

   // daca are doar bunuri legale sau nu are suficenti bani pentru mita, alege strategia de baza
      if (hasOnlyLegals || this.getCoins() <= Constants.getLowBribe()) {

        super.goodsDeclaration(round);
        this.setBribe(0);
        return;
      }

      if (!hasOnlyLegals) {
        tempList = new LinkedList<Goods>();
        int countIllegals = 0;
        LinkedList<Integer> toBeDeleted = new LinkedList<Integer>();
        int coins = this.getCoins();
        // sortez bunurile din "mana" dupa profit
        Collections.sort(this.getGoodsInHand(), new MyProfitComp());
        for (int i = 0; i < Constants.getHandCapacity()
            && tempList.size() < Constants.getBagCapacity()  && coins > 0; i++) {
          // daca are suficenti bani si are loc in sac, adauga bunul ilegal
          if (coins - this.getGoodsInHand().get(i).getPenalty() > 0) {
              if (this.getGoodsInHand().get(i).getType() == GoodsType.Illegal) {
              countIllegals++;
            }

            tempList.add(this.getGoodsInHand().get(i));
            toBeDeleted.add(i);
            coins -= this.getGoodsInHand().get(i).getPenalty();
          }
        }
        if (tempList != null) {
          this.setGoodsInBag(tempList);
          this.setGoodDeclared(0);
        }

        // sterg bunurile puse in sac
        for (int i = 0; i < toBeDeleted.size(); i++) {
          this.getGoodsInHand().remove(0);
        }



        if (coins >= 0 || (countIllegals <= 2 && this.getCoins() >= Constants.getLowBribe())
            || (countIllegals > 2 && this.getCoins() >= Constants.getHighBribe())) {

          // calcul mita
          if (countIllegals == 1 || countIllegals == 2) {
            this.setBribe(Constants.getLowBribe());
          } else
            if (countIllegals > 2) {
              this.setBribe(Constants.getHighBribe());
            }
        }
      }
      if (!hasOnlyLegals && this.getCoins() <= 0) {
        return;
      }

    }

  }
  /**
   *
   * @param numberOfPlayers - numarul de participanti la joc
   * @return vecinii ce trebuie inspectati de serif
   */
  public LinkedList<Integer> getNeighbors(final int numberOfPlayers) {
    // retin pozitiile vecinilor din stanga si dreapta
    LinkedList<Integer> toBeInspected = new LinkedList<Integer>();
    if (this.getId() == 0) {
      if (numberOfPlayers == 2) {
        toBeInspected.add(this.getId() + 1);
        return toBeInspected;

      } else {
        toBeInspected.add(numberOfPlayers - 1);
        toBeInspected.add(this.getId() + 1);
        return toBeInspected;
      }
    } else if (this.getId() == numberOfPlayers - 1) {

      if (numberOfPlayers == 2) {
        toBeInspected.add(this.getId() - 1);
        return toBeInspected;
      } else {
        toBeInspected.add(this.getId() - 1);
        toBeInspected.add(0);
        return toBeInspected;
      }
    }

    toBeInspected.add(this.getId() - 1);
    toBeInspected.add(this.getId() + 1);
    return toBeInspected;

  }
  /**
   * @param players - hash cu starea fiecarui jucator
   * @param gameInput - ofera acces la cartile neimpartite
   */
  @Override
  public void inspect(final Map<Integer, Player> players, final GameInput gameInput) {



    LinkedList<Integer> toBeInspected = getNeighbors(players.size());
    for (Player x : players.values()) {
      if (x != this && toBeInspected.contains(x.getId())) {
        this.doInspection(x, gameInput);
      }
    }

    for (Player x : players.values()) {
      if (x != this && !toBeInspected.contains(x.getId())) {
        UtilityPlayer.acceptBribe(this, x);
        x.moveGoodsToStand();
      }
    }


  }
}
