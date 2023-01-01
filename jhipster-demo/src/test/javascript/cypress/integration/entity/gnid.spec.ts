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

describe('Gnid e2e test', () => {
  const gnidPageUrl = '/gnid';
  const gnidPageUrlPattern = new RegExp('/gnid(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const gnidSample = {};

  let gnid: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/gnids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/gnids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/gnids/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (gnid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/gnids/${gnid.id}`,
      }).then(() => {
        gnid = undefined;
      });
    }
  });

  it('Gnids menu should load Gnids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('gnid');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Gnid').should('exist');
    cy.url().should('match', gnidPageUrlPattern);
  });

  describe('Gnid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gnidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Gnid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/gnid/new$'));
        cy.getEntityCreateUpdateHeading('Gnid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gnidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/gnids',
          body: gnidSample,
        }).then(({ body }) => {
          gnid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/gnids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [gnid],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(gnidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Gnid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('gnid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gnidPageUrlPattern);
      });

      it('edit button click should load edit Gnid page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Gnid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gnidPageUrlPattern);
      });

      it('last delete button click should delete instance of Gnid', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('gnid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', gnidPageUrlPattern);

        gnid = undefined;
      });
    });
  });

  describe('new Gnid page', () => {
    beforeEach(() => {
      cy.visit(`${gnidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Gnid');
    });

    it('should create an instance of Gnid', () => {
      cy.get(`[data-cy="gnid"]`).type('60161').should('have.value', '60161');

      cy.get(`[data-cy="gameCd"]`).type('Wooden Group Ohio').should('have.value', 'Wooden Group Ohio');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        gnid = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', gnidPageUrlPattern);
    });
  });
});
