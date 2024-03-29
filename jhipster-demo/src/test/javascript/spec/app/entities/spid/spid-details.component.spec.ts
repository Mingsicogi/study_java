/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SpidDetailComponent from '@/entities/spid/spid-details.vue';
import SpidClass from '@/entities/spid/spid-details.component';
import SpidService from '@/entities/spid/spid.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Spid Management Detail Component', () => {
    let wrapper: Wrapper<SpidClass>;
    let comp: SpidClass;
    let spidServiceStub: SinonStubbedInstance<SpidService>;

    beforeEach(() => {
      spidServiceStub = sinon.createStubInstance<SpidService>(SpidService);

      wrapper = shallowMount<SpidClass>(SpidDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { spidService: () => spidServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSpid = { id: 123 };
        spidServiceStub.find.resolves(foundSpid);

        // WHEN
        comp.retrieveSpid(123);
        await comp.$nextTick();

        // THEN
        expect(comp.spid).toBe(foundSpid);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSpid = { id: 123 };
        spidServiceStub.find.resolves(foundSpid);

        // WHEN
        comp.beforeRouteEnter({ params: { spidId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.spid).toBe(foundSpid);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
