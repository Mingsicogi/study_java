"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('login modal', function () {
    var _a, _b;
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    beforeEach(function () {
        cy.visit('');
        cy.clickOnLoginItem();
    });
    beforeEach(function () {
        cy.intercept('POST', '/api/authenticate').as('authenticate');
    });
    it('greets with signin', function () {
        cy.get(commands_1.titleLoginSelector).should('be.visible');
    });
    it('requires username', function () {
        cy.get(commands_1.passwordLoginSelector).type('a-password');
        cy.get(commands_1.submitLoginSelector).click();
        cy.wait('@authenticate').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(400);
        });
        // login page should stay open when login fails
        cy.get(commands_1.titleLoginSelector).should('be.visible');
    });
    it('requires password', function () {
        cy.get(commands_1.usernameLoginSelector).type('a-login');
        cy.get(commands_1.submitLoginSelector).click();
        cy.wait('@authenticate').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(400);
        });
        cy.get(commands_1.errorLoginSelector).should('be.visible');
    });
    it('errors when password is incorrect', function () {
        cy.get(commands_1.usernameLoginSelector).type(username);
        cy.get(commands_1.passwordLoginSelector).type('bad-password');
        cy.get(commands_1.submitLoginSelector).click();
        cy.wait('@authenticate').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(401);
        });
        cy.get(commands_1.errorLoginSelector).should('be.visible');
    });
    it('go to login page when successfully logs in', function () {
        cy.get(commands_1.usernameLoginSelector).type(username);
        cy.get(commands_1.passwordLoginSelector).type(password);
        cy.get(commands_1.submitLoginSelector).click();
        cy.wait('@authenticate').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(200);
        });
        cy.hash().should('eq', '');
    });
});
