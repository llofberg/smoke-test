package com.lbg;

import java.util.ArrayList;

public class Context {
  private ArrayList<Feature> features;

  Context(ArrayList<Feature> features) {
    this.features = features;
  }

  @Override
  public String toString() {
    StringBuilder out = new StringBuilder().append("| ");
    for (Feature feature : features) {
      out.append(feature.getId()).append(":").append(feature.getValue()).append(" ");
    }
    return out.toString();
  }
}
