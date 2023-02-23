"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('/register', function () {
    beforeEach(function () {
        cy.visit('/register');
    });
    beforeEach(function () {
        cy.intercept('POST', '/api/register').as('registerSave');
    });
    it('should be accessible through menu', function () {
        cy.visit('');
        cy.clickOnRegisterItem();
        cy.url().should('match', /\/register$/);
    });
    it('should load the register page', function () {
        cy.get(commands_1.submitRegisterSelector).should('be.visible');
    });
    it('requires username', function () {
        cy.get(commands_1.usernameRegisterSelector).should('have.class', commands_1.classInvalid).type('test').blur().should('have.class', commands_1.classValid);
    });
    it('should not accept invalid email', function () {
        cy.get(commands_1.emailRegisterSelector).should('have.class', commands_1.classInvalid).type('testtest.fr').blur().should('have.class', commands_1.classInvalid);
    });
    it('requires email in correct format', function () {
        cy.get(commands_1.emailRegisterSelector).should('have.class', commands_1.classInvalid).type('test@test.fr').blur().should('have.class', commands_1.classValid);
    });
    it('requires first password', function () {
        cy.get(commands_1.firstPasswordRegisterSelector).should('have.class', commands_1.classInvalid).type('test@test.fr').blur().should('have.class', commands_1.classValid);
    });
    it('requires password and confirm password to be same', function () {
        cy.get(commands_1.firstPasswordRegisterSelector).should('have.class', commands_1.classInvalid).type('test').blur().should('have.class', commands_1.classValid);
        cy.get(commands_1.secondPasswordRegisterSelector).should('have.class', commands_1.classInvalid).type('test').blur().should('have.class', commands_1.classValid);
    });
    it('requires password and confirm password have not the same value', function () {
        cy.get(commands_1.firstPasswordRegisterSelector).should('have.class', commands_1.classInvalid).type('test').blur().should('have.class', commands_1.classValid);
        cy.get(commands_1.secondPasswordRegisterSelector)
            .should('have.class', commands_1.classInvalid)
            .type('otherPassword')
            .blur()
            .should('have.class', commands_1.classInvalid);
    });
    it('register a valid user', function () {
        var randomEmail = 'Jamar_Sporer@hotmail.com';
        var randomUsername = 'Keith71';
        cy.get(commands_1.usernameRegisterSelector).type(randomUsername);
        cy.get(commands_1.emailRegisterSelector).type(randomEmail);
        cy.get(commands_1.firstPasswordRegisterSelector).type('jondoe');
        cy.get(commands_1.secondPasswordRegisterSelector).type('jondoe');
        cy.get(commands_1.submitRegisterSelector).click();
        cy.wait('@registerSave').then(function (_a) {
            var response = _a.response;
            return expect(response.statusCode).to.equal(201);
        });
    });
});
