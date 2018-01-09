import { BaseEntity } from './../../shared';

export class Quote implements BaseEntity {
    constructor(
        public id?: number,
        public lastPrice?: number,
        public volume?: number,
        public lowPrice?: number,
        public highPrice?: number,
        public avgPrice?: number,
        public buyPrice?: number,
        public sellPrice?: number,
        public openingPrice?: number,
        public closingPrice?: number,
        public quoteTime?: any,
        public currency?: any,
        public marketCoin?: BaseEntity,
    ) {
    }
}
