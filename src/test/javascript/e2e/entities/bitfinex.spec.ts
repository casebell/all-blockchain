import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Bitfinex e2e test', () => {

    let navBarPage: NavBarPage;
    let bitfinexDialogPage: BitfinexDialogPage;
    let bitfinexComponentsPage: BitfinexComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Bitfinexes', () => {
        navBarPage.goToEntity('bitfinex');
        bitfinexComponentsPage = new BitfinexComponentsPage();
        expect(bitfinexComponentsPage.getTitle()).toMatch(/blockchainApp.bitfinex.home.title/);

    });

    it('should load create Bitfinex dialog', () => {
        bitfinexComponentsPage.clickOnCreateButton();
        bitfinexDialogPage = new BitfinexDialogPage();
        expect(bitfinexDialogPage.getModalTitle()).toMatch(/blockchainApp.bitfinex.home.createOrEditLabel/);
        bitfinexDialogPage.close();
    });

    it('should create and save Bitfinexes', () => {
        bitfinexComponentsPage.clickOnCreateButton();
        bitfinexDialogPage.setMidInput('mid');
        expect(bitfinexDialogPage.getMidInput()).toMatch('mid');
        bitfinexDialogPage.setBidInput('bid');
        expect(bitfinexDialogPage.getBidInput()).toMatch('bid');
        bitfinexDialogPage.setAskInput('ask');
        expect(bitfinexDialogPage.getAskInput()).toMatch('ask');
        bitfinexDialogPage.setLast_priceInput('last_price');
        expect(bitfinexDialogPage.getLast_priceInput()).toMatch('last_price');
        bitfinexDialogPage.setLowInput('low');
        expect(bitfinexDialogPage.getLowInput()).toMatch('low');
        bitfinexDialogPage.setHighInput('high');
        expect(bitfinexDialogPage.getHighInput()).toMatch('high');
        bitfinexDialogPage.setVolumeInput('volume');
        expect(bitfinexDialogPage.getVolumeInput()).toMatch('volume');
        bitfinexDialogPage.setTimestampInput('timestamp');
        expect(bitfinexDialogPage.getTimestampInput()).toMatch('timestamp');
        bitfinexDialogPage.save();
        expect(bitfinexDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BitfinexComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-bitfinex div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BitfinexDialogPage {
    modalTitle = element(by.css('h4#myBitfinexLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    midInput = element(by.css('input#field_mid'));
    bidInput = element(by.css('input#field_bid'));
    askInput = element(by.css('input#field_ask'));
    last_priceInput = element(by.css('input#field_last_price'));
    lowInput = element(by.css('input#field_low'));
    highInput = element(by.css('input#field_high'));
    volumeInput = element(by.css('input#field_volume'));
    timestampInput = element(by.css('input#field_timestamp'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setMidInput = function(mid) {
        this.midInput.sendKeys(mid);
    }

    getMidInput = function() {
        return this.midInput.getAttribute('value');
    }

    setBidInput = function(bid) {
        this.bidInput.sendKeys(bid);
    }

    getBidInput = function() {
        return this.bidInput.getAttribute('value');
    }

    setAskInput = function(ask) {
        this.askInput.sendKeys(ask);
    }

    getAskInput = function() {
        return this.askInput.getAttribute('value');
    }

    setLast_priceInput = function(last_price) {
        this.last_priceInput.sendKeys(last_price);
    }

    getLast_priceInput = function() {
        return this.last_priceInput.getAttribute('value');
    }

    setLowInput = function(low) {
        this.lowInput.sendKeys(low);
    }

    getLowInput = function() {
        return this.lowInput.getAttribute('value');
    }

    setHighInput = function(high) {
        this.highInput.sendKeys(high);
    }

    getHighInput = function() {
        return this.highInput.getAttribute('value');
    }

    setVolumeInput = function(volume) {
        this.volumeInput.sendKeys(volume);
    }

    getVolumeInput = function() {
        return this.volumeInput.getAttribute('value');
    }

    setTimestampInput = function(timestamp) {
        this.timestampInput.sendKeys(timestamp);
    }

    getTimestampInput = function() {
        return this.timestampInput.getAttribute('value');
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
