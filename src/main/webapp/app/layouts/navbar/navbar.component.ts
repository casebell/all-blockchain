import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import { trigger, state, style, transition, animate } from '@angular/animations';
import {ProfileService} from '../profiles/profile.service';
import {JhiLanguageHelper, Principal, LoginModalService, LoginService} from '../../shared';

import {VERSION} from '../../app.constants';
import {MatDialog} from '@angular/material';
import {JhiLoginModalComponent} from '../../shared';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ],
    animations: [
        trigger('toolbarIcon', [
            state('default', style({transform: 'rotate(0)'})),
            state('rotated', style({transform: 'rotate(90deg)'})),
            transition('rotated => default', animate('200ms ease-out')),
            transition('default => rotated', animate('200ms ease-in'))
        ]),
    ]

})

export class NavbarComponent implements OnInit {

    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    // modalRef: NgbModalRef;
    version: string;
    checkMenu: boolean;
    coinState = 'default';
    accountState = 'default';
    constructor(private loginService: LoginService,
                private languageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private principal: Principal,
                private loginModalService: LoginModalService,
                private profileService: ProfileService,
                private router: Router,
                private dialog: MatDialog) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.checkMenu = false;
    }

    ngOnInit() {
        const lang = navigator.language;
        console.log(lang);
        this.languageHelper.getAll().then((languages) => {
            console.log('languages : ', languages);
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
    }

    clickMenuIcon() {
        this.checkMenu = !this.checkMenu;
    }

    rotate(value) {
        switch (value){
            case 'coinState':
                this.coinState = (this.coinState === 'default' ? 'rotated' : 'default');
                break;
            case 'accountState':
                this.accountState = (this.accountState === 'default' ? 'rotated' : 'default');
                break;
        }

    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        // this.modalRef = this.loginModalService.open();
        const dialogRef = this.dialog.open(JhiLoginModalComponent);
        dialogRef.afterClosed().subscribe((result) => {

        });
    }

    openCoinSidenav(){

    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
