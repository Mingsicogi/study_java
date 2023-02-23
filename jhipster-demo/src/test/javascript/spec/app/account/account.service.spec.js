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
var router_1 = require("@/router");
var axios_1 = require("axios");
var sinon_1 = require("sinon");
var account_service_1 = require("@/account/account.service");
var translation_service_1 = require("@/locale/translation.service");
var config = require("@/shared/config/config");
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
    post: sinon_1.default.stub(axios_1.default, 'post'),
};
var localVue = (0, test_utils_1.createLocalVue)();
var i18n;
var store;
describe('Account Service test suite', function () {
    var accountService;
    beforeEach(function () {
        axiosStub.get.reset();
        store = config.initVueXStore(localVue);
        i18n = config.initI18N(localVue);
    });
    it('should init service and do not retrieve account', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    axiosStub.get.resolves({ data: { 'display-ribbon-on-profiles': 'dev', activeProfiles: ['dev', 'test'] } });
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    expect(store.getters.logon).toBe(false);
                    expect(accountService.authenticated).toBe(false);
                    expect(store.getters.account).toBe(null);
                    expect(axiosStub.get.calledWith('management/info')).toBeTruthy();
                    expect(store.getters.activeProfiles[0]).toBe('dev');
                    expect(store.getters.activeProfiles[1]).toBe('test');
                    expect(store.getters.ribbonOnProfiles).toBe('dev');
                    return [2 /*return*/];
            }
        });
    }); });
    it('should init service and retrieve profiles if already logged in before but no account found', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    localStorage.setItem('jhi-authenticationToken', 'token');
                    axiosStub.get.resolves({});
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    expect(router_1.default.history.current.fullPath).toBe('/');
                    expect(store.getters.logon).toBe(false);
                    expect(accountService.authenticated).toBe(false);
                    expect(store.getters.account).toBe(null);
                    expect(axiosStub.get.calledWith('management/info')).toBeTruthy();
                    return [2 /*return*/];
            }
        });
    }); });
    it('should init service and retrieve profiles if already logged in before but exception occurred and should be logged out', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    localStorage.setItem('jhi-authenticationToken', 'token');
                    axiosStub.get.resolves({});
                    axiosStub.get.withArgs('api/account').rejects();
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    expect(router_1.default.history.current.fullPath).toBe('/');
                    expect(accountService.authenticated).toBe(false);
                    expect(store.getters.account).toBe(null);
                    expect(axiosStub.get.calledWith('management/info')).toBeTruthy();
                    return [2 /*return*/];
            }
        });
    }); });
    it('should init service and check for authority after retrieving account but getAccount failed', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    localStorage.setItem('jhi-authenticationToken', 'token');
                    axiosStub.get.rejects();
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    return [2 /*return*/, accountService.hasAnyAuthorityAndCheckAuth('USER').then(function (value) {
                            expect(value).toBe(false);
                        })];
            }
        });
    }); });
    it('should init service and check for authority after retrieving account', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    localStorage.setItem('jhi-authenticationToken', 'token');
                    axiosStub.get.resolves({ data: { authorities: ['USER'] } });
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    return [2 /*return*/, accountService.hasAnyAuthorityAndCheckAuth('USER').then(function (value) {
                            expect(value).toBe(true);
                        })];
            }
        });
    }); });
    it('should init service as not authentified and not return any authorities admin and not retrieve account', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    axiosStub.get.resolves({});
                    axiosStub.get.withArgs('api/account').rejects();
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    return [2 /*return*/, accountService.hasAnyAuthorityAndCheckAuth('ADMIN').then(function (value) {
                            expect(value).toBe(false);
                        })];
            }
        });
    }); });
    it('should init service as not authentified and return authority user', function () { return __awaiter(void 0, void 0, void 0, function () {
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    axiosStub.get.resolves({});
                    axiosStub.get.withArgs('api/account').rejects();
                    return [4 /*yield*/, new account_service_1.default(store, new translation_service_1.default(store, i18n), router_1.default)];
                case 1:
                    accountService = _a.sent();
                    return [2 /*return*/, accountService.hasAnyAuthorityAndCheckAuth('USER').then(function (value) {
                            expect(value).toBe(true);
                        })];
            }
        });
    }); });
});
