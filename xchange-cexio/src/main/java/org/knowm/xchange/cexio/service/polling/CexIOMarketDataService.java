package org.knowm.xchange.cexio.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAdapters;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: brox Since: 2/6/14
 */
public class CexIOMarketDataService extends CexIOMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return CexIOAdapters.adaptTicker(getCexIOTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return CexIOAdapters.adaptOrderBook(getCexIOOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    CexIOTrade[] trades;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("arg[0] must be a Number used to represent since trade id.");
      } else {
        trades = getCexIOTrades(currencyPair, ((Number) arg0).longValue());
      }
    } else { // default to full available trade history
      trades = getCexIOTrades(currencyPair, null);
    }

    return CexIOAdapters.adaptTrades(trades, currencyPair);
  }

}
