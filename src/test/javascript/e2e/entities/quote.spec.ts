import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Quote e2e test', () => {

    let navBarPage: NavBarPage;
    let quoteDialogPage: QuoteDialogPage;
    let quoteComponentsPage: QuoteComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Quotes', () => {
        navBarPage.goToEntity('quote');
        quoteComponentsPage = new QuoteComponentsPage();
        expect(quoteComponentsPage.getTitle())
            .toMatch(/blockchainApp.quote.home.title/);

    });

    it('should load create Quote dialog', () => {
        quoteComponentsPage.clickOnCreateButton();
        quoteDialogPage = new QuoteDialogPage();
        expect(quoteDialogPage.getModalTitle())
            .toMatch(/blockchainApp.quote.home.createOrEditLabel/);
        quoteDialogPage.close();
    });

    it('should create and save Quotes', () => {
        quoteComponentsPage.clickOnCreateButton();
        quoteDialogPage.setLastPriceInput('5');
        expect(quoteDialogPage.getLastPriceInput()).toMatch('5');
        quoteDialogPage.setVolumeInput('5');
        expect(quoteDialogPage.getVolumeInput()).toMatch('5');
        quoteDialogPage.setLowPriceInput('5');
        expect(quoteDialogPage.getLowPriceInput()).toMatch('5');
        quoteDialogPage.setHighPriceInput('5');
        expect(quoteDialogPage.getHighPriceInput()).toMatch('5');
        quoteDialogPage.setAvgPriceInput('5');
        expect(quoteDialogPage.getAvgPriceInput()).toMatch('5');
        quoteDialogPage.setBuyPriceInput('5');
        expect(quoteDialogPage.getBuyPriceInput()).toMatch('5');
        quoteDialogPage.setSellPriceInput('5');
        expect(quoteDialogPage.getSellPriceInput()).toMatch('5');
        quoteDialogPage.setOpeningPriceInput('5');
        expect(quoteDialogPage.getOpeningPriceInput()).toMatch('5');
        quoteDialogPage.setClosingPriceInput('5');
        expect(quoteDialogPage.getClosingPriceInput()).toMatch('5');
        quoteDialogPage.setQuoteTimeInput(12310020012301);
        expect(quoteDialogPage.getQuoteTimeInput()).toMatch('2001-12-31T02:30');
        quoteDialogPage.marketCoinSelectLastOption();
        quoteDialogPage.save();
        expect(quoteDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class QuoteComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-quote div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class QuoteDialogPage {
    modalTitle = element(by.css('h4#myQuoteLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    lastPriceInput = element(by.css('input#field_lastPrice'));
    volumeInput = element(by.css('input#field_volume'));
    lowPriceInput = element(by.css('input#field_lowPrice'));
    highPriceInput = element(by.css('input#field_highPrice'));
    avgPriceInput = element(by.css('input#field_avgPrice'));
    buyPriceInput = element(by.css('input#field_buyPrice'));
    sellPriceInput = element(by.css('input#field_sellPrice'));
    openingPriceInput = element(by.css('input#field_openingPrice'));
    closingPriceInput = element(by.css('input#field_closingPrice'));
    quoteTimeInput = element(by.css('input#field_quoteTime'));
    marketCoinSelect = element(by.css('select#field_marketCoin'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLastPriceInput = function(lastPrice) {
        this.lastPriceInput.sendKeys(lastPrice);
    }

    getLastPriceInput = function() {
        return this.lastPriceInput.getAttribute('value');
    }

    setVolumeInput = function(volume) {
        this.volumeInput.sendKeys(volume);
    }

    getVolumeInput = function() {
        return this.volumeInput.getAttribute('value');
    }

    setLowPriceInput = function(lowPrice) {
        this.lowPriceInput.sendKeys(lowPrice);
    }

    getLowPriceInput = function() {
        return this.lowPriceInput.getAttribute('value');
    }

    setHighPriceInput = function(highPrice) {
        this.highPriceInput.sendKeys(highPrice);
    }

    getHighPriceInput = function() {
        return this.highPriceInput.getAttribute('value');
    }

    setAvgPriceInput = function(avgPrice) {
        this.avgPriceInput.sendKeys(avgPrice);
    }

    getAvgPriceInput = function() {
        return this.avgPriceInput.getAttribute('value');
    }

    setBuyPriceInput = function(buyPrice) {
        this.buyPriceInput.sendKeys(buyPrice);
    }

    getBuyPriceInput = function() {
        return this.buyPriceInput.getAttribute('value');
    }

    setSellPriceInput = function(sellPrice) {
        this.sellPriceInput.sendKeys(sellPrice);
    }

    getSellPriceInput = function() {
        return this.sellPriceInput.getAttribute('value');
    }

    setOpeningPriceInput = function(openingPrice) {
        this.openingPriceInput.sendKeys(openingPrice);
    }

    getOpeningPriceInput = function() {
        return this.openingPriceInput.getAttribute('value');
    }

    setClosingPriceInput = function(closingPrice) {
        this.closingPriceInput.sendKeys(closingPrice);
    }

    getClosingPriceInput = function() {
        return this.closingPriceInput.getAttribute('value');
    }

    setQuoteTimeInput = function(quoteTime) {
        this.quoteTimeInput.sendKeys(quoteTime);
    }

    getQuoteTimeInput = function() {
        return this.quoteTimeInput.getAttribute('value');
    }

    marketCoinSelectLastOption = function() {
        this.marketCoinSelect.all(by.tagName('option')).last().click();
    }

    marketCoinSelectOption = function(option) {
        this.marketCoinSelect.sendKeys(option);
    }

    getMarketCoinSelect = function() {
        return this.marketCoinSelect;
    }

    getMarketCoinSelectedOption = function() {
        return this.marketCoinSelect.element(by.css('option:checked')).getText();
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
