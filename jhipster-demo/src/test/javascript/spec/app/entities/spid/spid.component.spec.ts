/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SpidComponent from '@/entities/spid/spid.vue';
import SpidClass from '@/entities/spid/spid.component';
import SpidService from '@/entities/spid/spid.service';
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
  describe('Spid Management Component', () => {
    let wrapper: Wrapper<SpidClass>;
    let comp: SpidClass;
    let spidServiceStub: SinonStubbedInstance<SpidService>;

    beforeEach(() => {
      spidServiceStub = sinon.createStubInstance<SpidService>(SpidService);
      spidServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SpidClass>(SpidComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          spidService: () => spidServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      spidServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSpids();
      await comp.$nextTick();

      // THEN
      expect(spidServiceStub.retrieve.called).toBeTruthy();
      expect(comp.spids[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      spidServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(spidServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSpid();
      await comp.$nextTick();

      // THEN
      expect(spidServiceStub.delete.called).toBeTruthy();
      expect(spidServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
