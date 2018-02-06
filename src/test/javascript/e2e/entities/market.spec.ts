import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Market e2e test', () => {

    let navBarPage: NavBarPage;
    let marketDialogPage: MarketDialogPage;
    let marketComponentsPage: MarketComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Markets', () => {
        navBarPage.goToEntity('market');
        marketComponentsPage = new MarketComponentsPage();
        expect(marketComponentsPage.getTitle())
            .toMatch(/blockchainApp.market.home.title/);

    });

    it('should load create Market dialog', () => {
        marketComponentsPage.clickOnCreateButton();
        marketDialogPage = new MarketDialogPage();
        expect(marketDialogPage.getModalTitle())
            .toMatch(/blockchainApp.market.home.createOrEditLabel/);
        marketDialogPage.close();
    });

    it('should create and save Markets', () => {
        marketComponentsPage.clickOnCreateButton();
        marketDialogPage.setNameInput('name');
        expect(marketDialogPage.getNameInput()).toMatch('name');
        marketDialogPage.setCountryInput('country');
        expect(marketDialogPage.getCountryInput()).toMatch('country');
        marketDialogPage.setUrlInput('url');
        expect(marketDialogPage.getUrlInput()).toMatch('url');
        marketDialogPage.setCurrencyInput('currency');
        expect(marketDialogPage.getCurrencyInput()).toMatch('currency');
        marketDialogPage.save();
        expect(marketDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MarketComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-market div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MarketDialogPage {
    modalTitle = element(by.css('h4#myMarketLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    countryInput = element(by.css('input#field_country'));
    urlInput = element(by.css('input#field_url'));
    currencyInput = element(by.css('input#field_currency'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setCountryInput = function(country) {
        this.countryInput.sendKeys(country);
    };

    getCountryInput = function() {
        return this.countryInput.getAttribute('value');
    };

    setUrlInput = function(url) {
        this.urlInput.sendKeys(url);
    };

    getUrlInput = function() {
        return this.urlInput.getAttribute('value');
    };

    setCurrencyInput = function(currency) {
        this.currencyInput.sendKeys(currency);
    };

    getCurrencyInput = function() {
        return this.currencyInput.getAttribute('value');
    };

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
