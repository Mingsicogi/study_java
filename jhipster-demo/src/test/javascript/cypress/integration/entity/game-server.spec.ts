import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('GameServer e2e test', () => {
  const gameServerPageUrl = '/game-server';
  const gameServerPageUrlPattern = new RegExp('/game-server(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const gameServerSample = {};

  let gameServer: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/game-servers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/game-servers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/game-servers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (gameServer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/game-servers/${gameServer.id}`,
      }).then(() => {
        gameServer = undefined;
      });
    }
  });

  it('GameServers menu should load GameServers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('game-server');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('GameServer').should('exist');
    cy.url().should('match', gameServerPageUrlPattern);
  });

  describe('GameServer page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gameServerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create GameServer page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/game-server/new$'));
        cy.getEntityCreateUpdateHeading('GameServer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gameServerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/game-servers',
          body: gameServerSample,
        }).then(({ body }) => {
          gameServer = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/game-servers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [gameServer],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(gameServerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details GameServer page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('gameServer');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gameServerPageUrlPattern);
      });

      it('edit button click should load edit GameServer page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GameServer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gameServerPageUrlPattern);
      });

      it('last delete button click should delete instance of GameServer', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('gameServer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gameServerPageUrlPattern);

        gameServer = undefined;
      });
    });
  });

  describe('new GameServer page', () => {
    beforeEach(() => {
      cy.visit(`${gameServerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('GameServer');
    });

    it('should create an instance of GameServer', () => {
      cy.get(`[data-cy="gameServerId"]`).type('Burundi Arab Avon').should('have.value', 'Burundi Arab Avon');

      cy.get(`[data-cy="region"]`).type('payment').should('have.value', 'payment');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        gameServer = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', gameServerPageUrlPattern);
    });
  });
});
