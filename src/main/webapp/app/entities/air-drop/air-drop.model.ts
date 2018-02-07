import { BaseEntity } from './../../shared';

export class AirDrop implements BaseEntity {
    constructor(
        public id?: number,
        public coinName?: string,
        public startDate?: any,
        public finishDate?: any,
        public context?: string,
        public value?: number,
        public telegram?: string,
        public twitter?: string,
        public facebook?: string,
        public reddit?: string,
        public website?: string,
        public airdropPage?: string,
        public bitcoinTalk?: string,
    ) {
    }
}
