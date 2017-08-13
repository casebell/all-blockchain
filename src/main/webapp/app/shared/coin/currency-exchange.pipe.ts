import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'currencyExchange' })
export class CurrencyExchangePipe implements PipeTransform {

    transform(currency, coin): string {
        //console.log('currency piple : ',currency );
        //console.log('currency pipe coin : ',coin );
        if (coin.currency && coin.exchangeRate) {
            if (coin.currency === 'USD'  && coin.myCurrency == 'KRW') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[0].Rate);
            } else if (coin.currency === 'KRW' && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) / parseFloat(coin.exchangeRate.query.results.rate[0].Rate);
            } else if (coin.currency ==="CNY"  && coin.myCurrency == 'KRW'){
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[2].Rate);
            }else if (coin.currency ==="CNY"  && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[5].Rate);
            } else if (coin.currency ==="JPY"  && coin.myCurrency == 'KRW'){
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[3].Rate);
            }else if (coin.currency ==="JPY"  && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[6].Rate);
            }
        }
          if(coin.myCurrency == 'KRW')
                currency = parseFloat(currency).toFixed(0)
            else 
                currency = parseFloat(currency).toFixed(2)
        return Number(currency).toLocaleString();
    }
}
