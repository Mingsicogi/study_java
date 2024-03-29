/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GnidUpdateComponent from '@/entities/gnid/gnid-update.vue';
import GnidClass from '@/entities/gnid/gnid-update.component';
import GnidService from '@/entities/gnid/gnid.service';

import GameService from '@/entities/game/game.service';

import NidService from '@/entities/nid/nid.service';

import SpidService from '@/entities/spid/spid.service';
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
  describe('Gnid Management Update Component', () => {
    let wrapper: Wrapper<GnidClass>;
    let comp: GnidClass;
    let gnidServiceStub: SinonStubbedInstance<GnidService>;

    beforeEach(() => {
      gnidServiceStub = sinon.createStubInstance<GnidService>(GnidService);

      wrapper = shallowMount<GnidClass>(GnidUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          gnidService: () => gnidServiceStub,
          alertService: () => new AlertService(),

          gameService: () =>
            sinon.createStubInstance<GameService>(GameService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          nidService: () =>
            sinon.createStubInstance<NidService>(NidService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          spidService: () =>
            sinon.createStubInstance<SpidService>(SpidService, {
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
        comp.gnid = entity;
        gnidServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gnidServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.gnid = entity;
        gnidServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gnidServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGnid = { id: 123 };
        gnidServiceStub.find.resolves(foundGnid);
        gnidServiceStub.retrieve.resolves([foundGnid]);

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
