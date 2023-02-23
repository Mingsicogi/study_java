"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('forgot your password', function () {
    var _a;
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    beforeEach(function () {
        cy.visit('');
        cy.clickOnLoginItem();
        cy.get(commands_1.usernameLoginSelector).type(username);
        cy.get(commands_1.forgetYourPasswordSelector).click();
    });
    beforeEach(function () {
        cy.intercept('POST', '/api/account/reset-password/init').as('initResetPassword');
    });
    it('requires email', function () {
        cy.get(commands_1.emailResetPasswordSelector).should('have.class', commands_1.classInvalid).type('user@gmail.com');
        cy.get(commands_1.emailResetPasswordSelector).should('have.class', commands_1.classValid);
    });
    it('should be able to init reset password', function () {
        cy.get(commands_1.emailResetPasswordSelector).type('user@gmail.com');
        cy.get(commands_1.submitInitResetPasswordSelector).click({ force: true });
        cy.wait('@initResetPassword').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
    });
});
