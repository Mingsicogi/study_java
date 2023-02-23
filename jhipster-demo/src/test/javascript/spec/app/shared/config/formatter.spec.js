"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var formatter_1 = require("@/shared/config/formatter");
describe('Formatter i18n', function () {
    var formatter;
    beforeEach(function () {
        formatter = new formatter_1.default();
    });
    it('should interpolate properly given key', function () {
        var result = formatter.interpolate('{{key1}}', { key1: 'val1' });
        expect(result[0]).toBe('val1');
    });
});
