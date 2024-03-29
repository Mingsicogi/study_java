/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NidUpdateComponent from '@/entities/nid/nid-update.vue';
import NidClass from '@/entities/nid/nid-update.component';
import NidService from '@/entities/nid/nid.service';

import GnidService from '@/entities/gnid/gnid.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Nid Management Update Component', () => {
    let wrapper: Wrapper<NidClass>;
    let comp: NidClass;
    let nidServiceStub: SinonStubbedInstance<NidService>;

    beforeEach(() => {
      nidServiceStub = sinon.createStubInstance<NidService>(NidService);

      wrapper = shallowMount<NidClass>(NidUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          nidService: () => nidServiceStub,
          alertService: () => new AlertService(),

          gnidService: () =>
            sinon.createStubInstance<GnidService>(GnidService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.nid = entity;
        nidServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nidServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.nid = entity;
        nidServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(nidServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNid = { id: 123 };
        nidServiceStub.find.resolves(foundNid);
        nidServiceStub.retrieve.resolves([foundNid]);

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
