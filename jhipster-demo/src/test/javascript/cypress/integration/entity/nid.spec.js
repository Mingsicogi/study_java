"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('Nid e2e test', function () {
    var _a, _b;
    var nidPageUrl = '/nid';
    var nidPageUrlPattern = new RegExp('/nid(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var nidSample = {};
    var nid;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/nids+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/nids').as('postEntityRequest');
        cy.intercept('DELETE', '/api/nids/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (nid) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/nids/".concat(nid.id)
            }).then(function () {
                nid = undefined;
            });
        }
    });
    it('Nids menu should load Nids page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('nid');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('Nid').should('exist');
        cy.url().should('match', nidPageUrlPattern);
    });
    describe('Nid page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(nidPageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create Nid page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/nid/new$'));
                cy.getEntityCreateUpdateHeading('Nid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', nidPageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/nids',
                    body: nidSample
                }).then(function (_a) {
                    var body = _a.body;
                    nid = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/nids+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [nid]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(nidPageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details Nid page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('nid');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', nidPageUrlPattern);
            });
            it('edit button click should load edit Nid page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('Nid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', nidPageUrlPattern);
            });
            it('last delete button click should delete instance of Nid', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('nid').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', nidPageUrlPattern);
                nid = undefined;
            });
        });
    });
    describe('new Nid page', function () {
        beforeEach(function () {
            cy.visit("".concat(nidPageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('Nid');
        });
        it('should create an instance of Nid', function () {
            cy.get("[data-cy=\"nid\"]").type('83876').should('have.value', '83876');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                nid = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', nidPageUrlPattern);
        });
    });
});
