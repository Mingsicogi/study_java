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

describe('Game e2e test', () => {
  const gamePageUrl = '/game';
  const gamePageUrlPattern = new RegExp('/game(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const gameSample = {};

  let game: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/games+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/games').as('postEntityRequest');
    cy.intercept('DELETE', '/api/games/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (game) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/games/${game.id}`,
      }).then(() => {
        game = undefined;
      });
    }
  });

  it('Games menu should load Games page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('game');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Game').should('exist');
    cy.url().should('match', gamePageUrlPattern);
  });

  describe('Game page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gamePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Game page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/game/new$'));
        cy.getEntityCreateUpdateHeading('Game');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gamePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/games',
          body: gameSample,
        }).then(({ body }) => {
          game = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/games+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [game],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(gamePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Game page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('game');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gamePageUrlPattern);
      });

      it('edit button click should load edit Game page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Game');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gamePageUrlPattern);
      });

      it('last delete button click should delete instance of Game', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('game').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gamePageUrlPattern);

        game = undefined;
      });
    });
  });

  describe('new Game page', () => {
    beforeEach(() => {
      cy.visit(`${gamePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Game');
    });

    it('should create an instance of Game', () => {
      cy.get(`[data-cy="gameId"]`).type('4625').should('have.value', '4625');

      cy.get(`[data-cy="gameNm"]`).type('Account Ukraine').should('have.value', 'Account Ukraine');

      cy.get(`[data-cy="gameCd"]`).type('morph multi-byte').should('have.value', 'morph multi-byte');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        game = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', gamePageUrlPattern);
    });
  });
});
