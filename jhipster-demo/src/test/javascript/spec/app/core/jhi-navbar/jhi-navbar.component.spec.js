"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
Object.defineProperty(exports, "__esModule", { value: true });
var test_utils_1 = require("@vue/test-utils");
var jhi_navbar_vue_1 = require("@/core/jhi-navbar/jhi-navbar.vue");
var config = require("@/shared/config/config");
var router_1 = require("@/router");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-navbar', {});
localVue.component('b-navbar-nav', {});
localVue.component('b-dropdown-item', {});
localVue.component('b-collapse', {});
localVue.component('b-nav-item', {});
localVue.component('b-nav-item-dropdown', {});
localVue.component('b-navbar-toggle', {});
localVue.component('b-navbar-brand', {});
localVue.component('b-navbar-nav', {});
describe('JhiNavbar', function () {
    var jhiNavbar;
    var wrapper;
    var loginService = { openLogin: jest.fn() };
    var accountService = { hasAnyAuthorityAndCheckAuth: jest.fn().mockImplementation(function () { return Promise.resolve(true); }) };
    var translationService = { refreshTranslation: jest.fn() };
    beforeEach(function () {
        wrapper = (0, test_utils_1.shallowMount)(jhi_navbar_vue_1.default, {
            i18n: i18n,
            store: store,
            router: router_1.default,
            localVue: localVue,
            provide: {
                loginService: function () { return loginService; },
                translationService: function () { return translationService; },
                accountService: function () { return accountService; },
            },
        });
        jhiNavbar = wrapper.vm;
    });
    it('should refresh translations', function () {
        expect(translationService.refreshTranslation).toHaveBeenCalled();
    });
    it('should not have user data set', function () {
        expect(jhiNavbar.authenticated).toBeFalsy();
        expect(jhiNavbar.openAPIEnabled).toBeFalsy();
        expect(jhiNavbar.inProduction).toBeFalsy();
    });
    it('should have user data set after authentication', function () {
        store.commit('authenticated', { login: 'test' });
        expect(jhiNavbar.authenticated).toBeTruthy();
    });
    it('should have profile info set after info retrieved', function () {
        store.commit('setActiveProfiles', ['prod', 'api-docs']);
        expect(jhiNavbar.openAPIEnabled).toBeTruthy();
        expect(jhiNavbar.inProduction).toBeTruthy();
    });
    it('should use login service', function () {
        jhiNavbar.openLogin();
        expect(loginService.openLogin).toHaveBeenCalled();
    });
    it('should use account service', function () {
        jhiNavbar.hasAnyAuthority('auth');
        expect(accountService.hasAnyAuthorityAndCheckAuth).toHaveBeenCalled();
    });
    it('logout should clear credentials', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    store.commit('authenticated', { login: 'test' });
                    return [4 /*yield*/, jhiNavbar.logout()];
                case 1:
                    _a.sent();
                    expect(jhiNavbar.authenticated).toBeFalsy();
                    return [2 /*return*/];
            }
        });
    }); });
    it('should determine active route', function () {
        router_1.default.push('/toto');
        expect(jhiNavbar.subIsActive('/titi')).toBeFalsy();
        expect(jhiNavbar.subIsActive('/toto')).toBeTruthy();
        expect(jhiNavbar.subIsActive(['/toto', 'toto'])).toBeTruthy();
    });
    it('should call translationService when changing language', function () {
        jhiNavbar.changeLanguage('fr');
        expect(translationService.refreshTranslation).toHaveBeenCalled();
    });
    it('should check for correct language', function () {
        store.commit('currentLanguage', 'fr');
        expect(jhiNavbar.isActiveLanguage('en')).toBeFalsy();
        expect(jhiNavbar.isActiveLanguage('fr')).toBeTruthy();
    });
});
