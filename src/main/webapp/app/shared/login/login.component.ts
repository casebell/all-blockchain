import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService } from './login.service';
import { StateStorageService } from '../auth/state-storage.service';
import { SocialService } from '../social/social.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MatDialogRef } from '@angular/material';

@Component({
    selector: 'jhi-login-modal',
    templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    loginForm: FormGroup;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private socialService: SocialService,
        private router: Router,
        private fb: FormBuilder,
        public dialogRef: MatDialogRef<JhiLoginModalComponent>,
        //public activeModal: NgbActiveModal
    ) {
        this.loginForm = this.fb.group({
            password: ['', Validators.required],
            username: ['', Validators.required],
            rememberMe: ['', ]
        });

        this.credentials = {};
    }

    ngAfterViewInit() {
       // this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
    //    this.activeModal.dismiss('cancel');
    }

    login(value) {
        this.loginService.login(value).then(() => {
            this.authenticationError = false;
       //     this.activeModal.dismiss('login success');
            if (this.router.url === '/register' || (/activate/.test(this.router.url)) ||
                this.router.url === '/finishReset' || this.router.url === '/requestReset') {
                this.router.navigate(['']);
            }

            this.eventManager.broadcast({
                name: 'authenticationSuccess',
                content: 'Sending Authentication Success'
            });

            // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
            // // since login is succesful, go to stored previousState and clear previousState
            const redirect = this.stateStorageService.getUrl();
            if (redirect) {
                this.router.navigate([redirect]);
            } else {
                this.dialogRef.close('login success');
            }
        }).catch(() => {
            this.authenticationError = true;
        });
    }

    register() {
     //   this.activeModal.dismiss('to state register');
     this.dialogRef.close('go-reset-password');
        this.router.navigate(['/register']);
    }

    requestResetPassword() {
        this.dialogRef.close('go-reset-password');
       // this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
    }
}
