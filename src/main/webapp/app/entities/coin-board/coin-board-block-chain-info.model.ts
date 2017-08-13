import { BaseEntity } from './../../shared';

const enum CoinBoardType {
    'FREE',
    'INQUIRY',
    'NEWS'
}

export class CoinBoardBlockChainInfo implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public context?: string,
        public coninBoardType?: CoinBoardType,
        public createdat?: any,
        public updatedat?: any,
        public coinId?: number,
        public coinBoardComments?: BaseEntity[],
        public userId?: number,
    ) {
    }
}
