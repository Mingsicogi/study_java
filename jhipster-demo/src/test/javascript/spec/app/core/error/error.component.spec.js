"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var test_utils_1 = require("@vue/test-utils");
var error_vue_1 = require("@/core/error/error.vue");
var config = require("@/shared/config/config");
var router_1 = require("@/router");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
var customErrorMsg = 'An error occurred.';
describe('Error component', function () {
    var error;
    var wrapper;
    var loginService;
    beforeEach(function () {
        loginService = { openLogin: jest.fn() };
        wrapper = (0, test_utils_1.shallowMount)(error_vue_1.default, {
            i18n: i18n,
            store: store,
            router: router_1.default,
            localVue: localVue,
            provide: {
                loginService: function () { return loginService; },
            },
        });
        error = wrapper.vm;
    });
    it('should have retrieve custom error on routing', function () {
        error.beforeRouteEnter({ meta: { errorMessage: customErrorMsg } }, null, function (cb) { return cb(error); });
        expect(error.errorMessage).toBe(customErrorMsg);
        expect(error.error403).toBeFalsy();
        expect(error.error404).toBeFalsy();
        expect(loginService.openLogin).toHaveBeenCalledTimes(0);
    });
    it('should have set forbidden error on routing', function () {
        error.beforeRouteEnter({ meta: { error403: true } }, null, function (cb) { return cb(error); });
        expect(error.errorMessage).toBeNull();
        expect(error.error403).toBeTruthy();
        expect(error.error404).toBeFalsy();
        expect(loginService.openLogin).toHaveBeenCalled();
    });
    it('should have set not found error on routing', function () {
        error.beforeRouteEnter({ meta: { error404: true } }, null, function (cb) { return cb(error); });
        expect(error.errorMessage).toBeNull();
        expect(error.error403).toBeFalsy();
        expect(error.error404).toBeTruthy();
        expect(loginService.openLogin).toHaveBeenCalledTimes(0);
    });
    it('should have retrieve custom error on init', function () {
        error.init(customErrorMsg, false, false);
        expect(error.errorMessage).toBe(customErrorMsg);
        expect(error.error403).toBeFalsy();
        expect(error.error404).toBeFalsy();
        expect(loginService.openLogin).toHaveBeenCalledTimes(0);
    });
    it('should have set forbidden error on init', function () {
        error.init(null, true, false);
        expect(error.errorMessage).toBeNull();
        expect(error.error403).toBeTruthy();
        expect(error.error404).toBeFalsy();
        expect(loginService.openLogin).toHaveBeenCalled();
    });
    it('should have set not found error on init', function () {
        error.init(null, false, true);
        expect(error.errorMessage).toBeNull();
        expect(error.error403).toBeFalsy();
        expect(error.error404).toBeTruthy();
        expect(loginService.openLogin).toHaveBeenCalledTimes(0);
    });
    it('should have set default on init', function () {
        error.init();
        expect(error.errorMessage).toBeNull();
        expect(error.error403).toBeFalsy();
        expect(error.error404).toBeFalsy();
        expect(loginService.openLogin).toHaveBeenCalledTimes(0);
    });
});
