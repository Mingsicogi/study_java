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
var metrics_vue_1 = require("@/admin/metrics/metrics.vue");
var metrics_modal_vue_1 = require("@/admin/metrics/metrics-modal.vue");
var metrics_service_1 = require("@/admin/metrics/metrics.service");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', vue_fontawesome_1.FontAwesomeIcon);
localVue.component('metrics-modal', metrics_modal_vue_1.default);
localVue.directive('b-modal', {});
localVue.directive('b-progress', {});
localVue.directive('b-progress-bar', {});
var axiosStub = {
    get: sinon_1.default.stub(axios_1.default, 'get'),
};
describe('Metrics Component', function () {
    var wrapper;
    var metricsComponent;
    var response = {
        jvm: {
            'PS Eden Space': {
                committed: 5.57842432e8,
                max: 6.49592832e8,
                used: 4.20828184e8,
            },
            'Code Cache': {
                committed: 2.3461888e7,
                max: 2.5165824e8,
                used: 2.2594368e7,
            },
            'Compressed Class Space': {
                committed: 1.2320768e7,
                max: 1.073741824e9,
                used: 1.1514008e7,
            },
            'PS Survivor Space': {
                committed: 1.5204352e7,
                max: 1.5204352e7,
                used: 1.2244376e7,
            },
            'PS Old Gen': {
                committed: 1.10624768e8,
                max: 1.37887744e9,
                used: 4.1390776e7,
            },
            Metaspace: {
                committed: 9.170944e7,
                max: -1.0,
                used: 8.7377552e7,
            },
        },
        databases: {
            min: {
                value: 10.0,
            },
            max: {
                value: 10.0,
            },
            idle: {
                value: 10.0,
            },
            usage: {
                '0.0': 0.0,
                '1.0': 0.0,
                max: 0.0,
                totalTime: 4210.0,
                mean: 701.6666666666666,
                '0.5': 0.0,
                count: 6,
                '0.99': 0.0,
                '0.75': 0.0,
                '0.95': 0.0,
            },
            pending: {
                value: 0.0,
            },
            active: {
                value: 0.0,
            },
            acquire: {
                '0.0': 0.0,
                '1.0': 0.0,
                max: 0.0,
                totalTime: 0.884426,
                mean: 0.14740433333333333,
                '0.5': 0.0,
                count: 6,
                '0.99': 0.0,
                '0.75': 0.0,
                '0.95': 0.0,
            },
            creation: {
                '0.0': 0.0,
                '1.0': 0.0,
                max: 0.0,
                totalTime: 27.0,
                mean: 3.0,
                '0.5': 0.0,
                count: 9,
                '0.99': 0.0,
                '0.75': 0.0,
                '0.95': 0.0,
            },
            connections: {
                value: 10.0,
            },
        },
        'http.server.requests': {
            all: {
                count: 5,
            },
            percode: {
                '200': {
                    max: 0.0,
                    mean: 298.9012628,
                    count: 5,
                },
            },
        },
        cache: {
            usersByEmail: {
                'cache.gets.miss': 0.0,
                'cache.puts': 0.0,
                'cache.gets.hit': 0.0,
                'cache.removals': 0.0,
                'cache.evictions': 0.0,
            },
            usersByLogin: {
                'cache.gets.miss': 1.0,
                'cache.puts': 1.0,
                'cache.gets.hit': 1.0,
                'cache.removals': 0.0,
                'cache.evictions': 0.0,
            },
            'tech.jhipster.domain.Authority': {
                'cache.gets.miss': 0.0,
                'cache.puts': 2.0,
                'cache.gets.hit': 0.0,
                'cache.removals': 0.0,
                'cache.evictions': 0.0,
            },
            'tech.jhipster.domain.User.authorities': {
                'cache.gets.miss': 0.0,
                'cache.puts': 1.0,
                'cache.gets.hit': 0.0,
                'cache.removals': 0.0,
                'cache.evictions': 0.0,
            },
            'tech.jhipster.domain.User': {
                'cache.gets.miss': 0.0,
                'cache.puts': 1.0,
                'cache.gets.hit': 0.0,
                'cache.removals': 0.0,
                'cache.evictions': 0.0,
            },
        },
        garbageCollector: {
            'jvm.gc.max.data.size': 1.37887744e9,
            'jvm.gc.pause': {
                '0.0': 0.0,
                '1.0': 0.0,
                max: 0.0,
                totalTime: 242.0,
                mean: 242.0,
                '0.5': 0.0,
                count: 1,
                '0.99': 0.0,
                '0.75': 0.0,
                '0.95': 0.0,
            },
            'jvm.gc.memory.promoted': 2.992732e7,
            'jvm.gc.memory.allocated': 1.26362872e9,
            classesLoaded: 17393.0,
            'jvm.gc.live.data.size': 3.1554408e7,
            classesUnloaded: 0.0,
        },
        services: {
            '/management/info': {
                GET: {
                    max: 0.0,
                    mean: 104.952893,
                    count: 1,
                },
            },
            '/api/authenticate': {
                POST: {
                    max: 0.0,
                    mean: 909.53003,
                    count: 1,
                },
            },
            '/api/account': {
                GET: {
                    max: 0.0,
                    mean: 141.209628,
                    count: 1,
                },
            },
            '/**': {
                GET: {
                    max: 0.0,
                    mean: 169.4068815,
                    count: 2,
                },
            },
        },
        processMetrics: {
            'system.load.average.1m': 3.63,
            'system.cpu.usage': 0.5724934148485453,
            'system.cpu.count': 4.0,
            'process.start.time': 1.548140811306e12,
            'process.files.open': 205.0,
            'process.cpu.usage': 0.003456347568026252,
            'process.uptime': 88404.0,
            'process.files.max': 1048576.0,
        },
        threads: [{ name: 'test1', threadState: 'RUNNABLE' }],
    };
    beforeEach(function () {
        axiosStub.get.resolves({ data: { timers: [], gauges: [] } });
        wrapper = (0, test_utils_1.shallowMount)(metrics_vue_1.default, {
            store: store,
            i18n: i18n,
            localVue: localVue,
            stubs: {
                bModal: true,
                bProgress: true,
                bProgressBar: true,
            },
            provide: {
                metricsService: function () { return new metrics_service_1.default(); },
            },
        });
        metricsComponent = wrapper.vm;
    });
    describe('refresh', function () {
        it('should call refresh on init', function () { return __awaiter(void 0, void 0, void 0, function () {
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        // GIVEN
                        axiosStub.get.resolves({ data: response });
                        // WHEN
                        metricsComponent.refresh();
                        return [4 /*yield*/, metricsComponent.$nextTick()];
                    case 1:
                        _a.sent();
                        // THEN
                        expect(axiosStub.get.calledWith('management/jhimetrics')).toBeTruthy();
                        expect(axiosStub.get.calledWith('management/threaddump')).toBeTruthy();
                        expect(metricsComponent.metrics).toHaveProperty('jvm');
                        expect(metricsComponent.metrics).toEqual(response);
                        expect(metricsComponent.threadStats).toEqual({
                            threadDumpRunnable: 1,
                            threadDumpWaiting: 0,
                            threadDumpTimedWaiting: 0,
                            threadDumpBlocked: 0,
                            threadDumpAll: 1,
                        });
                        return [2 /*return*/];
                }
            });
        }); });
    });
    describe('isNan', function () {
        it('should return if a variable is NaN', function () {
            expect(metricsComponent.filterNaN(1)).toBe(1);
            expect(metricsComponent.filterNaN('test')).toBe(0);
        });
    });
});
