import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Coin e2e test', () => {

    let navBarPage: NavBarPage;
    let coinDialogPage: CoinDialogPage;
    let coinComponentsPage: CoinComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Coins', () => {
        navBarPage.goToEntity('coin-block-chain-info');
        coinComponentsPage = new CoinComponentsPage();
        expect(coinComponentsPage.getTitle())
            .toMatch(/blockchainApp.coin.home.title/);

    });

    it('should load create Coin dialog', () => {
        coinComponentsPage.clickOnCreateButton();
        coinDialogPage = new CoinDialogPage();
        expect(coinDialogPage.getModalTitle())
            .toMatch(/blockchainApp.coin.home.createOrEditLabel/);
        coinDialogPage.close();
    });

    it('should create and save Coins', () => {
        coinComponentsPage.clickOnCreateButton();
        coinDialogPage.setNameInput('name');
        expect(coinDialogPage.getNameInput()).toMatch('name');
        coinDialogPage.setFounderInput('founder');
        expect(coinDialogPage.getFounderInput()).toMatch('founder');
        coinDialogPage.consensusAlgorithmsSelectLastOption();
        coinDialogPage.setHomepageInput('homepage');
        expect(coinDialogPage.getHomepageInput()).toMatch('homepage');
        coinDialogPage.setWhitePaperInput('whitePaper');
        expect(coinDialogPage.getWhitePaperInput()).toMatch('whitePaper');
        coinDialogPage.setContextInput('context');
        expect(coinDialogPage.getContextInput()).toMatch('context');
        coinDialogPage.setReleaseatInput(12310020012301);
        expect(coinDialogPage.getReleaseatInput()).toMatch('2001-12-31T02:30');
        coinDialogPage.setCreatedatInput(12310020012301);
        expect(coinDialogPage.getCreatedatInput()).toMatch('2001-12-31T02:30');
        coinDialogPage.setUpdatedatInput(12310020012301);
        expect(coinDialogPage.getUpdatedatInput()).toMatch('2001-12-31T02:30');
        coinDialogPage.save();
        expect(coinDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CoinComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-coin-block-chain-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CoinDialogPage {
    modalTitle = element(by.css('h4#myCoinLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    founderInput = element(by.css('input#field_founder'));
    consensusAlgorithmsSelect = element(by.css('select#field_consensusAlgorithms'));
    homepageInput = element(by.css('input#field_homepage'));
    whitePaperInput = element(by.css('input#field_whitePaper'));
    contextInput = element(by.css('input#field_context'));
    releaseatInput = element(by.css('input#field_releaseat'));
    createdatInput = element(by.css('input#field_createdat'));
    updatedatInput = element(by.css('input#field_updatedat'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setFounderInput = function(founder) {
        this.founderInput.sendKeys(founder);
    };

    getFounderInput = function() {
        return this.founderInput.getAttribute('value');
    };

    setConsensusAlgorithmsSelect = function(consensusAlgorithms) {
        this.consensusAlgorithmsSelect.sendKeys(consensusAlgorithms);
    };

    getConsensusAlgorithmsSelect = function() {
        return this.consensusAlgorithmsSelect.element(by.css('option:checked')).getText();
    };

    consensusAlgorithmsSelectLastOption = function() {
        this.consensusAlgorithmsSelect.all(by.tagName('option')).last().click();
    };
    setHomepageInput = function(homepage) {
        this.homepageInput.sendKeys(homepage);
    };

    getHomepageInput = function() {
        return this.homepageInput.getAttribute('value');
    };

    setWhitePaperInput = function(whitePaper) {
        this.whitePaperInput.sendKeys(whitePaper);
    };

    getWhitePaperInput = function() {
        return this.whitePaperInput.getAttribute('value');
    };

    setContextInput = function(context) {
        this.contextInput.sendKeys(context);
    };

    getContextInput = function() {
        return this.contextInput.getAttribute('value');
    };

    setReleaseatInput = function(releaseat) {
        this.releaseatInput.sendKeys(releaseat);
    };

    getReleaseatInput = function() {
        return this.releaseatInput.getAttribute('value');
    };

    setCreatedatInput = function(createdat) {
        this.createdatInput.sendKeys(createdat);
    };

    getCreatedatInput = function() {
        return this.createdatInput.getAttribute('value');
    };

    setUpdatedatInput = function(updatedat) {
        this.updatedatInput.sendKeys(updatedat);
    };

    getUpdatedatInput = function() {
        return this.updatedatInput.getAttribute('value');
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
