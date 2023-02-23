"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('GameServer e2e test', function () {
    var _a, _b;
    var gameServerPageUrl = '/game-server';
    var gameServerPageUrlPattern = new RegExp('/game-server(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var gameServerSample = {};
    var gameServer;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/game-servers+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/game-servers').as('postEntityRequest');
        cy.intercept('DELETE', '/api/game-servers/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (gameServer) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/game-servers/".concat(gameServer.id)
            }).then(function () {
                gameServer = undefined;
            });
        }
    });
    it('GameServers menu should load GameServers page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('game-server');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('GameServer').should('exist');
        cy.url().should('match', gameServerPageUrlPattern);
    });
    describe('GameServer page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(gameServerPageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create GameServer page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/game-server/new$'));
                cy.getEntityCreateUpdateHeading('GameServer');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gameServerPageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/game-servers',
                    body: gameServerSample
                }).then(function (_a) {
                    var body = _a.body;
                    gameServer = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/game-servers+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [gameServer]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(gameServerPageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details GameServer page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('gameServer');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gameServerPageUrlPattern);
            });
            it('edit button click should load edit GameServer page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('GameServer');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gameServerPageUrlPattern);
            });
            it('last delete button click should delete instance of GameServer', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('gameServer').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gameServerPageUrlPattern);
                gameServer = undefined;
            });
        });
    });
    describe('new GameServer page', function () {
        beforeEach(function () {
            cy.visit("".concat(gameServerPageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('GameServer');
        });
        it('should create an instance of GameServer', function () {
            cy.get("[data-cy=\"gameServerId\"]").type('Burundi Arab Avon').should('have.value', 'Burundi Arab Avon');
            cy.get("[data-cy=\"region\"]").type('payment').should('have.value', 'payment');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                gameServer = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', gameServerPageUrlPattern);
        });
    });
});
