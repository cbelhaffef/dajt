import {Component, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {AuthService} from './services/api/auth.service';
import {UserinfoService} from './services/userinfo.service';
import {filter, map, mergeMap, switchMap} from 'rxjs/operators';


@Component( {
  selector   :  'app-home-comp',
  templateUrl:  './home.component.html',
  styleUrls  :  ['./home.scss'],
  encapsulation:  ViewEncapsulation.None
})
export class HomeComponent    {

    public showAppAlert = true;

    public selectedHeaderItemIndex = 0;
    public selectedSubNavItemIndex = 1;
    public userName = '';

    constructor(
        private router:  Router,
        private activeRoute:  ActivatedRoute,
        private loginService:  AuthService,
        private userInfoService:  UserinfoService) {

        // This block is to retrieve the data from the routes (routes are defined in app.routing.ts)
        router.events.pipe(
            filter(event => event instanceof NavigationEnd),
            map( _ => this.router.routerState.root),
            map(route => {
                while (route.firstChild) {
                    route = route.firstChild;
                }
                return route;
            }),
            mergeMap( route => route.data))
            .subscribe(data => {
            console.log('Route data===:  ', data[0]);
            this.selectedHeaderItemIndex = data[0] ? data[0].selectedHeaderItemIndex :  -1;
            this.selectedSubNavItemIndex = data[0] ? data[0].selectedSubNavItemIndex :  -1;
        });
        this.userName = this.userInfoService.getUserName();

    }

    navbarSelectionChange(val) {
        console.log(val);
    }

    closeAppAlert() {
        this.showAppAlert = false;
    }

}
