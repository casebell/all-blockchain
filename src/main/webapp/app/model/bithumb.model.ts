export interface Bithumb {
    status: number;
    data: BithumbData;
}

interface BithumbData {
    opening_price: string;
    closing_price: string;
    min_price: string;
    max_price: string;
    average_price: string;
    units_traded: string;
    volume_1day: string;
    volume_7day: string;
    buy_price: string;
    sell_price: string;
    date: Date;
}

/*
  "status": "0000",
    "data": {
        "opening_price" : "504000",
        "closing_price" : "505000",
        "min_price"     : "504000",
        "max_price"     : "516000",
        "average_price" : "509533.3333",
        "units_traded"  : "14.71960286",
        "volume_1day"   : "14.71960286",
        "volume_7day"   : "15.81960286",
        "buy_price"     : "505000",
        "sell_price"    : "504000",
        "date"          : 1417141032622
    }
 */
