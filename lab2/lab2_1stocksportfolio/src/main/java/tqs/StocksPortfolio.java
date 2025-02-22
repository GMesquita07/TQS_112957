package tqs;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double totalValue() {
        double total = 0.0;

        for (Stock stock : stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }

        return total;
    }

    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
                     .sorted((s1, s2) -> Double.compare(
                         stockmarket.lookUpPrice(s2.getLabel()) * s2.getQuantity(),
                         stockmarket.lookUpPrice(s1.getLabel()) * s1.getQuantity()
                     ))
                     .limit(topN)
                     .toList();
    }
    
}