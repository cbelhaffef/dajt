import {Folder} from './folder.model';

export class FolderResponse {

    public items:  Folder[];
    public first: boolean;
    public last: boolean;
    public currentPAgeNumber: number;
    public itemsInPage: number;
    public pageSize:  number;
    public totalPages: number;
    public totalItems: number;
    public operationStatus: string;
    public operationMessage: string;


    constructor() {
        this.items = [];
        this.pageSize = 10;
    }
}
