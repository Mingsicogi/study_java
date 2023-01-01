/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NidComponent from '@/entities/nid/nid.vue';
import NidClass from '@/entities/nid/nid.component';
import NidService from '@/entities/nid/nid.service';
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
  describe('Nid Management Component', () => {
    let wrapper: Wrapper<NidClass>;
    let comp: NidClass;
    let nidServiceStub: SinonStubbedInstance<NidService>;

    beforeEach(() => {
      nidServiceStub = sinon.createStubInstance<NidService>(NidService);
      nidServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NidClass>(NidComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          nidService: () => nidServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      nidServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllNids();
      await comp.$nextTick();

      // THEN
      expect(nidServiceStub.retrieve.called).toBeTruthy();
      expect(comp.nids[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      nidServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(nidServiceStub.retrieve.callCount).toEqual(1);

      comp.removeNid();
      await comp.$nextTick();

      // THEN
      expect(nidServiceStub.delete.called).toBeTruthy();
      expect(nidServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
