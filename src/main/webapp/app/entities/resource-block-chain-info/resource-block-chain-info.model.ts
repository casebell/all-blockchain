import { BaseEntity } from './../../shared';

export const enum ResourceType {
    'IMAGE',
    'VIDEO'
}

export class ResourceBlockChainInfo implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public size?: number,
        public createdAt?: any,
        public updatedAt?: any,
        public resourceType?: ResourceType,
        public coinId?: number,
    ) {
    }
}
