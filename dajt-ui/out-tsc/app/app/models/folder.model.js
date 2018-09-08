import { Court } from './court.model';
import { User } from './user.model';
import { Office } from './office.model';
import { Status } from './status.model';
import { Priority } from './priority.model';
var Folder = /** @class */ (function () {
    function Folder() {
        this.court = new Court();
        this.office = new Office();
        this.assignee = new User();
        this.reporter = new User();
        this.status = new Status();
        this.priority = new Priority();
    }
    return Folder;
}());
export { Folder };
//# sourceMappingURL=folder.model.js.map