//package common;
package com.tema1.common;

import com.tema1.goods.GoodsFactory;

public final class Constants {
  // add/delete any constants you think you may use
  private Constants() {

  }

  static final int INITIAL_COINS = 80;
  static final int NO_MONEY = 16;
  static final int NUMBER_ILLEGAL_GOODS = 5;
  static final int NUMBER_LLEGAL_GOODS = 9;
  static final int LOW_BRIBE = 5;
  static final int HIGH_BRIBE = 10;
  static final int SILK_BONUS = 3;
  static final int PEPPER_BONUS = 2;
  static final int BARREL_BONUS = 2;
  static final int BEER_BONUS = 4;
  static final int SEAFOOD_BONUS = 3;

  public static int getSilkBonus() {
    return SILK_BONUS;
  }

  public static int getPepperBonus() {
    return PEPPER_BONUS;
  }

  public static int getBarrelBonus() {
    return BARREL_BONUS;
  }

  public static int getBeerBonus() {
    return BEER_BONUS;
  }

  public static int getSeafoodBonus() {
    return SEAFOOD_BONUS;
  }

  public static int getLowBribe() {
    return LOW_BRIBE;
  }

  public static int getHighBribe() {
    return HIGH_BRIBE;
  }

  public static int getNumberIllegalGoods() {
    return NUMBER_ILLEGAL_GOODS;
  }

  public static int getNumberLlegalGoods() {
    return NUMBER_LLEGAL_GOODS;
  }

  public static int getNoMoney() {
    return NO_MONEY;
  }

  public static int getBagCapacity() {
    return BAG_CAPACITY;
  }

  static final int HAND_CAPACITY = 10;

  public static int getInitialCoins() {
    return INITIAL_COINS;
  }

  static final int BAG_CAPACITY = 8;

  public static int getHandCapacity() {
    return HAND_CAPACITY;
  }

  static final GoodsFactory goodsFactory = GoodsFactory.getInstance();

  public static GoodsFactory getGoodsfactory() {
    return goodsFactory;
  }

}
