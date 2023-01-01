/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PidComponent from '@/entities/pid/pid.vue';
import PidClass from '@/entities/pid/pid.component';
import PidService from '@/entities/pid/pid.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Pid Management Component', () => {
    let wrapper: Wrapper<PidClass>;
    let comp: PidClass;
    let pidServiceStub: SinonStubbedInstance<PidService>;

    beforeEach(() => {
      pidServiceStub = sinon.createStubInstance<PidService>(PidService);
      pidServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PidClass>(PidComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          pidService: () => pidServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      pidServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPids();
      await comp.$nextTick();

      // THEN
      expect(pidServiceStub.retrieve.called).toBeTruthy();
      expect(comp.pids[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      pidServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(pidServiceStub.retrieve.callCount).toEqual(1);

      comp.removePid();
      await comp.$nextTick();

      // THEN
      expect(pidServiceStub.delete.called).toBeTruthy();
      expect(pidServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
