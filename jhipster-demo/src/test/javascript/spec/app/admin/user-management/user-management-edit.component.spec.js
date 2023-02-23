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
var vue_fontawesome_1 = require("@fortawesome/vue-fontawesome");
var config = require("@/shared/config/config");
var user_management_edit_vue_1 = require("@/admin/user-management/user-management-edit.vue");
var user_management_service_1 = require("@/admin/user-management/user-management.service");
var vue_router_1 = require("vue-router");
var bootstrap_vue_1 = require("bootstrap-vue");
var alert_service_1 = require("@/shared/alert/alert.service");
var localVue = (0, test_utils_1.createLocalVue)();
localVue.use(vue_router_1.default);
localVue.use(bootstrap_vue_1.ToastPlugin);
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', vue_fontawesome_1.FontAwesomeIcon);
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
    post: sinon_1.default.stub(axios_1.default, 'post'),
    put: sinon_1.default.stub(axios_1.default, 'put'),
};
describe('UserManagementEdit Component', function () {
    var wrapper;
    var userManagementEdit;
    beforeEach(function () {
        var router = new vue_router_1.default();
        wrapper = (0, test_utils_1.shallowMount)(user_management_edit_vue_1.default, {
            store: store,
            router: router,
            i18n: i18n,
            localVue: localVue,
            provide: {
                userManagementService: function () { return new user_management_service_1.default(); },
                alertService: function () { return new alert_service_1.default(); },
            },
        });
        userManagementEdit = wrapper.vm;
    });
    describe('init', function () {
        it('Should load user', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        axiosStub.get.resolves({});
                        // WHEN
                        userManagementEdit.init(123);
                        return [4 /*yield*/, userManagementEdit.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.get.calledWith('api/admin/users/' + 123)).toBeTruthy();
                        return [2 /*return*/];
                }
            });
        }); });
    });
    describe('initAuthorities', function () {
        it('Should load authorities', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        axiosStub.get.resolves({});
                        // WHEN
                        userManagementEdit.initAuthorities();
                        return [4 /*yield*/, userManagementEdit.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.get.calledWith("api/authorities")).toBeTruthy();
                        return [2 /*return*/];
                }
            });
        }); });
    });
    describe('save', function () {
        it('Should call update service on save for existing user', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        axiosStub.put.resolves({
                            headers: {
                                'x-shopapp-alert': '',
                                'x-shopapp-params': '',
                            },
                        });
                        userManagementEdit.userAccount = { id: 123, authorities: [] };
                        // WHEN
                        userManagementEdit.save();
                        return [4 /*yield*/, userManagementEdit.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.put.calledWith('api/admin/users', { id: 123, authorities: [] })).toBeTruthy();
                        expect(userManagementEdit.isSaving).toEqual(false);
                        return [2 /*return*/];
                }
            });
        }); });
        it('Should call create service on save for new user', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        axiosStub.post.resolves({
                            headers: {
                                'x-shopapp-alert': '',
                                'x-shopapp-params': '',
                            },
                        });
                        userManagementEdit.userAccount = { authorities: [] };
                        // WHEN
                        userManagementEdit.save();
                        return [4 /*yield*/, userManagementEdit.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.post.calledWith('api/admin/users', { authorities: [] })).toBeTruthy();
                        expect(userManagementEdit.isSaving).toEqual(false);
                        return [2 /*return*/];
                }
            });
        }); });
    });
});
