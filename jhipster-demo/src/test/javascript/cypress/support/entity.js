"use strict";
/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable @typescript-eslint/no-use-before-define */
// eslint-disable-next-line spaced-comment
/// <reference types="cypress" />
var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
exports.__esModule = true;
exports.entityConfirmDeleteButtonSelector = exports.entityDeleteButtonSelector = exports.entityEditButtonSelector = exports.entityDetailsBackButtonSelector = exports.entityDetailsButtonSelector = exports.entityCreateCancelButtonSelector = exports.entityCreateSaveButtonSelector = exports.entityCreateButtonSelector = exports.entityTableSelector = void 0;
// ***********************************************
// Begin Specific Selector Attributes for Cypress
// ***********************************************
// Entity
exports.entityTableSelector = '[data-cy="entityTable"]';
exports.entityCreateButtonSelector = '[data-cy="entityCreateButton"]';
exports.entityCreateSaveButtonSelector = '[data-cy="entityCreateSaveButton"]';
exports.entityCreateCancelButtonSelector = '[data-cy="entityCreateCancelButton"]';
exports.entityDetailsButtonSelector = '[data-cy="entityDetailsButton"]'; // can return multiple elements
exports.entityDetailsBackButtonSelector = '[data-cy="entityDetailsBackButton"]';
exports.entityEditButtonSelector = '[data-cy="entityEditButton"]';
exports.entityDeleteButtonSelector = '[data-cy="entityDeleteButton"]';
exports.entityConfirmDeleteButtonSelector = '[data-cy="entityConfirmDeleteButton"]';
// ***********************************************
// End Specific Selector Attributes for Cypress
// ***********************************************
Cypress.Commands.add('getEntityHeading', function (entityName) {
    return cy.get("[data-cy=\"".concat(entityName, "Heading\"]"));
});
Cypress.Commands.add('getEntityCreateUpdateHeading', function (entityName) {
    return cy.get("[data-cy=\"".concat(entityName, "CreateUpdateHeading\"]"));
});
Cypress.Commands.add('getEntityDetailsHeading', function (entityInstanceName) {
    return cy.get("[data-cy=\"".concat(entityInstanceName, "DetailsHeading\"]"));
});
Cypress.Commands.add('getEntityDeleteDialogHeading', function (entityInstanceName) {
    return cy.get("[data-cy=\"".concat(entityInstanceName, "DeleteDialogHeading\"]"));
});
Cypress.Commands.add('setFieldImageAsBytesOfEntity', function (fieldName, fileName, mimeType) {
    // fileName is the image which you have already put in cypress fixture folder
    // should be like : 'integration-test.png', 'image/png'
    cy.fixture(fileName)
        .as('image')
        .get("[data-cy=\"".concat(fieldName, "\"]"))
        .then(function (el) {
        var blob = Cypress.Blob.base64StringToBlob(this.image, mimeType);
        var file = new File([blob], fileName, { type: mimeType });
        var list = new DataTransfer();
        list.items.add(file);
        var myFileList = list.files;
        el[0].files = myFileList;
        el[0].dispatchEvent(new Event('change', { bubbles: true }));
    });
});
Cypress.Commands.add('setFieldSelectToLastOfEntity', function (fieldName) {
    return cy.get("[data-cy=\"".concat(fieldName, "\"]")).then(function (select) {
        var _a, _b;
        var selectSize = ((_b = (_a = select[0]) === null || _a === void 0 ? void 0 : _a.options) === null || _b === void 0 ? void 0 : _b.length) || Number(select.attr('size')) || 0;
        if (selectSize > 0) {
            return cy.get("[data-cy=\"".concat(fieldName, "\"] option")).then(function (options) {
                var elements = __spreadArray([], options, true).map(function (o) { return o.label; });
                var lastElement = elements.length - 1;
                cy.get("[data-cy=\"".concat(fieldName, "\"]")).select(lastElement).type('{downarrow}');
            });
        }
        else {
            return cy.get("[data-cy=\"".concat(fieldName, "\"]")).type('{downarrow}');
        }
    });
});
