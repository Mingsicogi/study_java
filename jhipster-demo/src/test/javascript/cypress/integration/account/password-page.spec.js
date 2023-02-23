"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('/account/password', function () {
    var _a, _b;
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    beforeEach(function () {
        cy.login(username, password);
        cy.visit('/account/password');
    });
    beforeEach(function () {
        cy.intercept('POST', '/api/account/change-password').as('passwordSave');
    });
    it('should be accessible through menu', function () {
        cy.visit('');
        cy.clickOnPasswordItem();
        cy.url().should('match', /\/account\/password$/);
    });
    it('requires current password', function () {
        cy.get(commands_1.currentPasswordSelector)
            .should('have.class', commands_1.classInvalid)
            .type('wrong-current-password')
            .blur()
            .should('have.class', commands_1.classValid);
    });
    it('requires new password', function () {
        cy.get(commands_1.newPasswordSelector).should('have.class', commands_1.classInvalid).type('jhipster').blur().should('have.class', commands_1.classValid);
    });
    it('requires confirm new password', function () {
        cy.get(commands_1.newPasswordSelector).type('jhipster');
        cy.get(commands_1.confirmPasswordSelector).should('have.class', commands_1.classInvalid).type('jhipster').blur().should('have.class', commands_1.classValid);
    });
    it('should fail to update password when using incorrect current password', function () {
        cy.get(commands_1.currentPasswordSelector).type('wrong-current-password');
        cy.get(commands_1.newPasswordSelector).type('jhipster');
        cy.get(commands_1.confirmPasswordSelector).type('jhipster');
        cy.get(commands_1.submitPasswordSelector).click();
        cy.wait('@passwordSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(400);
        });
    });
    it('should be able to update password', function () {
        cy.get(commands_1.currentPasswordSelector).type(password);
        cy.get(commands_1.newPasswordSelector).type(password);
        cy.get(commands_1.confirmPasswordSelector).type(password);
        cy.get(commands_1.submitPasswordSelector).click();
        cy.wait('@passwordSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
    });
});
