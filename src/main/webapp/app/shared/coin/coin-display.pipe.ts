import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'coinDisplay' })
export class CoinDisplayPipe implements PipeTransform {

    transform(value): string {
        let image = "";
        let imgPath = "../../../../content/images/icon/";
        if (value) {
            switch(value){
                case "btc":
                    image = `${imgPath}bitcoin.png`;
                    break;
                case "eth":
                    image = `${imgPath}ethereum.png`;
                    break;
                case "xrp":
                    image = `${imgPath}ripple.png`;
                    break;
                case "dash":
                    image = `${imgPath}dash.png`;
                    break;
                case "ltc":
                    image = `${imgPath}lite-coin.png`;
                    break;
                case "etc":
                    image = `${imgPath}ethereum-classic.png`;
                    break;
                case "zec":
                    image = `${imgPath}zcash.png`;
                    break;
                case "xmr":
                    image = `${imgPath}monero.png`;
                    break;
                case "neo":
                    image = `${imgPath}neo.png`;
                    break;
                case "qtum":
                    image = `${imgPath}zqtum.png`;
                    break;
                case "iota":
                    image = `${imgPath}iota.png`;
                    break;
                case "cardano":
                    image = `${imgPath}cardano.png`;
                    break;
                case "btg":
                    image = `${imgPath}bitcoin-gold.png`;
                    break;
                case "eos":
                    image = `${imgPath}eos.png`;
                    break;
            }
        }

        return image;
    }
}

/*
yahoo api now error
 if (coin.currency === 'USD'  && coin.myCurrency == 'KRW') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[0].Rate);
            } else if (coin.currency === 'KRW' && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) / parseFloat(coin.exchangeRate.query.results.rate[0].Rate);
            } else if (coin.currency ==="CNY"  && coin.myCurrency == 'KRW'){
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[2].Rate);
            }else if (coin.currency ==="CNY"  && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[5].Rate);
            } else if (coin.currency ==="EUR"  && coin.myCurrency == 'KRW'){
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[1].Rate);
            }else if (coin.currency ==="EUR"  && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[4].Rate);
            } else if (coin.currency ==="JPY"  && coin.myCurrency == 'KRW'){
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[3].Rate);
            }else if (coin.currency ==="JPY"  && coin.myCurrency == 'USD') {
                currency = parseFloat(currency) * parseFloat(coin.exchangeRate.query.results.rate[6].Rate);
            }
 */
