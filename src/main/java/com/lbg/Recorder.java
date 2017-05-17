package com.lbg;

import lombok.Data;

@Data
public class Recorder<T> implements com.mwt.recorders.Recorder<T> {
  private T context;
  private int action;
  private float probability;
  private String uniqueKey;

  public void record(T context, int action, float probability, String uniqueKey) {
    this.context = context;
    this.action = action;
    this.probability = probability;
    this.uniqueKey = uniqueKey;
  }
}
