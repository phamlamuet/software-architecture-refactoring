package org.example;

import org.json.JSONObject;

public class PerformanceCalFactory {

    public PerformanceCalculator getPerformanceCal(JSONObject play,JSONObject performance) {
        switch (play.get("type").toString()) {
            case "tragedy":
                return new TragedyCalculator(play, performance);
            case "comedy":
                return new ComedyCalculator(play, performance);
            default: throw new RuntimeException("Unsupported play type");
        }
    }
}
