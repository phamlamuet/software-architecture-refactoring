package org.example;

import org.json.JSONObject;

public abstract class PerformanceCalculator {
    JSONObject play;
    JSONObject performance;

    public PerformanceCalculator(JSONObject play, JSONObject performance) {
        this.play = play;
        this.performance = performance;
    }

    abstract double getAmount();

    double getVolumeCredits() {
        return Math.max(Integer.parseInt(this.performance.get("audience").toString()) - 30, 0);
    }
}
