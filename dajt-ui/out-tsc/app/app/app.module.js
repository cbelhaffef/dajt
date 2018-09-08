var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// Third Party Modules
import { MatDialogModule, MatProgressSpinnerModule } from '@angular/material';
import { TableModule } from 'primeng/table';
// Local App Modules
import { AppRoutingModule } from './app-routing.module';
// Directives
import { TrackScrollDirective } from './directives/track_scroll/track_scroll.directive';
// Components
import { BadgeComponent } from './components/badge/badge.component';
import { LegendComponent } from './components/legend/legend.component';
import { LogoComponent } from './components/logo/logo.component';
// Pages  -- Pages too are components, they contain other components
import { AppComponent } from './app.component';
import { HomeComponent } from './home.component';
import { LoginComponent } from './pages/login/login.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
// Services
import { AppConfig } from './app-config';
import { UserInfoService } from './services/user-info.service';
import { AuthGuard } from './services/auth_guard.service';
import { ApiRequestService } from './services/api/api-request.service';
import { TranslateService } from './services/api/translate.service';
import { AuthService } from './services/api/auth.service';
import { LegalCasesComponent } from './pages/legal_cases/legal_cases.component';
import { TransmissionsComponent } from './pages/transmissions/transmissions.component';
import { TransmissionFileComponent } from './pages/transmission_file/transmission_file.component';
import { AdvocatesComponent } from './pages/advocates/advocate.component';
import { FoldersComponent } from './pages/folders/folders.component';
import { FolderDetailsComponent } from './pages/folder_details/folder_details.component';
import { FolderService } from './services/api/folder.service';
import { VictimService } from './services/api/victim.service';
import { AccusedService } from './services/api/accused.service';
import { CourtService } from './services/api/court.service';
import { StatusService } from './services/api/status.service';
import { PriorityService } from './services/api/priority.service';
import { ActionService } from './services/api/action.service';
import { CommonService } from './services/common.service';
import { registerLocaleData } from '@angular/common';
import { OfficeService } from './services/api/office.service';
import { FoldersCreateDialogComponent } from './pages/folders/folders.create.dialog.component';
import localeAr from '@angular/common/locales/ar';
import { AutoCompleteModule, CheckboxModule, DropdownModule, InputTextModule, MessagesModule, SplitButtonModule, TooltipModule } from 'primeng/primeng';
import { UserService } from 'app/services/api/user.service';
import { OverlayPanelModule } from 'primeng/components/overlaypanel/overlaypanel';
import { SpinnerService } from './services/spinner.service';
import { SpinnerComponent } from './components/spinner/spinner.component';
import { PanelModule } from 'primeng/panel';
import { EditorModule } from 'primeng/editor';
import { CardModule } from 'primeng/card';
import { ChipsModule } from 'primeng/chips';
import { MessageService } from 'primeng/components/common/messageservice';
import { SidebarModule } from 'primeng/components/sidebar/sidebar';
import { GrowlModule } from 'primeng/growl';
import { SharedService } from './services/shared.service';
// the second parameter 'fr' is optional
registerLocaleData(localeAr, 'ar');
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        NgModule({
            imports: [
                BrowserModule,
                BrowserAnimationsModule,
                FormsModule,
                ReactiveFormsModule,
                HttpClientModule,
                // Angular Material
                MatDialogModule,
                MatProgressSpinnerModule,
                // Primeng Module
                TableModule,
                CardModule,
                DropdownModule,
                InputTextModule,
                OverlayPanelModule,
                SplitButtonModule,
                AutoCompleteModule,
                PanelModule,
                TooltipModule,
                EditorModule,
                CheckboxModule,
                ChipsModule,
                MessagesModule,
                SidebarModule,
                GrowlModule,
                // Local App Modules
                AppRoutingModule
            ],
            entryComponents: [FoldersCreateDialogComponent],
            declarations: [
                // Components
                BadgeComponent,
                LegendComponent,
                LogoComponent,
                // Pages -- Pages too are components, they contain other components
                AppComponent,
                HomeComponent,
                LoginComponent,
                LogoutComponent,
                DashboardComponent,
                LegalCasesComponent,
                TransmissionsComponent,
                TransmissionFileComponent,
                AdvocatesComponent,
                FoldersComponent,
                FolderDetailsComponent,
                FoldersCreateDialogComponent,
                SpinnerComponent,
                // Directives
                TrackScrollDirective
            ],
            providers: [
                AuthGuard,
                SharedService,
                UserInfoService,
                UserService,
                TranslateService,
                ApiRequestService,
                AuthService,
                FolderService,
                VictimService,
                AccusedService,
                CourtService,
                OfficeService,
                ActionService,
                StatusService,
                PriorityService,
                SpinnerService,
                CommonService,
                MessageService,
                AppConfig
            ],
            bootstrap: [AppComponent]
        })
    ], AppModule);
    return AppModule;
}());
export { AppModule };
//# sourceMappingURL=app.module.js.map