package pt.ua.tqs.mealbooking.dto;

public class WeatherCacheStats {
    private int totalRequests;
    private int hits;
    private int misses;

    public WeatherCacheStats(int totalRequests, int hits, int misses) {
        this.totalRequests = totalRequests;
        this.hits = hits;
        this.misses = misses;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}
