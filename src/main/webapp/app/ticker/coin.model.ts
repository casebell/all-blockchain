
export class Coin {
    constructor(public id?: number,
                public name?: string,
                public founder?: string,
                public url?: string,
                public consensusAlgorithms?: string,
                public context?: string,
                public releaseat?: Date) {
    }
}
