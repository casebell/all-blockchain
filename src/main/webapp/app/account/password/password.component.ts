import { Component, OnInit } from '@angular/core';
import { Principal } from '../../shared';
import { PasswordService } from './password.service';

@Component({
    selector: 'jhi-password',
    templateUrl: './password.component.html'
})
export class PasswordComponent implements OnInit {
    error: string;
    success: string;
    account: any;
    password: string;

    constructor(
        private passwordService: PasswordService,
        private principal: Principal
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    changePassword() {
        this.passwordService.save(this.password).subscribe(() => {
            this.error = null;
            this.success = 'OK';
        }, () => {
            this.success = null;
            this.error = 'ERROR';
        });
    }
}
