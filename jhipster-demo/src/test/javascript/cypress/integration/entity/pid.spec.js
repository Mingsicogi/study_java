"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('Pid e2e test', function () {
    var _a, _b;
    var pidPageUrl = '/pid';
    var pidPageUrlPattern = new RegExp('/pid(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var pidSample = {};
    var pid;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/pids+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/pids').as('postEntityRequest');
        cy.intercept('DELETE', '/api/pids/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (pid) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/pids/".concat(pid.id)
            }).then(function () {
                pid = undefined;
            });
        }
    });
    it('Pids menu should load Pids page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('pid');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('Pid').should('exist');
        cy.url().should('match', pidPageUrlPattern);
    });
    describe('Pid page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(pidPageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create Pid page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/pid/new$'));
                cy.getEntityCreateUpdateHeading('Pid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', pidPageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/pids',
                    body: pidSample
                }).then(function (_a) {
                    var body = _a.body;
                    pid = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/pids+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [pid]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(pidPageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details Pid page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('pid');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', pidPageUrlPattern);
            });
            it('edit button click should load edit Pid page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('Pid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', pidPageUrlPattern);
            });
            it('last delete button click should delete instance of Pid', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('pid').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', pidPageUrlPattern);
                pid = undefined;
            });
        });
    });
    describe('new Pid page', function () {
        beforeEach(function () {
            cy.visit("".concat(pidPageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('Pid');
        });
        it('should create an instance of Pid', function () {
            cy.get("[data-cy=\"pid\"]").type('57469').should('have.value', '57469');
            cy.get("[data-cy=\"phone\"]").type('931-613-0126 x1991').should('have.value', '931-613-0126 x1991');
            cy.get("[data-cy=\"ci\"]").type('navigate Fantastic').should('have.value', 'navigate Fantastic');
            cy.get("[data-cy=\"di\"]").type('green').should('have.value', 'green');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                pid = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', pidPageUrlPattern);
        });
    });
});
