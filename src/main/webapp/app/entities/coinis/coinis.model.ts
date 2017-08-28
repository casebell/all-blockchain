import { BaseEntity } from './../../shared';

export class Coinis implements BaseEntity {
    constructor(
        public id?: number,
        public closeprice?: string,
        public highprice?: string,
        public itemcode?: string,
        public lowprice?: string,
        public openprice?: string,
        public prevcloseprice?: string,
        public tradeamount?: string,
        public tradedaebi?: string,
        public tradedaebirate?: string,
        public tradedate?: string,
        public tradevolumn?: string,
        public symbol?: string,
        public createdat?: any,
    ) {
    }
}
