import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CoinBoard e2e test', () => {

    let navBarPage: NavBarPage;
    let coinBoardDialogPage: CoinBoardDialogPage;
    let coinBoardComponentsPage: CoinBoardComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CoinBoards', () => {
        navBarPage.goToEntity('coin-board-block-chain-info');
        coinBoardComponentsPage = new CoinBoardComponentsPage();
        expect(coinBoardComponentsPage.getTitle())
            .toMatch(/blockchainApp.coinBoard.home.title/);

    });

    it('should load create CoinBoard dialog', () => {
        coinBoardComponentsPage.clickOnCreateButton();
        coinBoardDialogPage = new CoinBoardDialogPage();
        expect(coinBoardDialogPage.getModalTitle())
            .toMatch(/blockchainApp.coinBoard.home.createOrEditLabel/);
        coinBoardDialogPage.close();
    });

    it('should create and save CoinBoards', () => {
        coinBoardComponentsPage.clickOnCreateButton();
        coinBoardDialogPage.setTitleInput('title');
        expect(coinBoardDialogPage.getTitleInput()).toMatch('title');
        coinBoardDialogPage.setContextInput('context');
        expect(coinBoardDialogPage.getContextInput()).toMatch('context');
        coinBoardDialogPage.coninBoardTypeSelectLastOption();
        coinBoardDialogPage.setCreatedatInput(12310020012301);
        expect(coinBoardDialogPage.getCreatedatInput()).toMatch('2001-12-31T02:30');
        coinBoardDialogPage.setUpdatedatInput(12310020012301);
        expect(coinBoardDialogPage.getUpdatedatInput()).toMatch('2001-12-31T02:30');
        coinBoardDialogPage.coinSelectLastOption();
        coinBoardDialogPage.userSelectLastOption();
        coinBoardDialogPage.save();
        expect(coinBoardDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CoinBoardComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-coin-board-block-chain-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CoinBoardDialogPage {
    modalTitle = element(by.css('h4#myCoinBoardLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    contextInput = element(by.css('input#field_context'));
    coninBoardTypeSelect = element(by.css('select#field_coninBoardType'));
    createdatInput = element(by.css('input#field_createdat'));
    updatedatInput = element(by.css('input#field_updatedat'));
    coinSelect = element(by.css('select#field_coin'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    }

    setContextInput = function(context) {
        this.contextInput.sendKeys(context);
    }

    getContextInput = function() {
        return this.contextInput.getAttribute('value');
    }

    setConinBoardTypeSelect = function(coninBoardType) {
        this.coninBoardTypeSelect.sendKeys(coninBoardType);
    }

    getConinBoardTypeSelect = function() {
        return this.coninBoardTypeSelect.element(by.css('option:checked')).getText();
    }

    coninBoardTypeSelectLastOption = function() {
        this.coninBoardTypeSelect.all(by.tagName('option')).last().click();
    }
    setCreatedatInput = function(createdat) {
        this.createdatInput.sendKeys(createdat);
    }

    getCreatedatInput = function() {
        return this.createdatInput.getAttribute('value');
    }

    setUpdatedatInput = function(updatedat) {
        this.updatedatInput.sendKeys(updatedat);
    }

    getUpdatedatInput = function() {
        return this.updatedatInput.getAttribute('value');
    }

    coinSelectLastOption = function() {
        this.coinSelect.all(by.tagName('option')).last().click();
    }

    coinSelectOption = function(option) {
        this.coinSelect.sendKeys(option);
    }

    getCoinSelect = function() {
        return this.coinSelect;
    }

    getCoinSelectedOption = function() {
        return this.coinSelect.element(by.css('option:checked')).getText();
    }

    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function() {
        return this.userSelect;
    }

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
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
