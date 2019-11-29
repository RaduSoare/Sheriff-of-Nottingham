package com.tema1.player;

import java.util.LinkedList;
import java.util.Map;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

public class BasePlayer extends Player {

    public BasePlayer() {
        super();

    }

    public BasePlayer(final int id, final int coins, final int bonus,
        final String type, final LinkedList<Goods> goodsInHand,
        final LinkedList<Goods> goodsOnStand, final LinkedList<Goods> goodsInBag,
        final int goodDeclared, final int bribe) {
        super(id, coins, bonus, type, goodsInHand, goodsOnStand, goodsInBag,
            goodDeclared, bribe);
    }
    /**
     * Cauta cel mai frecvent bun si ii adauga fiecare aparitie in sac.
     *
     * @param round -  runda la care se afla jocul in momentul apelarii functiei
     */
    @Override
    public void goodsDeclaration(final int round) {
        // verific daca are doar bunuri ilegale
        if (this.getGoodsInHand() != null) {

            GoodsFactory goodsfactoryInstance = GoodsFactory.getInstance();
            boolean hasOnlyIllegalGoods = true;

            for (Goods x : this.getGoodsInHand()) {
                if (x.getType() == GoodsType.Legal) {
                    hasOnlyIllegalGoods = false;
                    break;
                }
            }

            LinkedList<Goods> goodsInBag = new LinkedList<Goods>();
            int goodsMoved = 0;
            /*
             * daca are si bunuri legale, cauta cel mai frecvent si adauga toate bunurile
             * din acel tip in sac
             */
            if (!hasOnlyIllegalGoods) {
                for (Goods x : this.getGoodsInHand()) {
                    if (x.getId() == getMostFrequent(GoodsType.Legal)
                        && goodsMoved <= Constants.getBagCapacity()) {
                        goodsInBag.add(goodsfactoryInstance.
                            getGoodsById(getMostFrequent(GoodsType.Legal)));
                        goodsMoved++;
                    }
                }
                this.setGoodsInBag(goodsInBag);
                this.setGoodDeclared(getMostFrequent(GoodsType.Legal));

                //elimina cartea pusa in sac
                for (int i = 0; i < this.getGoodsInHand().size() && goodsMoved > 0; i++) {
                    if (this.getGoodsInHand().get(i).getId() == getMostFrequent(GoodsType.Legal)) {
                        this.getGoodsInHand().remove(i);
                        goodsMoved--;
                    }

                }



            } else {
              /*
               * daca detine doar bunuri ilegale il adauga pe cel cu profitul cel mai mare
               */
                goodsInBag.add(goodsfactoryInstance.
                    getGoodsById(getMostProfitable(GoodsType.Illegal)));
                this.setGoodsInBag(goodsInBag);
                this.setGoodDeclared(0); //apple declaration
                this.setBribe(0);

                //elimina cartea pusa in sac
                for (int i = 0; i < this.getGoodsInHand().size(); i++) {
                    if (this.getGoodsInHand().get(i).getId()
                        == getMostProfitable(GoodsType.Illegal)) {
                        this.getGoodsInHand().remove(i);
                        break;
                    }

                }

            }
        }


    }
    /**
     * Inspectarea celorlati jucatori.
     * @param players - HashMap-ul de participanti la joc
     * @param gameInput - ofera acces la cartile neimpartite inca
     */
    @Override
    public void inspect(final Map<Integer, Player> players, final GameInput gameInput) {

      for (Player x : players.values()) {
        if (x != this && this.getCoins() >= Constants.getNoMoney()) {
          this.doInspection(x, gameInput);
        } else
          if (this.getCoins() < Constants.getNoMoney()) {
              this.doNotInspect(x, gameInput);
            }

      }
    }

}
