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
var axios_1 = require("axios");
var sinon_1 = require("sinon");
var account_service_1 = require("@/account/account.service");
var vue_router_1 = require("vue-router");
var translation_service_1 = require("@/locale/translation.service");
var config = require("@/shared/config/config");
var login_form_vue_1 = require("@/account/login-form/login-form.vue");
var localVue = (0, test_utils_1.createLocalVue)();
localVue.component('b-alert', {});
localVue.component('b-button', {});
localVue.component('b-form', {});
localVue.component('b-form-input', {});
localVue.component('b-form-group', {});
localVue.component('b-form-checkbox', {});
localVue.component('b-link', {});
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
    post: sinon_1.default.stub(axios_1.default, 'post'),
};
describe('LoginForm Component', function () {
    var wrapper;
    var loginForm;
    beforeEach(function () {
        axiosStub.get.resolves({});
        axiosStub.post.reset();
        wrapper = (0, test_utils_1.shallowMount)(login_form_vue_1.default, {
            store: store,
            i18n: i18n,
            localVue: localVue,
            provide: {
                accountService: function () { return new account_service_1.default(store, new translation_service_1.default(store, i18n), new vue_router_1.default()); },
            },
        });
        loginForm = wrapper.vm;
    });
    it('should not store token if authentication is KO', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    // GIVEN
                    loginForm.login = 'login';
                    loginForm.password = 'pwd';
                    loginForm.rememberMe = true;
                    axiosStub.post.rejects();
                    // WHEN
                    loginForm.doLogin();
                    return [4 /*yield*/, loginForm.$nextTick()];
                case 1:
                    _a.sent();
                    // THEN
                    expect(axiosStub.post.calledWith('api/authenticate', {
                        username: 'login',
                        password: 'pwd',
                        rememberMe: true,
                    })).toBeTruthy();
                    return [4 /*yield*/, loginForm.$nextTick()];
                case 2:
                    _a.sent();
                    expect(loginForm.authenticationError).toBeTruthy();
                    return [2 /*return*/];
            }
        });
    }); });
    it('should store token if authentication is OK', function () { return __awaiter(void 0, void 0, void 0, function () {
        var jwtSecret;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    // GIVEN
                    loginForm.login = 'login';
                    loginForm.password = 'pwd';
                    loginForm.rememberMe = true;
                    jwtSecret = 'jwt-secret';
                    axiosStub.post.resolves({ headers: { authorization: 'Bearer ' + jwtSecret } });
                    // WHEN
                    loginForm.doLogin();
                    return [4 /*yield*/, loginForm.$nextTick()];
                case 1:
                    _a.sent();
                    // THEN
                    expect(axiosStub.post.calledWith('api/authenticate', {
                        username: 'login',
                        password: 'pwd',
                        rememberMe: true,
                    })).toBeTruthy();
                    expect(loginForm.authenticationError).toBeFalsy();
                    expect(localStorage.getItem('jhi-authenticationToken')).toEqual(jwtSecret);
                    return [2 /*return*/];
            }
        });
    }); });
    it('should store token if authentication is OK in session', function () { return __awaiter(void 0, void 0, void 0, function () {
        var jwtSecret;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    // GIVEN
                    loginForm.login = 'login';
                    loginForm.password = 'pwd';
                    loginForm.rememberMe = false;
                    jwtSecret = 'jwt-secret';
                    axiosStub.post.resolves({ headers: { authorization: 'Bearer ' + jwtSecret } });
                    // WHEN
                    loginForm.doLogin();
                    return [4 /*yield*/, loginForm.$nextTick()];
                case 1:
                    _a.sent();
                    // THEN
                    expect(axiosStub.post.calledWith('api/authenticate', {
                        username: 'login',
                        password: 'pwd',
                        rememberMe: false,
                    })).toBeTruthy();
                    expect(loginForm.authenticationError).toBeFalsy();
                    expect(sessionStorage.getItem('jhi-authenticationToken')).toEqual(jwtSecret);
                    return [2 /*return*/];
            }
        });
    }); });
});
