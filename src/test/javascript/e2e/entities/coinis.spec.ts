import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Coinis e2e test', () => {

    let navBarPage: NavBarPage;
    let coinisDialogPage: CoinisDialogPage;
    let coinisComponentsPage: CoinisComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Coinis', () => {
        navBarPage.goToEntity('coinis');
        coinisComponentsPage = new CoinisComponentsPage();
        expect(coinisComponentsPage.getTitle())
            .toMatch(/blockchainApp.coinis.home.title/);

    });

    it('should load create Coinis dialog', () => {
        coinisComponentsPage.clickOnCreateButton();
        coinisDialogPage = new CoinisDialogPage();
        expect(coinisDialogPage.getModalTitle())
            .toMatch(/blockchainApp.coinis.home.createOrEditLabel/);
        coinisDialogPage.close();
    });

    it('should create and save Coinis', () => {
        coinisComponentsPage.clickOnCreateButton();
        coinisDialogPage.setClosepriceInput('closeprice');
        expect(coinisDialogPage.getClosepriceInput()).toMatch('closeprice');
        coinisDialogPage.setHighpriceInput('highprice');
        expect(coinisDialogPage.getHighpriceInput()).toMatch('highprice');
        coinisDialogPage.setItemcodeInput('itemcode');
        expect(coinisDialogPage.getItemcodeInput()).toMatch('itemcode');
        coinisDialogPage.setLowpriceInput('lowprice');
        expect(coinisDialogPage.getLowpriceInput()).toMatch('lowprice');
        coinisDialogPage.setOpenpriceInput('openprice');
        expect(coinisDialogPage.getOpenpriceInput()).toMatch('openprice');
        coinisDialogPage.setPrevclosepriceInput('prevcloseprice');
        expect(coinisDialogPage.getPrevclosepriceInput()).toMatch('prevcloseprice');
        coinisDialogPage.setTradeamountInput('tradeamount');
        expect(coinisDialogPage.getTradeamountInput()).toMatch('tradeamount');
        coinisDialogPage.setTradedaebiInput('tradedaebi');
        expect(coinisDialogPage.getTradedaebiInput()).toMatch('tradedaebi');
        coinisDialogPage.setTradedaebirateInput('tradedaebirate');
        expect(coinisDialogPage.getTradedaebirateInput()).toMatch('tradedaebirate');
        coinisDialogPage.setTradedateInput('tradedate');
        expect(coinisDialogPage.getTradedateInput()).toMatch('tradedate');
        coinisDialogPage.setTradevolumnInput('tradevolumn');
        expect(coinisDialogPage.getTradevolumnInput()).toMatch('tradevolumn');
        coinisDialogPage.setSymbolInput('symbol');
        expect(coinisDialogPage.getSymbolInput()).toMatch('symbol');
        coinisDialogPage.setCreatedatInput(12310020012301);
        expect(coinisDialogPage.getCreatedatInput()).toMatch('2001-12-31T02:30');
        coinisDialogPage.save();
        expect(coinisDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CoinisComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-coinis div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CoinisDialogPage {
    modalTitle = element(by.css('h4#myCoinisLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    closepriceInput = element(by.css('input#field_closeprice'));
    highpriceInput = element(by.css('input#field_highprice'));
    itemcodeInput = element(by.css('input#field_itemcode'));
    lowpriceInput = element(by.css('input#field_lowprice'));
    openpriceInput = element(by.css('input#field_openprice'));
    prevclosepriceInput = element(by.css('input#field_prevcloseprice'));
    tradeamountInput = element(by.css('input#field_tradeamount'));
    tradedaebiInput = element(by.css('input#field_tradedaebi'));
    tradedaebirateInput = element(by.css('input#field_tradedaebirate'));
    tradedateInput = element(by.css('input#field_tradedate'));
    tradevolumnInput = element(by.css('input#field_tradevolumn'));
    symbolInput = element(by.css('input#field_symbol'));
    createdatInput = element(by.css('input#field_createdat'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setClosepriceInput = function(closeprice) {
        this.closepriceInput.sendKeys(closeprice);
    }

    getClosepriceInput = function() {
        return this.closepriceInput.getAttribute('value');
    }

    setHighpriceInput = function(highprice) {
        this.highpriceInput.sendKeys(highprice);
    }

    getHighpriceInput = function() {
        return this.highpriceInput.getAttribute('value');
    }

    setItemcodeInput = function(itemcode) {
        this.itemcodeInput.sendKeys(itemcode);
    }

    getItemcodeInput = function() {
        return this.itemcodeInput.getAttribute('value');
    }

    setLowpriceInput = function(lowprice) {
        this.lowpriceInput.sendKeys(lowprice);
    }

    getLowpriceInput = function() {
        return this.lowpriceInput.getAttribute('value');
    }

    setOpenpriceInput = function(openprice) {
        this.openpriceInput.sendKeys(openprice);
    }

    getOpenpriceInput = function() {
        return this.openpriceInput.getAttribute('value');
    }

    setPrevclosepriceInput = function(prevcloseprice) {
        this.prevclosepriceInput.sendKeys(prevcloseprice);
    }

    getPrevclosepriceInput = function() {
        return this.prevclosepriceInput.getAttribute('value');
    }

    setTradeamountInput = function(tradeamount) {
        this.tradeamountInput.sendKeys(tradeamount);
    }

    getTradeamountInput = function() {
        return this.tradeamountInput.getAttribute('value');
    }

    setTradedaebiInput = function(tradedaebi) {
        this.tradedaebiInput.sendKeys(tradedaebi);
    }

    getTradedaebiInput = function() {
        return this.tradedaebiInput.getAttribute('value');
    }

    setTradedaebirateInput = function(tradedaebirate) {
        this.tradedaebirateInput.sendKeys(tradedaebirate);
    }

    getTradedaebirateInput = function() {
        return this.tradedaebirateInput.getAttribute('value');
    }

    setTradedateInput = function(tradedate) {
        this.tradedateInput.sendKeys(tradedate);
    }

    getTradedateInput = function() {
        return this.tradedateInput.getAttribute('value');
    }

    setTradevolumnInput = function(tradevolumn) {
        this.tradevolumnInput.sendKeys(tradevolumn);
    }

    getTradevolumnInput = function() {
        return this.tradevolumnInput.getAttribute('value');
    }

    setSymbolInput = function(symbol) {
        this.symbolInput.sendKeys(symbol);
    }

    getSymbolInput = function() {
        return this.symbolInput.getAttribute('value');
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
