"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('/account/settings', function () {
    var _a, _b, _c, _d;
    var adminUsername = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'admin';
    var adminPassword = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'admin';
    var username = (_c = Cypress.env('E2E_USERNAME')) !== null && _c !== void 0 ? _c : 'user';
    var password = (_d = Cypress.env('E2E_PASSWORD')) !== null && _d !== void 0 ? _d : 'user';
    beforeEach(function () {
        cy.login(username, password);
        cy.visit('/account/settings');
    });
    beforeEach(function () {
        cy.intercept('POST', '/api/account').as('settingsSave');
    });
    it('should be accessible through menu', function () {
        cy.visit('');
        cy.clickOnSettingsItem();
        cy.url().should('match', /\/account\/settings$/);
    });
    it("should be able to change 'user' firstname settings", function () {
        cy.get(commands_1.firstNameSettingsSelector).clear().type('jhipster');
        // need to modify email because default email does not match regex in vue
        cy.get(commands_1.emailSettingsSelector).clear().type('user@localhost.fr');
        cy.get(commands_1.submitSettingsSelector).click();
        cy.wait('@settingsSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
    });
    it("should be able to change 'user' lastname settings", function () {
        cy.get(commands_1.lastNameSettingsSelector).clear().type('retspihj');
        // need to modify email because default email does not match regex in vue
        cy.get(commands_1.emailSettingsSelector).clear().type('user@localhost.fr');
        cy.get(commands_1.submitSettingsSelector).click();
        cy.wait('@settingsSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
    });
    it("should be able to change 'user' email settings", function () {
        cy.get(commands_1.emailSettingsSelector).clear().type('user@localhost.fr');
        cy.get(commands_1.submitSettingsSelector).click();
        cy.wait('@settingsSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
    });
    describe('if there is another user with an email', function () {
        before(function () {
            cy.login(adminUsername, adminPassword);
            cy.visit('/account/settings');
            cy.get(commands_1.emailSettingsSelector).clear().type('admin@localhost.fr');
            cy.intercept({
                method: 'POST',
                url: '/api/account',
                times: 1
            }).as('settingsSave');
            cy.get(commands_1.submitSettingsSelector).click();
            cy.wait('@settingsSave');
        });
        it("should not be able to change 'user' email to same value", function () {
            cy.get(commands_1.emailSettingsSelector).clear().type('admin@localhost.fr');
            cy.get(commands_1.submitSettingsSelector).click();
            cy.wait('@settingsSave').then(function (_a) {
                var response = _a.response;
                return expect(response.statusCode).to.equal(400);
            });
        });
    });
});
