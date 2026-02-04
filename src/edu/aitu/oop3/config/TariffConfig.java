package edu.aitu.oop3.config;

public class TariffConfig {

    private static final TariffConfig INSTANCE = new TariffConfig();

    private int defaultTariffId = 1;

    private TariffConfig() { }

    public static TariffConfig getInstance() {
        return INSTANCE;
    }

    public int getDefaultTariffId() {
        return defaultTariffId;
    }

    public void setDefaultTariffId(int defaultTariffId) {
        this.defaultTariffId = defaultTariffId;
    }
}
