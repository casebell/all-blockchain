export class Coin {
    symbol: Symbol;
    averagePrice: number;
    closingPrice: number;
    currencies: Currency;
}

const enum Symbol {
    Bithumb,
    Korbit
}

const enum Currency {
    KRW,
    USD
}
