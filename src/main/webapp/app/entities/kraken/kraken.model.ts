import { BaseEntity } from './../../shared';

export class Kraken implements BaseEntity {
    constructor(
        public id?: number,
        public last?: string,
        public createdat?: any,
    ) {
    }
}
