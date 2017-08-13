import { BaseEntity } from './../../shared';

const enum ConsensusAlgorithms {
    'POS',
    'POW'
}

export class CoinBlockChainInfo implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public founder?: string,
        public consensusAlgorithms?: ConsensusAlgorithms,
        public homepage?: string,
        public whitePaper?: string,
        public context?: string,
        public releaseat?: any,
        public createdat?: any,
        public updatedat?: any,
        public resources?: BaseEntity[],
        public boards?: BaseEntity[],
    ) {
    }
}
