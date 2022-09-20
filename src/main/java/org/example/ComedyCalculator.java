package org.example;

import org.json.JSONObject;

public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(JSONObject play, JSONObject performance) {
        super(play, performance);
    }

    @Override
    double getAmount() {
        double result = 30000;
        if (Integer.parseInt(this.performance.get("audience").toString()) > 20) {
            result += 10000 + 500 * (Integer.parseInt(this.performance.get("audience").toString()) - 20);
        }
        result += 300 * Integer.parseInt(this.performance.get("audience").toString());
        return result;
    }

    @Override
    double getVolumeCredits() {
        return super.getVolumeCredits() + Math.floor((double) Integer.parseInt(this.performance.get("audience").toString()) / 5);
    }
}
