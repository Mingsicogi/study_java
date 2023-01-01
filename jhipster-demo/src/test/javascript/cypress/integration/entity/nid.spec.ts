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

describe('Nid e2e test', () => {
  const nidPageUrl = '/nid';
  const nidPageUrlPattern = new RegExp('/nid(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nidSample = {};

  let nid: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nids/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nids/${nid.id}`,
      }).then(() => {
        nid = undefined;
      });
    }
  });

  it('Nids menu should load Nids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nid');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Nid').should('exist');
    cy.url().should('match', nidPageUrlPattern);
  });

  describe('Nid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Nid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nid/new$'));
        cy.getEntityCreateUpdateHeading('Nid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nids',
          body: nidSample,
        }).then(({ body }) => {
          nid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [nid],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Nid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nidPageUrlPattern);
      });

      it('edit button click should load edit Nid page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nidPageUrlPattern);
      });

      it('last delete button click should delete instance of Nid', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('nid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nidPageUrlPattern);

        nid = undefined;
      });
    });
  });

  describe('new Nid page', () => {
    beforeEach(() => {
      cy.visit(`${nidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Nid');
    });

    it('should create an instance of Nid', () => {
      cy.get(`[data-cy="nid"]`).type('83876').should('have.value', '83876');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        nid = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', nidPageUrlPattern);
    });
  });
});
