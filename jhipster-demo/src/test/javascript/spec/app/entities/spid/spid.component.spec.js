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
var bootstrap_vue_1 = require("bootstrap-vue");
var config = require("@/shared/config/config");
var spid_vue_1 = require("@/entities/spid/spid.vue");
var spid_service_1 = require("@/entities/spid/spid.service");
var alert_service_1 = require("@/shared/alert/alert.service");
var localVue = (0, test_utils_1.createLocalVue)();
localVue.use(bootstrap_vue_1.ToastPlugin);
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});
var bModalStub = {
    render: function () { },
    methods: {
        hide: function () { },
        show: function () { },
    },
};
describe('Component Tests', function () {
    describe('Spid Management Component', function () {
        var wrapper;
        var comp;
        var spidServiceStub;
        beforeEach(function () {
            spidServiceStub = sinon_1.default.createStubInstance(spid_service_1.default);
            spidServiceStub.retrieve.resolves({ headers: {} });
            wrapper = (0, test_utils_1.shallowMount)(spid_vue_1.default, {
                store: store,
                i18n: i18n,
                localVue: localVue,
                stubs: { bModal: bModalStub },
                provide: {
                    spidService: function () { return spidServiceStub; },
                    alertService: function () { return new alert_service_1.default(); },
                },
            });
            comp = wrapper.vm;
        });
        it('Should call load all on init', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        spidServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
                        // WHEN
                        comp.retrieveAllSpids();
                        return [4 /*yield*/, comp.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(spidServiceStub.retrieve.called).toBeTruthy();
                        expect(comp.spids[0]).toEqual(expect.objectContaining({ id: 123 }));
                        return [2 /*return*/];
                }
            });
        }); });
        it('Should call delete service on confirmDelete', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        spidServiceStub.delete.resolves({});
                        // WHEN
                        comp.prepareRemove({ id: 123 });
                        expect(spidServiceStub.retrieve.callCount).toEqual(1);
                        comp.removeSpid();
                        return [4 /*yield*/, comp.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(spidServiceStub.delete.called).toBeTruthy();
                        expect(spidServiceStub.retrieve.callCount).toEqual(2);
                        return [2 /*return*/];
                }
            });
        }); });
    });
});
