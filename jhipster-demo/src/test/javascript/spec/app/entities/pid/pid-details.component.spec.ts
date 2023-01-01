/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PidDetailComponent from '@/entities/pid/pid-details.vue';
import PidClass from '@/entities/pid/pid-details.component';
import PidService from '@/entities/pid/pid.service';
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
  describe('Pid Management Detail Component', () => {
    let wrapper: Wrapper<PidClass>;
    let comp: PidClass;
    let pidServiceStub: SinonStubbedInstance<PidService>;

    beforeEach(() => {
      pidServiceStub = sinon.createStubInstance<PidService>(PidService);

      wrapper = shallowMount<PidClass>(PidDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { pidService: () => pidServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPid = { id: 123 };
        pidServiceStub.find.resolves(foundPid);

        // WHEN
        comp.retrievePid(123);
        await comp.$nextTick();

        // THEN
        expect(comp.pid).toBe(foundPid);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPid = { id: 123 };
        pidServiceStub.find.resolves(foundPid);

        // WHEN
        comp.beforeRouteEnter({ params: { pidId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.pid).toBe(foundPid);
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
