var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component } from '@angular/core';
import { AuthService } from '../../services/api/auth.service';
import { Router } from '@angular/router';
var LoginComponent = /** @class */ (function () {
    function LoginComponent(router, authService) {
        this.router = router;
        this.authService = authService;
        this.model = {};
        this.errMsg = '';
    }
    LoginComponent.prototype.ngOnInit = function () {
        // reset login status
        this.authService.logout(false);
    };
    LoginComponent.prototype.login = function () {
        var _this = this;
        this.authService.getToken(this.model.username, this.model.password)
            .subscribe(function (resp) {
            if (resp.user === undefined || resp.user.token === undefined || resp.user.token === 'INVALID') {
                _this.errMsg = 'nom d\'utilisateur ou mot passe incorrecte';
                return;
            }
            _this.router.navigate([resp.landingPage]);
        }, function (error) {
            switch (error.status) {
                case 401:
                    _this.errMsg = 'nom d\'utilisateur ou mot passe incorrecte';
                    break;
                case 404:
                    _this.errMsg = 'Service introuvable';
                    break;
                case 408:
                    _this.errMsg = 'Demande expirée';
                    break;
                case 500:
                    _this.errMsg = 'Erreur Interne du Serveur';
                    break;
                default:
                    _this.errMsg = 'Erreur du serveur';
                    break;
            }
        });
    };
    LoginComponent.prototype.onSignUp = function () {
        this.router.navigate(['signup']);
    };
    LoginComponent = __decorate([
        Component({
            selector: 'app-login-pg',
            templateUrl: './login.component.html',
            styleUrls: ['./login.scss'],
        }),
        __metadata("design:paramtypes", [Router,
            AuthService])
    ], LoginComponent);
    return LoginComponent;
}());
export { LoginComponent };
//# sourceMappingURL=login.component.js.map