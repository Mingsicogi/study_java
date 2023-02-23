"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var test_utils_1 = require("@vue/test-utils");
var config = require("@/shared/config/config");
var health_modal_vue_1 = require("@/admin/health/health-modal.vue");
var localVue = (0, test_utils_1.createLocalVue)();
config.initVueApp(localVue);
var i18n = config.initI18N(localVue);
var store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
var healthsService = { getBaseName: jest.fn(), getSubSystemName: jest.fn() };
describe('Health Modal Component', function () {
    var wrapper;
    var healthModal;
    beforeEach(function () {
        wrapper = (0, test_utils_1.shallowMount)(health_modal_vue_1.default, {
            propsData: {
                currentHealth: {},
            },
            i18n: i18n,
            localVue: localVue,
            provide: {
                healthService: function () { return healthsService; },
            },
        });
        healthModal = wrapper.vm;
    });
    describe('baseName and subSystemName', function () {
        it('should use healthService', function () {
            healthModal.baseName('base');
            expect(healthsService.getBaseName).toHaveBeenCalled();
        });
        it('should use healthService', function () {
            healthModal.subSystemName('base');
            expect(healthsService.getSubSystemName).toHaveBeenCalled();
        });
    });
    describe('readableValue should transform data', function () {
        it('to string when is an object', function () {
            var result = healthModal.readableValue({ data: 1000 });
            expect(result).toBe('{"data":1000}');
        });
        it('to string when is a string', function () {
            var result = healthModal.readableValue('data');
            expect(result).toBe('data');
        });
    });
});
describe('Health Modal Component for diskSpace', function () {
    var wrapper;
    var healthModal;
    beforeEach(function () {
        wrapper = (0, test_utils_1.shallowMount)(health_modal_vue_1.default, {
            propsData: {
                currentHealth: { name: 'diskSpace' },
            },
            i18n: i18n,
            localVue: localVue,
            provide: {
                healthService: function () { return healthsService; },
            },
        });
        healthModal = wrapper.vm;
    });
    describe('readableValue should transform data', function () {
        it('to GB when needed', function () {
            var result = healthModal.readableValue(2147483648);
            expect(result).toBe('2.00 GB');
        });
        it('to MB when needed', function () {
            var result = healthModal.readableValue(214748);
            expect(result).toBe('0.20 MB');
        });
    });
});
