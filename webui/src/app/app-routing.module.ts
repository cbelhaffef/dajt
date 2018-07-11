import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home.component';

import {LoginComponent} from './pages/login/login.component';
import {LogoutComponent} from './pages/logout/logout.component';


import {AuthGuard} from './services/auth_guard.service';
import {PageNotFoundComponent} from './pages/404/page-not-found.component';
import {LegalCasesComponent} from './pages/legal_cases/legal_cases.component';
import {TransmissionFileComponent} from './pages/transmission_file/transmission_file.component';
import {AdvocatesComponent} from './pages/advocates/advocate.component';
import {FoldersComponent} from './pages/folders/folders.component';
import {FolderDetailsComponent} from './pages/folder_details/folder_details.component';


export const routes:  Routes = [
  // Important:  The sequence of path is important as the router go over then in sequential manner
   { path:  '', redirectTo:  '/home/legal_cases/folders', pathMatch:  'full' },
   {
    path:  'home',
    component:  HomeComponent,
    canActivate:  [AuthGuard],
    children:  [  // Children paths are appended to the parent path
         { path:  '', redirectTo:  '/home/legal_cases/folders', pathMatch:  'full', data:  [ {selectedHeaderItemIndex:  1, selectedSubNavItemIndex:  -1}] },
        // Default path (if no deep path is specified for home component like webui/home then it will by default show ProductsComponent )
         {
            path     :  'legal_cases',
            component:  LegalCasesComponent,
            data     :  [ {selectedHeaderItemIndex:  0, selectedSubNavItemIndex:  -1}],
            children :  [
                 { path:  ''                  , redirectTo:  '/home/legal_cases/folders', pathMatch:  'full'},
                 { path:  'folders'           , component:  FoldersComponent          , data:  [ {selectedHeaderItemIndex:  0, selectedSubNavItemIndex:  0 }] },
                 { path:  'folders/:id'        , component:  FolderDetailsComponent    , data:  [ {selectedHeaderItemIndex:  0, selectedSubNavItemIndex:  0 }]  },
                 { path:  'advocates'         , component:  AdvocatesComponent        , data:  [ {selectedHeaderItemIndex:  0, selectedSubNavItemIndex:  1 }] },
                 { path:  'transmission_file' , component:  TransmissionFileComponent , data:  [ {selectedHeaderItemIndex:  0, selectedSubNavItemIndex:  2 }] },

            ]
        },
    ]
  },
   { path:  'login' , component:  LoginComponent       , data:  [ {selectedHeaderItemIndex:  -1, selectedSubNavItemIndex:  -1}] },
   { path:  'logout', component:  LogoutComponent      , data:  [ {selectedHeaderItemIndex:  -1, selectedSubNavItemIndex:  -1}] },
   { path:  '**'    , component:  PageNotFoundComponent, data:  [ {selectedHeaderItemIndex:  -1, selectedSubNavItemIndex:  -1}] }

];
@NgModule( {
  imports:  [ RouterModule.forRoot(routes, {useHash:  true} )],
  exports:  [ RouterModule ],
  declarations:  [PageNotFoundComponent]
})
export class AppRoutingModule {}
