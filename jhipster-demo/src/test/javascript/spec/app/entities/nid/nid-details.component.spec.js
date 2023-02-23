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
/* tslint:disable max-line-length */
var test_utils_1 = require("@vue/test-utils");
var sinon_1 = require("sinon");
var vue_router_1 = require("vue-router");
var config = require("@/shared/config/config");
var nid_details_vue_1 = require("@/entities/nid/nid-details.vue");
var nid_service_1 = require("@/entities/nid/nid.service");
var router_1 = require("@/router");
var alert_service_1 = require("@/shared/alert/alert.service");
var localVue = (0, test_utils_1.createLocalVue)();
localVue.use(vue_router_1.default);
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});
describe('Component Tests', function () {
    describe('Nid Management Detail Component', function () {
        var wrapper;
        var comp;
        var nidServiceStub;
        beforeEach(function () {
            nidServiceStub = sinon_1.default.createStubInstance(nid_service_1.default);
            wrapper = (0, test_utils_1.shallowMount)(nid_details_vue_1.default, {
                store: store,
                i18n: i18n,
                localVue: localVue,
                router: router_1.default,
                provide: { nidService: function () { return nidServiceStub; }, alertService: function () { return new alert_service_1.default(); } },
            });
            comp = wrapper.vm;
        });
        describe('OnInit', function () {
            it('Should call load all on init', function () { return __awaiter(void 0, void 0, void 0, function () {
                var foundNid;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            foundNid = { id: 123 };
                            nidServiceStub.find.resolves(foundNid);
                            // WHEN
                            comp.retrieveNid(123);
                            return [4 /*yield*/, comp.$nextTick()];
                        case 1:
                            _a.sent();
                            // THEN
                            expect(comp.nid).toBe(foundNid);
                            return [2 /*return*/];
                    }
                });
            }); });
        });
        describe('Before route enter', function () {
            it('Should retrieve data', function () { return __awaiter(void 0, void 0, void 0, function () {
                var foundNid;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            foundNid = { id: 123 };
                            nidServiceStub.find.resolves(foundNid);
                            // WHEN
                            comp.beforeRouteEnter({ params: { nidId: 123 } }, null, function (cb) { return cb(comp); });
                            return [4 /*yield*/, comp.$nextTick()];
                        case 1:
                            _a.sent();
                            // THEN
                            expect(comp.nid).toBe(foundNid);
                            return [2 /*return*/];
                    }
                });
            }); });
        });
        describe('Previous state', function () {
            it('Should go previous state', function () { return __awaiter(void 0, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            comp.previousState();
                            return [4 /*yield*/, comp.$nextTick()];
                        case 1:
                            _a.sent();
                            expect(comp.$router.currentRoute.fullPath).toContain('/');
                            return [2 /*return*/];
                    }
                });
            }); });
        });
    });
});
