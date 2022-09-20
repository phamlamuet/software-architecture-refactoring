package org.example;

import org.json.JSONObject;

public class TragedyCalculator extends PerformanceCalculator {
    public TragedyCalculator(JSONObject play, JSONObject performance) {
        super(play, performance);
    }

    @Override
    double getAmount() {
        double result = 40000;
        if (Integer.parseInt(this.performance.get("audience").toString()) > 30) {
            result += 1000 * (Integer.parseInt(this.performance.get("audience").toString()) - 30);
        }
        return result;
    }

}
