"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('Spid e2e test', function () {
    var _a, _b;
    var spidPageUrl = '/spid';
    var spidPageUrlPattern = new RegExp('/spid(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var spidSample = {};
    var spid;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/spids+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/spids').as('postEntityRequest');
        cy.intercept('DELETE', '/api/spids/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (spid) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/spids/".concat(spid.id)
            }).then(function () {
                spid = undefined;
            });
        }
    });
    it('Spids menu should load Spids page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('spid');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('Spid').should('exist');
        cy.url().should('match', spidPageUrlPattern);
    });
    describe('Spid page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(spidPageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create Spid page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/spid/new$'));
                cy.getEntityCreateUpdateHeading('Spid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', spidPageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/spids',
                    body: spidSample
                }).then(function (_a) {
                    var body = _a.body;
                    spid = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/spids+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [spid]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(spidPageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details Spid page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('spid');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', spidPageUrlPattern);
            });
            it('edit button click should load edit Spid page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('Spid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', spidPageUrlPattern);
            });
            it('last delete button click should delete instance of Spid', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('spid').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', spidPageUrlPattern);
                spid = undefined;
            });
        });
    });
    describe('new Spid page', function () {
        beforeEach(function () {
            cy.visit("".concat(spidPageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('Spid');
        });
        it('should create an instance of Spid', function () {
            cy.get("[data-cy=\"spid\"]").type('66975').should('have.value', '66975');
            cy.get("[data-cy=\"email\"]").type('Matt23@yahoo.com').should('have.value', 'Matt23@yahoo.com');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                spid = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', spidPageUrlPattern);
        });
    });
});
