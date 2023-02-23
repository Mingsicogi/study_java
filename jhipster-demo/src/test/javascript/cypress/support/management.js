"use strict";
/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable @typescript-eslint/no-use-before-define */
exports.__esModule = true;
Cypress.Commands.add('getManagementInfo', function () {
    return cy
        .request({
        method: 'GET',
        url: '/management/info'
    })
        .then(function (response) { return response.body; });
});
