/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GnidDetailComponent from '@/entities/gnid/gnid-details.vue';
import GnidClass from '@/entities/gnid/gnid-details.component';
import GnidService from '@/entities/gnid/gnid.service';
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
  describe('Gnid Management Detail Component', () => {
    let wrapper: Wrapper<GnidClass>;
    let comp: GnidClass;
    let gnidServiceStub: SinonStubbedInstance<GnidService>;

    beforeEach(() => {
      gnidServiceStub = sinon.createStubInstance<GnidService>(GnidService);

      wrapper = shallowMount<GnidClass>(GnidDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { gnidService: () => gnidServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGnid = { id: 123 };
        gnidServiceStub.find.resolves(foundGnid);

        // WHEN
        comp.retrieveGnid(123);
        await comp.$nextTick();

        // THEN
        expect(comp.gnid).toBe(foundGnid);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGnid = { id: 123 };
        gnidServiceStub.find.resolves(foundGnid);

        // WHEN
        comp.beforeRouteEnter({ params: { gnidId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.gnid).toBe(foundGnid);
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
