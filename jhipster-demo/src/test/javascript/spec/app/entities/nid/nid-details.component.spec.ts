/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import NidDetailComponent from '@/entities/nid/nid-details.vue';
import NidClass from '@/entities/nid/nid-details.component';
import NidService from '@/entities/nid/nid.service';
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
  describe('Nid Management Detail Component', () => {
    let wrapper: Wrapper<NidClass>;
    let comp: NidClass;
    let nidServiceStub: SinonStubbedInstance<NidService>;

    beforeEach(() => {
      nidServiceStub = sinon.createStubInstance<NidService>(NidService);

      wrapper = shallowMount<NidClass>(NidDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { nidService: () => nidServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNid = { id: 123 };
        nidServiceStub.find.resolves(foundNid);

        // WHEN
        comp.retrieveNid(123);
        await comp.$nextTick();

        // THEN
        expect(comp.nid).toBe(foundNid);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNid = { id: 123 };
        nidServiceStub.find.resolves(foundNid);

        // WHEN
        comp.beforeRouteEnter({ params: { nidId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.nid).toBe(foundNid);
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
