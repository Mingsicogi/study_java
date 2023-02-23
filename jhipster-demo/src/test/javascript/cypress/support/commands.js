"use strict";
/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable @typescript-eslint/no-use-before-define */
// eslint-disable-next-line spaced-comment
/// <reference types="cypress" />
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
exports.__esModule = true;
exports.classValid = exports.classInvalid = exports.configurationPageHeadingSelector = exports.logsPageHeadingSelector = exports.healthPageHeadingSelector = exports.metricsPageHeadingSelector = exports.swaggerPageSelector = exports.swaggerFrameSelector = exports.userManagementPageHeadingSelector = exports.submitInitResetPasswordSelector = exports.emailResetPasswordSelector = exports.submitPasswordSelector = exports.confirmPasswordSelector = exports.newPasswordSelector = exports.currentPasswordSelector = exports.submitSettingsSelector = exports.languageSettingsSelector = exports.emailSettingsSelector = exports.lastNameSettingsSelector = exports.firstNameSettingsSelector = exports.submitRegisterSelector = exports.secondPasswordRegisterSelector = exports.firstPasswordRegisterSelector = exports.emailRegisterSelector = exports.usernameRegisterSelector = exports.titleRegisterSelector = exports.submitLoginSelector = exports.forgetYourPasswordSelector = exports.passwordLoginSelector = exports.usernameLoginSelector = exports.errorLoginSelector = exports.titleLoginSelector = exports.entityItemSelector = exports.logoutItemSelector = exports.loginItemSelector = exports.passwordItemSelector = exports.settingsItemSelector = exports.registerItemSelector = exports.accountMenuSelector = exports.adminMenuSelector = exports.navbarSelector = void 0;
// ***********************************************
// This commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
// ***********************************************
// Begin Specific Selector Attributes for Cypress
// ***********************************************
// Navbar
exports.navbarSelector = '[data-cy="navbar"]';
exports.adminMenuSelector = '[data-cy="adminMenu"]';
exports.accountMenuSelector = '[data-cy="accountMenu"]';
exports.registerItemSelector = '[data-cy="register"]';
exports.settingsItemSelector = '[data-cy="settings"]';
exports.passwordItemSelector = '[data-cy="passwordItem"]';
exports.loginItemSelector = '[data-cy="login"]';
exports.logoutItemSelector = '[data-cy="logout"]';
exports.entityItemSelector = '[data-cy="entity"]';
// Login
exports.titleLoginSelector = '[data-cy="loginTitle"]';
exports.errorLoginSelector = '[data-cy="loginError"]';
exports.usernameLoginSelector = '[data-cy="username"]';
exports.passwordLoginSelector = '[data-cy="password"]';
exports.forgetYourPasswordSelector = '[data-cy="forgetYourPasswordSelector"]';
exports.submitLoginSelector = '[data-cy="submit"]';
// Register
exports.titleRegisterSelector = '[data-cy="registerTitle"]';
exports.usernameRegisterSelector = '[data-cy="username"]';
exports.emailRegisterSelector = '[data-cy="email"]';
exports.firstPasswordRegisterSelector = '[data-cy="firstPassword"]';
exports.secondPasswordRegisterSelector = '[data-cy="secondPassword"]';
exports.submitRegisterSelector = '[data-cy="submit"]';
// Settings
exports.firstNameSettingsSelector = '[data-cy="firstname"]';
exports.lastNameSettingsSelector = '[data-cy="lastname"]';
exports.emailSettingsSelector = '[data-cy="email"]';
exports.languageSettingsSelector = '[data-cy="langKey"]';
exports.submitSettingsSelector = '[data-cy="submit"]';
// Password
exports.currentPasswordSelector = '[data-cy="currentPassword"]';
exports.newPasswordSelector = '[data-cy="newPassword"]';
exports.confirmPasswordSelector = '[data-cy="confirmPassword"]';
exports.submitPasswordSelector = '[data-cy="submit"]';
// Reset Password
exports.emailResetPasswordSelector = '[data-cy="emailResetPassword"]';
exports.submitInitResetPasswordSelector = '[data-cy="submit"]';
// Administration
exports.userManagementPageHeadingSelector = '[data-cy="userManagementPageHeading"]';
exports.swaggerFrameSelector = 'iframe[data-cy="swagger-frame"]';
exports.swaggerPageSelector = '[id="swagger-ui"]';
exports.metricsPageHeadingSelector = '[data-cy="metricsPageHeading"]';
exports.healthPageHeadingSelector = '[data-cy="healthPageHeading"]';
exports.logsPageHeadingSelector = '[data-cy="logsPageHeading"]';
exports.configurationPageHeadingSelector = '[data-cy="configurationPageHeading"]';
// ***********************************************
// End Specific Selector Attributes for Cypress
// ***********************************************
exports.classInvalid = 'invalid';
exports.classValid = 'valid';
Cypress.Commands.add('authenticatedRequest', function (data) {
    var bearerToken = sessionStorage.getItem(Cypress.env('jwtStorageName'));
    return cy.request(__assign(__assign({}, data), { auth: {
            bearer: bearerToken
        } }));
});
Cypress.Commands.add('login', function (username, password) {
    cy.session([username, password], function () {
        cy.request({
            method: 'GET',
            url: '/api/account',
            failOnStatusCode: false
        });
        cy.authenticatedRequest({
            method: 'POST',
            body: { username: username, password: password },
            url: Cypress.env('authenticationUrl')
        }).then(function (_a) {
            var id_token = _a.body.id_token;
            sessionStorage.setItem(Cypress.env('jwtStorageName'), id_token);
        });
    }, {
        validate: function () {
            cy.authenticatedRequest({ url: '/api/account' }).its('status').should('eq', 200);
        }
    });
});
require("cypress-audit/commands");
