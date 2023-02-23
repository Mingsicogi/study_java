"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var data_utils_service_1 = require("@/shared/data/data-utils.service");
describe('Formatter i18n', function () {
    var dataUtilsService;
    beforeEach(function () {
        dataUtilsService = new data_utils_service_1.default();
    });
    it('should not abbreviate text shorter than 30 characters', function () {
        var result = dataUtilsService.abbreviate('JHipster JHipster');
        expect(result).toBe('JHipster JHipster');
    });
    it('should abbreviate text longer than 30 characters', function () {
        var result = dataUtilsService.abbreviate('JHipster JHipster JHipster JHipster JHipster');
        expect(result).toBe('JHipster JHipst...r JHipster');
    });
    it('should retrieve byteSize', function () {
        var result = dataUtilsService.byteSize('JHipster rocks!');
        expect(result).toBe('11.25 bytes');
    });
    it('should clear input entity', function () {
        var entity = { field: 'key', value: 'value' };
        dataUtilsService.clearInputImage(entity, null, 'field', 'value', 1);
        expect(entity.field).toBeNull();
        expect(entity.value).toBeNull();
    });
    it('should open file', function () {
        window.open = jest.fn().mockReturnValue({});
        var objectURL = 'blob:http://localhost:9000/xxx';
        URL.createObjectURL = jest.fn().mockImplementationOnce(function () {
            return objectURL;
        });
        dataUtilsService.openFile('text', 'data');
        expect(window.open).toBeCalledWith(objectURL);
    });
    it('should check text ends with suffix', function () {
        var result = dataUtilsService.endsWith('rocky', 'JHipster rocks!');
        expect(result).toBe(false);
    });
    it('should paddingSize to 0', function () {
        var result = dataUtilsService.paddingSize('toto');
        expect(result).toBe(0);
    });
    it('should paddingSize to 1', function () {
        var result = dataUtilsService.paddingSize('toto=');
        expect(result).toBe(1);
    });
    it('should paddingSize to 2', function () {
        var result = dataUtilsService.paddingSize('toto==');
        expect(result).toBe(2);
    });
    it('should parse links', function () {
        var result = dataUtilsService.parseLinks('<http://localhost/api/entities?' +
            'sort=date%2Cdesc&sort=id&page=1&size=12>; rel="next",<http://localhost/api' +
            '/entities?sort=date%2Cdesc&sort=id&page=2&size=12>; rel="last",<http://localhost' +
            '/api/entities?sort=date%2Cdesc&sort=id&page=0&size=12>; rel="first"');
        expect(result['last']).toBe(2);
    });
    it('should return empty JSON object for empty string', function () {
        var result = dataUtilsService.parseLinks('');
        expect(result).toStrictEqual({});
    });
    it('should return empty JSON object for text with no link header', function () {
        var result = dataUtilsService.parseLinks('JHipster rocks!');
        expect(result).toStrictEqual({});
    });
    it('should return empty JSON object for text without >;', function () {
        var result = dataUtilsService.parseLinks('<http://localhost/api/entities?' +
            'sort=date%2Cdesc&sort=id&page=1&size=12> rel="next",<http://localhost/api' +
            '/entities?sort=date%2Cdesc&sort=id&page=2&size=12> rel="last",<http://localhost' +
            '/api/entities?sort=date%2Cdesc&sort=id&page=0&size=12> rel="first"');
        expect(result).toStrictEqual({});
    });
    it('should return empty JSON object for text with no comma separated link header', function () {
        var result = dataUtilsService.parseLinks('<http://localhost/api/entities?' +
            'sort=id%2Cdesc&sort=id&page=1&size=12>; rel="next"<http://localhost/api' +
            '/entities?sort=id%2Cdesc&sort=id&page=2&size=12>; rel="last"<http://localhost' +
            '/api/entities?sort=id%2Cdesc&sort=id&page=0&size=12>; rel="first"');
        expect(result).toStrictEqual({});
    });
});
