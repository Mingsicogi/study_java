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

describe('Pid e2e test', () => {
  const pidPageUrl = '/pid';
  const pidPageUrlPattern = new RegExp('/pid(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const pidSample = {};

  let pid: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/pids+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/pids').as('postEntityRequest');
    cy.intercept('DELETE', '/api/pids/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (pid) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/pids/${pid.id}`,
      }).then(() => {
        pid = undefined;
      });
    }
  });

  it('Pids menu should load Pids page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('pid');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Pid').should('exist');
    cy.url().should('match', pidPageUrlPattern);
  });

  describe('Pid page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(pidPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Pid page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/pid/new$'));
        cy.getEntityCreateUpdateHeading('Pid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pidPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/pids',
          body: pidSample,
        }).then(({ body }) => {
          pid = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/pids+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [pid],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(pidPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Pid page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('pid');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pidPageUrlPattern);
      });

      it('edit button click should load edit Pid page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Pid');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pidPageUrlPattern);
      });

      it('last delete button click should delete instance of Pid', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('pid').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', pidPageUrlPattern);

        pid = undefined;
      });
    });
  });

  describe('new Pid page', () => {
    beforeEach(() => {
      cy.visit(`${pidPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Pid');
    });

    it('should create an instance of Pid', () => {
      cy.get(`[data-cy="pid"]`).type('57469').should('have.value', '57469');

      cy.get(`[data-cy="phone"]`).type('931-613-0126 x1991').should('have.value', '931-613-0126 x1991');

      cy.get(`[data-cy="ci"]`).type('navigate Fantastic').should('have.value', 'navigate Fantastic');

      cy.get(`[data-cy="di"]`).type('green').should('have.value', 'green');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        pid = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', pidPageUrlPattern);
    });
  });
});
