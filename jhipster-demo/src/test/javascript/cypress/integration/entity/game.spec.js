"use strict";
exports.__esModule = true;
var entity_1 = require("../../support/entity");
describe('Game e2e test', function () {
    var _a, _b;
    var gamePageUrl = '/game';
    var gamePageUrlPattern = new RegExp('/game(\\?.*)?$');
    var username = (_a = Cypress.env('E2E_USERNAME')) !== null && _a !== void 0 ? _a : 'user';
    var password = (_b = Cypress.env('E2E_PASSWORD')) !== null && _b !== void 0 ? _b : 'user';
    var gameSample = {};
    var game;
    beforeEach(function () {
        cy.login(username, password);
    });
    beforeEach(function () {
        cy.intercept('GET', '/api/games+(?*|)').as('entitiesRequest');
        cy.intercept('POST', '/api/games').as('postEntityRequest');
        cy.intercept('DELETE', '/api/games/*').as('deleteEntityRequest');
    });
    afterEach(function () {
        if (game) {
            cy.authenticatedRequest({
                method: 'DELETE',
                url: "/api/games/".concat(game.id)
            }).then(function () {
                game = undefined;
            });
        }
    });
    it('Games menu should load Games page', function () {
        cy.visit('/');
        cy.clickOnEntityMenuItem('game');
        cy.wait('@entitiesRequest').then(function (_a) {
            var response = _a.response;
            if (response.body.length === 0) {
                cy.get(entity_1.entityTableSelector).should('not.exist');
            }
            else {
                cy.get(entity_1.entityTableSelector).should('exist');
            }
        });
        cy.getEntityHeading('Game').should('exist');
        cy.url().should('match', gamePageUrlPattern);
    });
    describe('Game page', function () {
        describe('create button click', function () {
            beforeEach(function () {
                cy.visit(gamePageUrl);
                cy.wait('@entitiesRequest');
            });
            it('should load create Game page', function () {
                cy.get(entity_1.entityCreateButtonSelector).click();
                cy.url().should('match', new RegExp('/game/new$'));
                cy.getEntityCreateUpdateHeading('Game');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gamePageUrlPattern);
            });
        });
        describe('with existing value', function () {
            beforeEach(function () {
                cy.authenticatedRequest({
                    method: 'POST',
                    url: '/api/games',
                    body: gameSample
                }).then(function (_a) {
                    var body = _a.body;
                    game = body;
                    cy.intercept({
                        method: 'GET',
                        url: '/api/games+(?*|)',
                        times: 1
                    }, {
                        statusCode: 200,
                        body: [game]
                    }).as('entitiesRequestInternal');
                });
                cy.visit(gamePageUrl);
                cy.wait('@entitiesRequestInternal');
            });
            it('detail button click should load details Game page', function () {
                cy.get(entity_1.entityDetailsButtonSelector).first().click();
                cy.getEntityDetailsHeading('game');
                cy.get(entity_1.entityDetailsBackButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gamePageUrlPattern);
            });
            it('edit button click should load edit Game page', function () {
                cy.get(entity_1.entityEditButtonSelector).first().click();
                cy.getEntityCreateUpdateHeading('Game');
                cy.get(entity_1.entityCreateSaveButtonSelector).should('exist');
                cy.get(entity_1.entityCreateCancelButtonSelector).click();
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gamePageUrlPattern);
            });
            it('last delete button click should delete instance of Game', function () {
                cy.get(entity_1.entityDeleteButtonSelector).last().click();
                cy.getEntityDeleteDialogHeading('game').should('exist');
                cy.get(entity_1.entityConfirmDeleteButtonSelector).click();
                cy.wait('@deleteEntityRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(204);
                });
                cy.wait('@entitiesRequest').then(function (_a) {
                    var response = _a.response;
                    expect(response.statusCode).to.equal(200);
                });
                cy.url().should('match', gamePageUrlPattern);
                game = undefined;
            });
        });
    });
    describe('new Game page', function () {
        beforeEach(function () {
            cy.visit("".concat(gamePageUrl));
            cy.get(entity_1.entityCreateButtonSelector).click();
            cy.getEntityCreateUpdateHeading('Game');
        });
        it('should create an instance of Game', function () {
            cy.get("[data-cy=\"gameId\"]").type('4625').should('have.value', '4625');
            cy.get("[data-cy=\"gameNm\"]").type('Account Ukraine').should('have.value', 'Account Ukraine');
            cy.get("[data-cy=\"gameCd\"]").type('morph multi-byte').should('have.value', 'morph multi-byte');
            cy.get(entity_1.entityCreateSaveButtonSelector).click();
            cy.wait('@postEntityRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(201);
                game = response.body;
            });
            cy.wait('@entitiesRequest').then(function (_a) {
                var response = _a.response;
                expect(response.statusCode).to.equal(200);
            });
            cy.url().should('match', gamePageUrlPattern);
        });
    });
});
