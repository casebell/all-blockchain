import { BaseEntity } from './../../shared';

export class CoinBoardCommentBlockChainInfo implements BaseEntity {
    constructor(
        public id?: number,
        public context?: string,
        public groupNo?: number,
        public parent?: number,
        public depth?: number,
        public createdat?: any,
        public updatedat?: any,
        public coinBoardId?: number,
        public userId?: number,
    ) {
    }
}
