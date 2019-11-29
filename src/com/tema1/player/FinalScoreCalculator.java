package com.tema1.player;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;

public class FinalScoreCalculator {



  private static class IllegalsId {
    public static final int SILK = 20;
    public static final int PEPPER = 21;
    public static final int BARREL = 22;
    public static final int BEER = 23;
    public static final int SEAFOOD = 24;
  }

  private static class IllegalsBonus {
    public static final int SILK_BONUS = 9;
    public static final int PEPPER_BONUS = 8;
    public static final int BARREL_BONUS = 8;
    public static final int BEER_BONUS = 20;
    public static final int SEAFOOD_BONUS = 19;
  }

/**
 * Am scris ceva.
 *
 * @param id - id-ul pentru care se cere profitul
 * @return valoarea bunului ilegal cu id-ul dat ca parametru
 */
  public int getBonusById(final int id) {

    if (id == IllegalsId.SILK) {
      return IllegalsBonus.SILK_BONUS;
    }
    if (id == IllegalsId.PEPPER) {
      return IllegalsBonus.PEPPER_BONUS;
    }
    if (id == IllegalsId.BARREL) {
      return IllegalsBonus.BARREL_BONUS;
    }
    if (id == IllegalsId.BEER) {
      return IllegalsBonus.BEER_BONUS;
    }
    if (id == IllegalsId.SEAFOOD) {
      return IllegalsBonus.SEAFOOD_BONUS;
    }

    return 0;

  }



  private static class QueenBonus {
    public static final int APPLE = 10;
    public static final int CHEESE = 9;
    public static final int BREAD = 9;
    public static final int CHICKEN = 8;
    public static final int TOMATO = 7;
    public static final int CORN = 6;
    public static final int POTATO = 5;
    public static final int WINE = 4;
    public static final int SALT = 3;
    public static final int SUGAR = 2;
  }

  private static class KingBonus {
    public static final int APPLE = 20;
    public static final int CHEESE = 19;
    public static final int BREAD = 18;
    public static final int CHICKEN = 17;
    public static final int TOMATO = 16;
    public static final int CORN = 15;
    public static final int POTATO = 14;
    public static final int WINE = 13;
    public static final int SALT = 12;
    public static final int SUGAR = 11;
  }
  /**
   *
   * @param id
   * @return bonusul de King pentru bunul cu id-ul dat ca parametru
   */
  public int getKingBonus(final int id) {
    if (id == LegalsId.APPLE) {
      return KingBonus.APPLE;
    }
    if (id == LegalsId.CHEESE) {
      return KingBonus.CHEESE;
    }
    if (id == LegalsId.BREAD) {
      return KingBonus.BREAD;
    }
    if (id == LegalsId.CHICKEN) {
      return KingBonus.CHICKEN;
    }
    if (id == LegalsId.TOMATO) {
      return KingBonus.TOMATO;
    }
    if (id == LegalsId.CORN) {
      return KingBonus.CORN;
    }
    if (id == LegalsId.POTATO) {
      return KingBonus.POTATO;
    }
    if (id == LegalsId.WINE) {
      return KingBonus.WINE;
    }
    if (id == LegalsId.SALT) {
      return KingBonus.SALT;
    }
    if (id == LegalsId.SUGAR) {
      return KingBonus.SUGAR;
    }

    return 0;
  }
  private static class LegalsId {
    public static final int APPLE = 0;
    public static final int CHEESE = 1;
    public static final int BREAD = 2;
    public static final int CHICKEN = 3;
    public static final int TOMATO = 4;
    public static final int CORN = 5;
    public static final int POTATO = 6;
    public static final int WINE = 7;
    public static final int SALT = 8;
    public static final int SUGAR = 9;
  }
  /**
  *
  * @param id
  * @return bonusul de Queen pentru bunul cu id-ul dat ca parametru
  */
  public int getQueenBonus(final int id) {

    if (id == LegalsId.APPLE) {
      return QueenBonus.APPLE;
    }
    if (id == LegalsId.CHEESE) {
      return QueenBonus.CHEESE;
    }
    if (id == LegalsId.BREAD) {
      return QueenBonus.BREAD;
    }
    if (id == LegalsId.CHICKEN) {
      return QueenBonus.CHICKEN;
    }
    if (id == LegalsId.TOMATO) {
      return QueenBonus.TOMATO;
    }
    if (id == LegalsId.CORN) {
      return QueenBonus.CORN;
    }
    if (id == LegalsId.POTATO) {
      return QueenBonus.POTATO;
    }
    if (id == LegalsId.WINE) {
      return QueenBonus.WINE;
    }
    if (id == LegalsId.SALT) {
      return QueenBonus.SALT;
    }
    if (id == LegalsId.SUGAR) {
      return QueenBonus.SUGAR;
    }

    return 0;
  }

  public static HashMap<Integer, Integer> sortByValue(final HashMap<Integer, Integer> hm) {
    // creeaza o lista cu elementele din hash
    List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(
        hm.entrySet());

    // sorteaza lista
    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
      @Override
      public int compare(final Map.Entry<Integer, Integer> o2,
          final Map.Entry<Integer, Integer> o1) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // transfera datele din lista sortata in hash
    HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
    for (Map.Entry<Integer, Integer> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }
  /**
   * Creeaza un hash pentru a retine frecventele bunurilor in sacii fiecarui jucator.
   * Aplica bonusul de King/Queen primilor doi in functie de profit
   * @param players
   */
  public void decideKingAndQueen(final Map<Integer, Player> players) {

    for (int id = 0; id <= Constants.getNumberLlegalGoods(); id++) {
      // pentru fiecare bun creeaza un hash cu frecventa acelui bun pe taraba fiecarui jucator
      int index = 0;
      HashMap<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
      for (Player currentPlayer : players.values()) {
        Integer count = 0;

        if (currentPlayer.getGoodsOnStand() != null) {
          for (Goods currentGood : currentPlayer.getGoodsOnStand()) {
            if (currentGood.getId() == id) {
              count++;
            }
          }
        }

        frequencyMap.put(index, count);
        index++;

      }
      HashMap<Integer, Integer> sortedMap = sortByValue(frequencyMap);
      // acordul king/queen bonus primilor 2 jucatori din hash
      index = 0;
      for (Integer x : sortedMap.keySet()) {
        if (index == 0 && sortedMap.get(x) != 0) {
          players.get(x).setCoins(players.get(x).getCoins() + getKingBonus(id));
        } else if (index == 1 && sortedMap.get(x) != 0) {
          players.get(x).setCoins(players.get(x).getCoins() + getQueenBonus(id));
        } else {
          break;
        }
        index++;
      }

    }
  }
  /**
   *
   * @param id
   * @param temp - lista in care se retin temporar bunurile ce trebuie adaugate pe taraba
   */
  public void addBonusOnStand(final int id, final LinkedList<Goods> temp) {
    // adauga pe taraba bunurile bonus corespunzatoare bunurilor ilegale
    if (id == IllegalsId.SILK) {
      for (int i = 1; i <= Constants.getSilkBonus(); i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.CHEESE));
      }
    }
    if (id == IllegalsId.PEPPER) {
      for (int i = 1; i <= Constants.getPepperBonus(); i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.CHICKEN));
      }
    }
    if (id == IllegalsId.BARREL) {
      for (int i = 1; i <= Constants.getBarrelBonus(); i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.BREAD));
      }
    }
    if (id == IllegalsId.BEER) {
      for (int i = 1; i <= Constants.getBeerBonus(); i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.WINE));
      }
    }
    if (id == IllegalsId.SEAFOOD) {
      for (int i = 1; i <= Constants.getSeafoodBonus() - 1; i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.TOMATO));
      }
      for (int i = 1; i <= Constants.getSeafoodBonus(); i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.POTATO));
      }
      for (int i = 1; i <= 1; i++) {
        temp.add(Constants.getGoodsfactory().getGoodsById(LegalsId.CHICKEN));
      }
    }

  }
  /**
   *
   * @param player
   */
  public void calculateProfit(final Player player) {
    int profit = 0;
    LinkedList<Goods> temp = new LinkedList<Goods>();
    if (player.getGoodsOnStand() != null) {
      for (Goods x : player.getGoodsOnStand()) {
        addBonusOnStand(x.getId(), temp);
        profit += Constants.getGoodsfactory().getGoodsById(x.getId()).getProfit()
            + getBonusById(x.getId());
      }
      player.setCoins(player.getCoins() + profit);
      player.getGoodsOnStand().addAll(temp);
    }

  }
}
