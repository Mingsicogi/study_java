"use strict";
exports.__esModule = true;
var commands_1 = require("../../support/commands");
describe('/admin', function () {
    var _a, _b;
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'admin';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'admin';
    beforeEach(function () {
        cy.login(username, password);
        cy.visit('');
    });
    describe('/user-management', function () {
        it('should load the page', function () {
            cy.clickOnAdminMenuItem('user-management');
            cy.get(commands_1.userManagementPageHeadingSelector).should('be.visible');
        });
    });
    describe('/metrics', function () {
        it('should load the page', function () {
            cy.clickOnAdminMenuItem('metrics');
            cy.get(commands_1.metricsPageHeadingSelector).should('be.visible');
        });
    });
    describe('/health', function () {
        it('should load the page', function () {
            cy.clickOnAdminMenuItem('health');
            cy.get(commands_1.healthPageHeadingSelector).should('be.visible');
        });
    });
    describe('/logs', function () {
        it('should load the page', function () {
            cy.clickOnAdminMenuItem('logs');
            cy.get(commands_1.logsPageHeadingSelector).should('be.visible');
        });
    });
    describe('/configuration', function () {
        it('should load the page', function () {
            cy.clickOnAdminMenuItem('configuration');
            cy.get(commands_1.configurationPageHeadingSelector).should('be.visible');
        });
    });
    describe('/docs', function () {
        it('should load the page', function () {
            cy.getManagementInfo().then(function (info) {
                if (info.activeProfiles.includes('api-docs')) {
                    cy.clickOnAdminMenuItem('docs');
                    cy.get(commands_1.swaggerFrameSelector)
                        .should('be.visible')
                        .then(function () {
                        // Wait iframe to load
                        cy.wait(500); // eslint-disable-line cypress/no-unnecessary-waiting
                        cy.get(commands_1.swaggerFrameSelector).its('0.contentDocument.body').find(commands_1.swaggerPageSelector).should('be.visible');
                    });
                }
            });
        });
    });
});
