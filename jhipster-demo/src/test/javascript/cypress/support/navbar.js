"use strict";
/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable @typescript-eslint/no-use-before-define */
exports.__esModule = true;
var commands_1 = require("./commands");
Cypress.Commands.add('clickOnLoginItem', function () {
    return cy.get(commands_1.navbarSelector).get(commands_1.accountMenuSelector).click().get(commands_1.loginItemSelector).click();
});
Cypress.Commands.add('clickOnLogoutItem', function () {
    return cy.get(commands_1.navbarSelector).get(commands_1.accountMenuSelector).click().get(commands_1.logoutItemSelector).click();
});
Cypress.Commands.add('clickOnRegisterItem', function () {
    return cy.get(commands_1.navbarSelector).get(commands_1.accountMenuSelector).click().get(commands_1.registerItemSelector).click();
});
Cypress.Commands.add('clickOnSettingsItem', function () {
    return cy.get(commands_1.navbarSelector).get(commands_1.accountMenuSelector).click().get(commands_1.settingsItemSelector).click();
});
Cypress.Commands.add('clickOnPasswordItem', function () {
    return cy.get(commands_1.navbarSelector).get(commands_1.accountMenuSelector).click().get(commands_1.passwordItemSelector).click();
});
Cypress.Commands.add('clickOnAdminMenuItem', function (item) {
    return cy.get(commands_1.navbarSelector).get(commands_1.adminMenuSelector).click().get(".dropdown-item[href=\"/admin/".concat(item, "\"]")).click();
});
Cypress.Commands.add('clickOnEntityMenuItem', function (entityName) {
    return cy.get(commands_1.navbarSelector).get(commands_1.entityItemSelector).click().get(".dropdown-item[href=\"/".concat(entityName, "\"]")).click();
});
