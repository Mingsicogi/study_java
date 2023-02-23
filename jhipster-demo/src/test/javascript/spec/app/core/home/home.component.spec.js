"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var test_utils_1 = require("@vue/test-utils");
var home_vue_1 = require("@/core/home/home.vue");
var config = require("@/shared/config/config");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var store = config.initVueXStore(localVue);
var i18n = config.initI18N(localVue);
localVue.component('router-link', {});
describe('Home', function () {
    var home;
    var wrapper;
    var loginService = { openLogin: jest.fn() };
    beforeEach(function () {
        wrapper = (0, test_utils_1.shallowMount)(home_vue_1.default, {
            i18n: i18n,
            store: store,
            localVue: localVue,
            provide: {
                loginService: function () { return loginService; },
            },
        });
        home = wrapper.vm;
    });
    it('should not have user data set', function () {
        expect(home.authenticated).toBeFalsy();
        expect(home.username).toBe('');
    });
    it('should have user data set after authentication', function () {
        store.commit('authenticated', { login: 'test' });
        expect(home.authenticated).toBeTruthy();
        expect(home.username).toBe('test');
    });
    it('should use login service', function () {
        home.openLogin();
        expect(loginService.openLogin).toHaveBeenCalled();
    });
});
