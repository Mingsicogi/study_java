"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('Gnid e2e test', function () {
    var _a, _b;
    var gnidPageUrl = '/gnid';
    var gnidPageUrlPattern = new RegExp('/gnid(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var gnidSample = {};
    var gnid;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/gnids+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/gnids').as('postEntityRequest');
        cy.intercept('DELETE', '/api/gnids/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (gnid) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/gnids/".concat(gnid.id)
            }).then(function () {
                gnid = undefined;
            });
        }
    });
    it('Gnids menu should load Gnids page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('gnid');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('Gnid').should('exist');
        cy.url().should('match', gnidPageUrlPattern);
    });
    describe('Gnid page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(gnidPageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create Gnid page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/gnid/new$'));
                cy.getEntityCreateUpdateHeading('Gnid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gnidPageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/gnids',
                    body: gnidSample
                }).then(function (_a) {
                    var body = _a.body;
                    gnid = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/gnids+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [gnid]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(gnidPageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details Gnid page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('gnid');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gnidPageUrlPattern);
            });
            it('edit button click should load edit Gnid page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('Gnid');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gnidPageUrlPattern);
            });
            it('last delete button click should delete instance of Gnid', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('gnid').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gnidPageUrlPattern);
                gnid = undefined;
            });
        });
    });
    describe('new Gnid page', function () {
        beforeEach(function () {
            cy.visit("".concat(gnidPageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('Gnid');
        });
        it('should create an instance of Gnid', function () {
            cy.get("[data-cy=\"gnid\"]").type('60161').should('have.value', '60161');
            cy.get("[data-cy=\"gameCd\"]").type('Wooden Group Ohio').should('have.value', 'Wooden Group Ohio');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                gnid = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', gnidPageUrlPattern);
        });
    });
});
