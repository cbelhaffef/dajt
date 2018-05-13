import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
// Third Party Modules
import {MatDialogModule, MatProgressSpinnerModule} from '@angular/material';
import {TableModule} from 'primeng/table';
// Local App Modules
import {AppRoutingModule} from './app-routing.module';
// Directives
import {TrackScrollDirective} from './directives/track_scroll/track_scroll.directive';
// Components
import {BadgeComponent} from './components/badge/badge.component';
import {LegendComponent} from './components/legend/legend.component';
import {LogoComponent} from './components/logo/logo.component';
// Pages  -- Pages too are components, they contain other components
import {AppComponent} from './app.component';
import {HomeComponent} from './home.component';
import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {DashboardComponent} from './pages/dashboard/dashboard.component';
// Services
import {AppConfig} from './app-config';
import {UserInfoService} from './services/user-info.service';
import {AuthGuard} from './services/auth_guard.service';
import {ApiRequestService} from './services/api/api-request.service';
import {TranslateService} from './services/api/translate.service';
import {LoginService} from './services/api/login.service';
import {LegalCasesComponent} from './pages/legal_cases/legal_cases.component';
import {TransmissionsComponent} from './pages/transmissions/transmissions.component';
import {TransmissionFileComponent} from './pages/transmission_file/transmission_file.component';
import {AdvocatesComponent} from './pages/advocates/advocate.component';
import {FoldersComponent} from './pages/folders/folders.component';
import {FolderDetailsComponent} from './pages/folder_details/folder_details.component';
import {FolderService} from './services/api/folder.service';
import {VictimService} from './services/api/victim.service';
import {GuiltyService} from './services/api/guilty.service';
import {CourtService} from './services/api/court.service';

import {registerLocaleData} from '@angular/common';
import {OfficeService} from './services/api/office.service';
import {FoldersCreateDialogComponent} from './pages/folders/folders.create.dialog.component';
import localeAr from '@angular/common/locales/ar';
import {AutoCompleteModule, DropdownModule, InputTextModule, SplitButtonModule, TooltipModule} from 'primeng/primeng';
import {UserService} from 'app/services/api/user.service';
import {OverlayPanelModule} from 'primeng/components/overlaypanel/overlaypanel';
import {SpinnerService} from './services/spinner.service';
import {SpinnerComponent} from './components/spinner/spinner.component';
import {PanelModule} from 'primeng/panel';
import {ActionService} from './services/api/action.service';
import {EditorModule} from 'primeng/editor';
import {CardModule} from 'primeng/card';

// the second parameter 'fr' is optional
registerLocaleData(localeAr, 'ar');

@NgModule({

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
    UserInfoService,
    UserService,
    TranslateService,
    ApiRequestService,
    LoginService,
    FolderService,
    VictimService,
    GuiltyService,
    CourtService,
    OfficeService,
    ActionService,
    SpinnerService,
    AppConfig
  ],

  bootstrap: [AppComponent]
})

export class AppModule {}
