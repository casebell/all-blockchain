import { Component, OnInit, AfterViewInit, Renderer, ElementRef, ViewEncapsulation } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { Register } from './register.service';
import { LoginModalService } from '../../shared';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MdSnackBar } from '@angular/material';
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styleUrls: ['register.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit, AfterViewInit {

    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    registerForm:FormGroup
    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private fb: FormBuilder,
        private snackBar: MdSnackBar,
        private translateService: TranslateService
    ) {
        this.registerForm = this.fb.group({
            login: ['', [Validators.required,
                         Validators.minLength(1),
                         Validators.maxLength(50)]],
            password: ['',Validators.compose([Validators.required,
                       Checkpassword.checkPasswordPattern])],
            email: ['', Validators.required]
        });
    }
    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        console.log('translate ', this.translateService.instant('blockchainApp.resource.home.title'))
        console.log('translate ', this.translateService.instant('global.form.email.placeholder'))
    }

    ngAfterViewInit() {
        /* this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []); */
    }

    register(register) {
        this.error = null;
        this.errorUserExists = null;
        this.errorEmailExists = null;

        this.languageService.getCurrent().then((key) => {
            register.value.langKey = key;
          //  this.registerAccount.langKey = key;
            this.registerService.save(register.value).subscribe(() => {
                this.success = true;
            }, (response) => this.processError(response));
        });
       /*  if(register.hasError('passwordNotSame')){
            this.openSnackBar('비밀번호와 비밀번호 확인이 다릅니다. ','');
        } */
        /*  this.languageService.getCurrent().then((key) => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(() => {
                    this.success = true;
                }, (response) => this.processError(response));
            }); */
       /*  if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then((key) => {
                this.registerAccount.langKey = key;
                this.registerService.save(this.registerAccount).subscribe(() => {
                    this.success = true;
                }, (response) => this.processError(response));
            });
        } */
    }

    get getLogin(){
        return this.registerForm.get('login');
    }

    get getPassword(){
        return this.registerForm.get('password');
    }


    get getEmail() {
        return this.registerForm.get('email');
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response) {
        console.log('error response : ', response.error);
        console.log('error response : ', response.error.code);
        console.log('translate ', this.translateService.instant('blockchainApp.resource.home.title'))
        if (response.status === 400 && response.error.code === 'duplicated.login.exception') {
            console.log('test');
            this.errorUserExists='ERROR';
        } else if (response.status === 400 && response.error.code === 'duplicated.email.exception') {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error ='ERROR';
        }
    }

    openSnackBar(message: string,action:string) {
        this.snackBar.open(message, action, {
          duration: 2000,
          extraClasses:['custom-snack-bar'],
        });
      }
}

export class Checkpassword {
    static checkPasswordPattern(control: FormControl){
        const passwordRegx = new RegExp("^(?=.*[a-z])(?=.*[0-9])(?=.{6,100})")
            if (!passwordRegx.test(control.value)) {
              return {'passwordPattern' : true};
            }
        return null;
    }
}