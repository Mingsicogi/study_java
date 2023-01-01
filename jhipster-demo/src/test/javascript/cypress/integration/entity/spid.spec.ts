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

describe('Spid e2e test', () => {
  const spidPageUrl = '/spid';
  const spidPageUrlPattern = new RegExp('/spid(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const spidSample = {};

  let spid: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/spids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/spids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/spids/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (spid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/spids/${spid.id}`,
      }).then(() => {
        spid = undefined;
      });
    }
  });

  it('Spids menu should load Spids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('spid');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Spid').should('exist');
    cy.url().should('match', spidPageUrlPattern);
  });

  describe('Spid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(spidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Spid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/spid/new$'));
        cy.getEntityCreateUpdateHeading('Spid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/spids',
          body: spidSample,
        }).then(({ body }) => {
          spid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/spids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [spid],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(spidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Spid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('spid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spidPageUrlPattern);
      });

      it('edit button click should load edit Spid page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Spid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spidPageUrlPattern);
      });

      it('last delete button click should delete instance of Spid', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('spid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', spidPageUrlPattern);

        spid = undefined;
      });
    });
  });

  describe('new Spid page', () => {
    beforeEach(() => {
      cy.visit(`${spidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Spid');
    });

    it('should create an instance of Spid', () => {
      cy.get(`[data-cy="spid"]`).type('66975').should('have.value', '66975');

      cy.get(`[data-cy="email"]`).type('Matt23@yahoo.com').should('have.value', 'Matt23@yahoo.com');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        spid = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', spidPageUrlPattern);
    });
  });
});
