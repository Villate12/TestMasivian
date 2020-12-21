package com.testmasivian.test;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer userId;
  private Integer rouletteId;
  private String type;
  private String betValue;
  private Integer amount;

  //@ManyToOne
  //@JoinColumn(name = "rouletteId",cascade = CascadeType.ALL)
  //private Roulettes roulette;

  public Integer getuserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBetValue() {
    return betValue;
  }

  public void setBetValue(String betValue) {
    this.betValue = betValue;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Integer getRouletteId() {
    return rouletteId;
  }

  public void setRouletteId(Integer rouletteId) {
    this.rouletteId = rouletteId;
  }
  /*public Roulettes getRoulette() {
    return roulette;
  }

  public void setRoulette(Roulettes roulette) {
    this.roulette = roulette;
  }*/
}
