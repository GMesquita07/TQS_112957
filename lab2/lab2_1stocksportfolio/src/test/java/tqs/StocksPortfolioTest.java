package tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tqs.IStockmarketService;
import tqs.Stock;
import tqs.StocksPortfolio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) //Usa-se para evitar warnings e exceptions de misuses
public class StocksPortfolioTest {
    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void calcTotalValueAnnotations() {

        //1. Prepare a mock to substitute the remote service (@Mock annotation)
        //IStockmarketService market = mock(IStockmarketService.class);


        //2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        //StocksPortfolio portfolio = new StocksPortfolio(market);

        
        //3. Load the mock with the proper expectations (when...thenReturn)
        when(market.lookUpPrice("EBAY")).thenReturn(4.0);
        when(market.lookUpPrice("MSFT")).thenReturn(1.5);


        //4. Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 4));
        double result = portfolio.totalValue();

        //5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result);

        assertThat(result, is(14.0));
        verify(market, times(2)).lookUpPrice(anyString());

    }

}