import { BaseEntity } from './../../shared';

export class Market implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public country?: string,
        public url?: string,
        public currency?: string,
        public coins?: BaseEntity[],
    ) {
    }
}
