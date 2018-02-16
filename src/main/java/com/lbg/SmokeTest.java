package com.lbg;

import com.mwt.explorers.EpsilonGreedyExplorer;
import com.mwt.explorers.MwtExplorer;
import com.mwt.policies.Policy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import vowpalWabbit.learner.VWLearners;
import vowpalWabbit.learner.VWMulticlassLearner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
public class SmokeTest {

  private static final String config = "src/main/resources/conf.yml";

  private String appId = "mwt";
  private String command = "--quiet --cb 9 -f cb.model";
  private int numActions = 9;
  private float epsilon = 0.0f;

  private int size = 4;
  private int rounds = 100;

  private List<String> train;
  private ArrayList<ArrayList<Feature>> tests;

  void run() {
    final Recorder<Context> recorder = new Recorder<>();
    final MwtExplorer<Context> mwt = new MwtExplorer<>(appId, recorder);
    final VWMulticlassLearner vw = VWLearners.create(command);
    final Policy<Context> policy = cxt -> vw.predict(cxt.toString());
    final EpsilonGreedyExplorer<Context> explorer = new EpsilonGreedyExplorer<>(policy, epsilon, numActions);

    int key = 0;

    for (String sample : train) {
      vw.learn(sample);
    }

    for (ArrayList<Feature> features : tests) {
      String uniqueKey = String.valueOf(++key);
      mwt.chooseAction(explorer, uniqueKey, new Context(features));
      log.debug("Action: {}", recorder.getAction());
    }

    try {
      vw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    getTest().ifPresent(SmokeTest::run);
  }

  private static Optional<SmokeTest> getTest() {
    SmokeTest smokeTest = null;
    try {
      smokeTest = new Yaml().loadAs(new FileReader(config), SmokeTest.class);
    } catch (FileNotFoundException e) {
      log.error("Failed to load configuration", e);
    }
    return Optional.ofNullable(smokeTest);
  }
}
