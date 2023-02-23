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
var logs_vue_1 = require("@/admin/logs/logs.vue");
var logs_service_1 = require("@/admin/logs/logs.service");
var config = require("@/shared/config/config");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
    post: sinon_1.default.stub(axios_1.default, 'post'),
};
describe('Logs Component', function () {
    var wrapper;
    var logs;
    beforeEach(function () {
        axiosStub.get.resolves({});
        wrapper = (0, test_utils_1.shallowMount)(logs_vue_1.default, { store: store, i18n: i18n, localVue: localVue, provide: { logsService: function () { return new logs_service_1.default(); } } });
        logs = wrapper.vm;
    });
    describe('OnInit', function () {
        it('should set all default values correctly', function () {
            expect(logs.filtered).toBe('');
            expect(logs.orderProp).toBe('name');
            expect(logs.reverse).toBe(false);
        });
        it('Should call load all on init', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // WHEN
                        logs.init();
                        return [4 /*yield*/, logs.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.get.calledWith('management/loggers')).toBeTruthy();
                        return [2 /*return*/];
                }
            });
        }); });
    });
    describe('change log level', function () {
        it('should change log level correctly', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        axiosStub.post.resolves({});
                        // WHEN
                        logs.updateLevel('main', 'ERROR');
                        return [4 /*yield*/, logs.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.post.calledWith('management/loggers/main', { configuredLevel: 'ERROR' })).toBeTruthy();
                        expect(axiosStub.get.calledWith('management/loggers')).toBeTruthy();
                        return [2 /*return*/];
                }
            });
        }); });
    });
    describe('change order', function () {
        it('should change order and invert reverse', function () {
            // WHEN
            logs.changeOrder('dummy-order');
            // THEN
            expect(logs.orderProp).toEqual('dummy-order');
            expect(logs.reverse).toBe(true);
        });
    });
});
