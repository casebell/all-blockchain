import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Kraken e2e test', () => {

    let navBarPage: NavBarPage;
    let krakenDialogPage: KrakenDialogPage;
    let krakenComponentsPage: KrakenComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Krakens', () => {
        navBarPage.goToEntity('kraken');
        krakenComponentsPage = new KrakenComponentsPage();
        expect(krakenComponentsPage.getTitle()).toMatch(/blockchainApp.kraken.home.title/);

    });

    it('should load create Kraken dialog', () => {
        krakenComponentsPage.clickOnCreateButton();
        krakenDialogPage = new KrakenDialogPage();
        expect(krakenDialogPage.getModalTitle()).toMatch(/blockchainApp.kraken.home.createOrEditLabel/);
        krakenDialogPage.close();
    });

    it('should create and save Krakens', () => {
        krakenComponentsPage.clickOnCreateButton();
        krakenDialogPage.setLastInput('last');
        expect(krakenDialogPage.getLastInput()).toMatch('last');
        krakenDialogPage.setCreatedatInput(12310020012301);
        expect(krakenDialogPage.getCreatedatInput()).toMatch('2001-12-31T02:30');
        krakenDialogPage.save();
        expect(krakenDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class KrakenComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-kraken div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class KrakenDialogPage {
    modalTitle = element(by.css('h4#myKrakenLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    lastInput = element(by.css('input#field_last'));
    createdatInput = element(by.css('input#field_createdat'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLastInput = function(last) {
        this.lastInput.sendKeys(last);
    }

    getLastInput = function() {
        return this.lastInput.getAttribute('value');
    }

    setCreatedatInput = function(createdat) {
        this.createdatInput.sendKeys(createdat);
    }

    getCreatedatInput = function() {
        return this.createdatInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
