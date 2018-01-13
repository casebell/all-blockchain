import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CoinBoardComment e2e test', () => {

    let navBarPage: NavBarPage;
    let coinBoardCommentDialogPage: CoinBoardCommentDialogPage;
    let coinBoardCommentComponentsPage: CoinBoardCommentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CoinBoardComments', () => {
        navBarPage.goToEntity('coin-board-comment-block-chain-info');
        coinBoardCommentComponentsPage = new CoinBoardCommentComponentsPage();
        expect(coinBoardCommentComponentsPage.getTitle()).toMatch(/blockchainApp.coinBoardComment.home.title/);

    });

    it('should load create CoinBoardComment dialog', () => {
        coinBoardCommentComponentsPage.clickOnCreateButton();
        coinBoardCommentDialogPage = new CoinBoardCommentDialogPage();
        expect(coinBoardCommentDialogPage.getModalTitle()).toMatch(/blockchainApp.coinBoardComment.home.createOrEditLabel/);
        coinBoardCommentDialogPage.close();
    });

    it('should create and save CoinBoardComments', () => {
        coinBoardCommentComponentsPage.clickOnCreateButton();
        coinBoardCommentDialogPage.setContextInput('context');
        expect(coinBoardCommentDialogPage.getContextInput()).toMatch('context');
        coinBoardCommentDialogPage.setGroupNoInput('5');
        expect(coinBoardCommentDialogPage.getGroupNoInput()).toMatch('5');
        coinBoardCommentDialogPage.setParentInput('5');
        expect(coinBoardCommentDialogPage.getParentInput()).toMatch('5');
        coinBoardCommentDialogPage.setDepthInput('5');
        expect(coinBoardCommentDialogPage.getDepthInput()).toMatch('5');
        coinBoardCommentDialogPage.setCreatedatInput(12310020012301);
        expect(coinBoardCommentDialogPage.getCreatedatInput()).toMatch('2001-12-31T02:30');
        coinBoardCommentDialogPage.setUpdatedatInput(12310020012301);
        expect(coinBoardCommentDialogPage.getUpdatedatInput()).toMatch('2001-12-31T02:30');
        coinBoardCommentDialogPage.coinBoardSelectLastOption();
        coinBoardCommentDialogPage.userSelectLastOption();
        coinBoardCommentDialogPage.save();
        expect(coinBoardCommentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CoinBoardCommentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-coin-board-comment-block-chain-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CoinBoardCommentDialogPage {
    modalTitle = element(by.css('h4#myCoinBoardCommentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    contextInput = element(by.css('input#field_context'));
    groupNoInput = element(by.css('input#field_groupNo'));
    parentInput = element(by.css('input#field_parent'));
    depthInput = element(by.css('input#field_depth'));
    createdatInput = element(by.css('input#field_createdat'));
    updatedatInput = element(by.css('input#field_updatedat'));
    coinBoardSelect = element(by.css('select#field_coinBoard'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setContextInput = function(context) {
        this.contextInput.sendKeys(context);
    }

    getContextInput = function() {
        return this.contextInput.getAttribute('value');
    }

    setGroupNoInput = function(groupNo) {
        this.groupNoInput.sendKeys(groupNo);
    }

    getGroupNoInput = function() {
        return this.groupNoInput.getAttribute('value');
    }

    setParentInput = function(parent) {
        this.parentInput.sendKeys(parent);
    }

    getParentInput = function() {
        return this.parentInput.getAttribute('value');
    }

    setDepthInput = function(depth) {
        this.depthInput.sendKeys(depth);
    }

    getDepthInput = function() {
        return this.depthInput.getAttribute('value');
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

    coinBoardSelectLastOption = function() {
        this.coinBoardSelect.all(by.tagName('option')).last().click();
    }

    coinBoardSelectOption = function(option) {
        this.coinBoardSelect.sendKeys(option);
    }

    getCoinBoardSelect = function() {
        return this.coinBoardSelect;
    }

    getCoinBoardSelectedOption = function() {
        return this.coinBoardSelect.element(by.css('option:checked')).getText();
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
