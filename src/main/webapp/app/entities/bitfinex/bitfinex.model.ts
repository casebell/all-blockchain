import { BaseEntity } from './../../shared';

export class Bitfinex implements BaseEntity {
    constructor(
        public id?: number,
        public mid?: string,
        public bid?: string,
        public ask?: string,
        public last_price?: string,
        public low?: string,
        public high?: string,
        public volume?: string,
        public timestamp?: string,
        public coinId?: number,
    ) {
    }
}
