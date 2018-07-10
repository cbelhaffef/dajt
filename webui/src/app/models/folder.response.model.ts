import {Folder} from './folder.model';

export class FolderResponse {

    public items:  Folder[];
    public pageSize:  number;

    constructor() {
        this.items = [];
        this.pageSize = 0;
    }
}
