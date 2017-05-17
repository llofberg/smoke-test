package com.lbg;

import lombok.Data;

@Data
public class Feature {
  private double value;
  private int id;

  public Feature(int id, double value) {
    this.value = value;
    this.id = id;
  }
}
