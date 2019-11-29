package com.tema1.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

public class Player {

  private int id;
  private int coins;
  private int bonus;
  private String type;
  private LinkedList<Goods> goodsInHand;
  private LinkedList<Goods> goodsOnStand;
  private LinkedList<Goods> goodsInBag;
  private int goodDeclared;
  private int bribe;

  public final LinkedList<Goods> getGoodsInBag() {
    return goodsInBag;
  }

  public final void setGoodsInBag(final LinkedList<Goods> goodsInBag) {
    this.goodsInBag = goodsInBag;
  }

  public final int getId() {
    return id;
  }

  public Player() {
    this.id = 0;
    this.coins = 0;
    this.bonus = 0;
    this.type = null;
    this.goodsInHand = new LinkedList<Goods>();
    this.goodsOnStand = new LinkedList<Goods>();
    this.goodsInBag = new LinkedList<Goods>();
    this.goodDeclared = -1;
    this.bribe = 0;
  }

  public Player(final int id, final int coins, final int bonus,
      final String type, final LinkedList<Goods> goodsInHand, final LinkedList<Goods> goodsOnStand,
      final LinkedList<Goods> goodsInBag, final int goodDeclared, final int bribe) {
    super();
    this.id = id;
    this.coins = coins;
    this.bonus = bonus;
    this.type = type;
    this.goodsInHand = goodsInHand;
    this.goodsOnStand = goodsOnStand;
    this.goodsInBag = goodsInBag;
    this.bribe = bribe;
    this.goodDeclared = goodDeclared;
  }

  public final int getGoodDeclared() {
    return goodDeclared;
  }

  public final void setGoodDeclared(final int goodDeclared) {
    this.goodDeclared = goodDeclared;
  }

  public final int getBribe() {
    return bribe;
  }

  public final void setBribe(final int bribe) {
    this.bribe = bribe;
  }

  public final String getType() {
    return type;
  }

  public final void setType(final String type) {
    this.type = type;
  }

  public final void setId(final int id) {
    this.id = id;
  }

  public final int getCoins() {
    return coins;
  }

  public final void setCoins(final int coins) {
    this.coins = coins;
  }

  public final int getBonus() {
    return bonus;
  }

  public final void setBonus(final int bonus) {
    this.bonus = bonus;
  }

  public final LinkedList<Goods> getGoodsInHand() {
    return goodsInHand;
  }

  public final void setGoodsInHand(final LinkedList<Goods> goodsInHand) {
    this.goodsInHand = goodsInHand;
  }

  public final LinkedList<Goods> getGoodsOnStand() {
    return goodsOnStand;
  }

  public final void setGoodsOnStand(final LinkedList<Goods> goodsOnStand) {
    this.goodsOnStand = goodsOnStand;
  }

  public final void addGoodOnStand(final Goods x) {
    this.goodsOnStand.add(x);
  }

  public final int getMostFrequent(final GoodsType typeOfGood) {
    GoodsFactory goodsDB = GoodsFactory.getInstance();
    // creeaza un hash pentru frecventa cu care apare fiecare carte in mana unui jucator
    Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
    for (Goods x : this.getGoodsInHand()) {
      if (frequency.get(x.getId()) == null) {
        frequency.put(x.getId(), 1);
      } else {
        frequency.put(x.getId(), frequency.get(x.getId()) + 1);
      }
    }
    // alege cea mai frecventa -> profitabila -> mare dpdv al id-ului
    int maxFrequency = 0, idMaxFrequency = 0;
    Set<Map.Entry<Integer, Integer>> st = frequency.entrySet();
    for (Map.Entry<Integer, Integer> me : st) {
      if (goodsDB.getGoodsById(me.getKey()).getType() == typeOfGood) {
        if (me.getValue() == maxFrequency
            && goodsDB.getGoodsById(me.getKey()).getProfit() == goodsDB.getGoodsById(idMaxFrequency)
                .getProfit()
            && goodsDB.getGoodsById(me.getKey()).getId() > goodsDB.getGoodsById(idMaxFrequency)
                .getId()) {
          maxFrequency = me.getValue();
          idMaxFrequency = me.getKey();
        } else if (me.getValue() == maxFrequency && goodsDB.getGoodsById(me.getKey())
            .getProfit() > goodsDB.getGoodsById(idMaxFrequency).getProfit()) {
          maxFrequency = me.getValue();
          idMaxFrequency = me.getKey();
        } else if (me.getValue() > maxFrequency) {
          maxFrequency = me.getValue();
          idMaxFrequency = me.getKey();
        }
      }

    }

    return idMaxFrequency;
  }

  public void goodsDeclaration(final int round) {

  }

  public void inspect(final Map<Integer, Player> players, final GameInput gameInput) {

  }

  public final void doInspection(final Player inspected, final GameInput gameInput) {
    int index = 0;
    boolean hasIllegals = false;
    // daca jucatorul inspectat are sacul gol, nu se realizeaza inspectia
    if (inspected.getGoodsInBag() == null) {
      return;
    }
    // daca seriful nu are bani, nu inspecteaza
    if (this.getCoins() < Constants.getNoMoney()) {
      inspected.moveGoodsToStand();
      return;
    }
    // seriful verifica daca jucatorul are bunuri ilegale
    for (Goods x : inspected.getGoodsInBag()) {
      if (x.getType() == GoodsType.Illegal) {
        hasIllegals = true;
      }
    }

    LinkedList<Integer> toBeDeleted = new LinkedList<Integer>();
    for (Goods x : inspected.getGoodsInBag()) {
      if (x.getId() != inspected.getGoodDeclared() || x.getType() == GoodsType.Illegal) {
        // daca jucatorul a avut un bun ilegal sau nedeclarat, primeste penalty-ul
        UtilityPlayer.applyPenalty(inspected, this, x);
        gameInput.getAssetIds().add(x.getId());
        toBeDeleted.add(index);
      } else if (x.getType() == GoodsType.Legal) {
        // jucatorul primeste bani doar daca nu e mincinos
        if (!hasIllegals) {
          // daca jucatorul are doar legale, serifului i se aplica penalty-ul
          UtilityPlayer.applyPenalty(this, inspected, x);
        }
        if (inspected.getGoodsOnStand() == null) {
          inspected.goodsOnStand = new LinkedList<Goods>();
        }
        // adauga bunurile trecute de inspectie pe taraba
        inspected.getGoodsOnStand().add(x);
        toBeDeleted.add(index);
      }
      index++;

    }

    for (int i = 0; i < toBeDeleted.size(); i++) {
      inspected.getGoodsInBag().remove(0);
    }

    inspected.setBribe(0);

  }

  public final void moveGoodsToStand() {
    if(this.getGoodsInBag() == null)
      return;
    // muta bunurile unui jucator din sac pe taraba
    LinkedList<Goods> tempStand = null;
    LinkedList<Integer> toBeDeleted = new LinkedList<Integer>();
    if (this.getGoodsOnStand() != null) {
      tempStand = this.getGoodsOnStand();
    } else {
      tempStand = new LinkedList<Goods>();
    }
    int index = 0;
    for (Goods x : this.getGoodsInBag()) {
      tempStand.add(x);
      toBeDeleted.add(index);
      index++;
    }

    for (int i = 0; i < toBeDeleted.size(); i++) {
      this.getGoodsInBag().remove(0);
    }

    this.setGoodsOnStand(tempStand);

  }

  public final void doNotInspect(final Player inspected, final GameInput gameInput) {
    if (this.getCoins() >= Constants.getNoMoney()) {
      UtilityPlayer.acceptBribe(this, inspected);
    }
    inspected.moveGoodsToStand();
  }

  public final int getMostProfitable(final GoodsType typeOfGood) {

    int mostProfitable = 0, idMostProfitable = -1;
    for (Goods x : this.getGoodsInHand()) {
      if (x.getType() == typeOfGood && x.getProfit() > mostProfitable) {
        mostProfitable = x.getProfit();
        idMostProfitable = x.getId();
      }
    }
    return idMostProfitable;
  }

  public final void handRefill(final GameInput gameInput) {
    // fiecare jucator primeste alte 10 carti in pachet
    int size = 0, index = 0;
    LinkedList<Goods> tempGoods;
    if (this.getGoodsInHand() != null) {
      tempGoods = this.getGoodsInHand();
    } else {
      tempGoods = new LinkedList<Goods>();
    }

    if (tempGoods != null) {
      size = tempGoods.size();
    }

    while (size != Constants.getHandCapacity()) {
      tempGoods.add(Constants.getGoodsfactory().getGoodsById(gameInput.getAssetIds().get(index)));
      index++;
      size++;
    }
    for (int i = 0; i < index; i++) {
      gameInput.getAssetIds().remove(0);
    }

    this.setGoodsInHand(tempGoods);

  }

}
