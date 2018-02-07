import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('AirDrop e2e test', () => {

    let navBarPage: NavBarPage;
    let airDropDialogPage: AirDropDialogPage;
    let airDropComponentsPage: AirDropComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AirDrops', () => {
        navBarPage.goToEntity('air-drop');
        airDropComponentsPage = new AirDropComponentsPage();
        expect(airDropComponentsPage.getTitle())
            .toMatch(/blockchainApp.airDrop.home.title/);

    });

    it('should load create AirDrop dialog', () => {
        airDropComponentsPage.clickOnCreateButton();
        airDropDialogPage = new AirDropDialogPage();
        expect(airDropDialogPage.getModalTitle())
            .toMatch(/blockchainApp.airDrop.home.createOrEditLabel/);
        airDropDialogPage.close();
    });

    it('should create and save AirDrops', () => {
        airDropComponentsPage.clickOnCreateButton();
        airDropDialogPage.setCoinNameInput('coinName');
        expect(airDropDialogPage.getCoinNameInput()).toMatch('coinName');
        airDropDialogPage.setStartDateInput(12310020012301);
        expect(airDropDialogPage.getStartDateInput()).toMatch('2001-12-31T02:30');
        airDropDialogPage.setFinishDateInput(12310020012301);
        expect(airDropDialogPage.getFinishDateInput()).toMatch('2001-12-31T02:30');
        airDropDialogPage.setContextInput('context');
        expect(airDropDialogPage.getContextInput()).toMatch('context');
        airDropDialogPage.setValueInput('5');
        expect(airDropDialogPage.getValueInput()).toMatch('5');
        airDropDialogPage.setTelegramInput('telegram');
        expect(airDropDialogPage.getTelegramInput()).toMatch('telegram');
        airDropDialogPage.setTwitterInput('twitter');
        expect(airDropDialogPage.getTwitterInput()).toMatch('twitter');
        airDropDialogPage.setFacebookInput('facebook');
        expect(airDropDialogPage.getFacebookInput()).toMatch('facebook');
        airDropDialogPage.setRedditInput('reddit');
        expect(airDropDialogPage.getRedditInput()).toMatch('reddit');
        airDropDialogPage.setWebsiteInput('website');
        expect(airDropDialogPage.getWebsiteInput()).toMatch('website');
        airDropDialogPage.setAirdropPageInput('airdropPage');
        expect(airDropDialogPage.getAirdropPageInput()).toMatch('airdropPage');
        airDropDialogPage.setBitcoinTalkInput('bitcoinTalk');
        expect(airDropDialogPage.getBitcoinTalkInput()).toMatch('bitcoinTalk');
        airDropDialogPage.save();
        expect(airDropDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AirDropComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-air-drop div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AirDropDialogPage {
    modalTitle = element(by.css('h4#myAirDropLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    coinNameInput = element(by.css('input#field_coinName'));
    startDateInput = element(by.css('input#field_startDate'));
    finishDateInput = element(by.css('input#field_finishDate'));
    contextInput = element(by.css('input#field_context'));
    valueInput = element(by.css('input#field_value'));
    telegramInput = element(by.css('input#field_telegram'));
    twitterInput = element(by.css('input#field_twitter'));
    facebookInput = element(by.css('input#field_facebook'));
    redditInput = element(by.css('input#field_reddit'));
    websiteInput = element(by.css('input#field_website'));
    airdropPageInput = element(by.css('input#field_airdropPage'));
    bitcoinTalkInput = element(by.css('input#field_bitcoinTalk'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCoinNameInput = function(coinName) {
        this.coinNameInput.sendKeys(coinName);
    };

    getCoinNameInput = function() {
        return this.coinNameInput.getAttribute('value');
    };

    setStartDateInput = function(startDate) {
        this.startDateInput.sendKeys(startDate);
    };

    getStartDateInput = function() {
        return this.startDateInput.getAttribute('value');
    };

    setFinishDateInput = function(finishDate) {
        this.finishDateInput.sendKeys(finishDate);
    };

    getFinishDateInput = function() {
        return this.finishDateInput.getAttribute('value');
    };

    setContextInput = function(context) {
        this.contextInput.sendKeys(context);
    };

    getContextInput = function() {
        return this.contextInput.getAttribute('value');
    };

    setValueInput = function(value) {
        this.valueInput.sendKeys(value);
    };

    getValueInput = function() {
        return this.valueInput.getAttribute('value');
    };

    setTelegramInput = function(telegram) {
        this.telegramInput.sendKeys(telegram);
    };

    getTelegramInput = function() {
        return this.telegramInput.getAttribute('value');
    };

    setTwitterInput = function(twitter) {
        this.twitterInput.sendKeys(twitter);
    };

    getTwitterInput = function() {
        return this.twitterInput.getAttribute('value');
    };

    setFacebookInput = function(facebook) {
        this.facebookInput.sendKeys(facebook);
    };

    getFacebookInput = function() {
        return this.facebookInput.getAttribute('value');
    };

    setRedditInput = function(reddit) {
        this.redditInput.sendKeys(reddit);
    };

    getRedditInput = function() {
        return this.redditInput.getAttribute('value');
    };

    setWebsiteInput = function(website) {
        this.websiteInput.sendKeys(website);
    };

    getWebsiteInput = function() {
        return this.websiteInput.getAttribute('value');
    };

    setAirdropPageInput = function(airdropPage) {
        this.airdropPageInput.sendKeys(airdropPage);
    };

    getAirdropPageInput = function() {
        return this.airdropPageInput.getAttribute('value');
    };

    setBitcoinTalkInput = function(bitcoinTalk) {
        this.bitcoinTalkInput.sendKeys(bitcoinTalk);
    };

    getBitcoinTalkInput = function() {
        return this.bitcoinTalkInput.getAttribute('value');
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
