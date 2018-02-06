import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Resource e2e test', () => {

    let navBarPage: NavBarPage;
    let resourceDialogPage: ResourceDialogPage;
    let resourceComponentsPage: ResourceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Resources', () => {
        navBarPage.goToEntity('resource-block-chain-info');
        resourceComponentsPage = new ResourceComponentsPage();
        expect(resourceComponentsPage.getTitle())
            .toMatch(/blockchainApp.resource.home.title/);

    });

    it('should load create Resource dialog', () => {
        resourceComponentsPage.clickOnCreateButton();
        resourceDialogPage = new ResourceDialogPage();
        expect(resourceDialogPage.getModalTitle())
            .toMatch(/blockchainApp.resource.home.createOrEditLabel/);
        resourceDialogPage.close();
    });

    it('should create and save Resources', () => {
        resourceComponentsPage.clickOnCreateButton();
        resourceDialogPage.setUrlInput('url');
        expect(resourceDialogPage.getUrlInput()).toMatch('url');
        resourceDialogPage.setSizeInput('5');
        expect(resourceDialogPage.getSizeInput()).toMatch('5');
        resourceDialogPage.setCreatedAtInput(12310020012301);
        expect(resourceDialogPage.getCreatedAtInput()).toMatch('2001-12-31T02:30');
        resourceDialogPage.setUpdatedAtInput(12310020012301);
        expect(resourceDialogPage.getUpdatedAtInput()).toMatch('2001-12-31T02:30');
        resourceDialogPage.resourceTypeSelectLastOption();
        resourceDialogPage.coinSelectLastOption();
        resourceDialogPage.save();
        expect(resourceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ResourceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-resource-block-chain-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ResourceDialogPage {
    modalTitle = element(by.css('h4#myResourceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    urlInput = element(by.css('input#field_url'));
    sizeInput = element(by.css('input#field_size'));
    createdAtInput = element(by.css('input#field_createdAt'));
    updatedAtInput = element(by.css('input#field_updatedAt'));
    resourceTypeSelect = element(by.css('select#field_resourceType'));
    coinSelect = element(by.css('select#field_coin'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setUrlInput = function(url) {
        this.urlInput.sendKeys(url);
    };

    getUrlInput = function() {
        return this.urlInput.getAttribute('value');
    };

    setSizeInput = function(size) {
        this.sizeInput.sendKeys(size);
    };

    getSizeInput = function() {
        return this.sizeInput.getAttribute('value');
    };

    setCreatedAtInput = function(createdAt) {
        this.createdAtInput.sendKeys(createdAt);
    };

    getCreatedAtInput = function() {
        return this.createdAtInput.getAttribute('value');
    };

    setUpdatedAtInput = function(updatedAt) {
        this.updatedAtInput.sendKeys(updatedAt);
    };

    getUpdatedAtInput = function() {
        return this.updatedAtInput.getAttribute('value');
    };

    setResourceTypeSelect = function(resourceType) {
        this.resourceTypeSelect.sendKeys(resourceType);
    };

    getResourceTypeSelect = function() {
        return this.resourceTypeSelect.element(by.css('option:checked')).getText();
    };

    resourceTypeSelectLastOption = function() {
        this.resourceTypeSelect.all(by.tagName('option')).last().click();
    };
    coinSelectLastOption = function() {
        this.coinSelect.all(by.tagName('option')).last().click();
    };

    coinSelectOption = function(option) {
        this.coinSelect.sendKeys(option);
    };

    getCoinSelect = function() {
        return this.coinSelect;
    };

    getCoinSelectedOption = function() {
        return this.coinSelect.element(by.css('option:checked')).getText();
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
