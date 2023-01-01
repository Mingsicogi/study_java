/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GnidComponent from '@/entities/gnid/gnid.vue';
import GnidClass from '@/entities/gnid/gnid.component';
import GnidService from '@/entities/gnid/gnid.service';
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
  describe('Gnid Management Component', () => {
    let wrapper: Wrapper<GnidClass>;
    let comp: GnidClass;
    let gnidServiceStub: SinonStubbedInstance<GnidService>;

    beforeEach(() => {
      gnidServiceStub = sinon.createStubInstance<GnidService>(GnidService);
      gnidServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GnidClass>(GnidComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          gnidService: () => gnidServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      gnidServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGnids();
      await comp.$nextTick();

      // THEN
      expect(gnidServiceStub.retrieve.called).toBeTruthy();
      expect(comp.gnids[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      gnidServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(gnidServiceStub.retrieve.callCount).toEqual(1);

      comp.removeGnid();
      await comp.$nextTick();

      // THEN
      expect(gnidServiceStub.delete.called).toBeTruthy();
      expect(gnidServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
