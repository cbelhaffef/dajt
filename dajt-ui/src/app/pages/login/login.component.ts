import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/api/auth.service';
import { Router } from '@angular/router';

@Component({
	selector   : 's-login-pg',
	templateUrl: './login.component.html',
    styleUrls  : [ './login.scss'],
})

export class LoginComponent implements OnInit {
    model: any = {};
    errMsg: string = '';
    constructor(
        private router: Router,
        private authService: AuthService) { }

    ngOnInit() {
        // reset login status
        this.authService.logout(false);
    }

    login() {
        this.authService.getToken(this.model.username, this.model.password)
            .subscribe(resp => {
                    if (resp.user === undefined || resp.user.token === undefined || resp.user.token === 'INVALID' ) {
                        this.errMsg = 'nom d\'utilisateur ou mot passe incorrecte';
                        return;
                    }
                    this.router.navigate([resp.landingPage]);
                },
                error => {
                    switch (error.status) {
                        case 401:
                            this.errMsg = 'nom d\'utilisateur ou mot passe incorrecte';
                            break;
                        case 404:
                            this.errMsg = 'Service introuvable';
                            break;
                        case 408:
                            this.errMsg = 'Demande expirée';
                            break;
                        case 500:
                            this.errMsg = 'Erreur Interne du Serveur';
                            break;
                        default:
                            this.errMsg = 'Erreur du serveur';
                            break;
                    }
                }
            );
    }

    onSignUp() {
      this.router.navigate(['signup']);
    }

}
