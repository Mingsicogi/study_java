/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GameServerUpdateComponent from '@/entities/game-server/game-server-update.vue';
import GameServerClass from '@/entities/game-server/game-server-update.component';
import GameServerService from '@/entities/game-server/game-server.service';

import GameService from '@/entities/game/game.service';
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
  describe('GameServer Management Update Component', () => {
    let wrapper: Wrapper<GameServerClass>;
    let comp: GameServerClass;
    let gameServerServiceStub: SinonStubbedInstance<GameServerService>;

    beforeEach(() => {
      gameServerServiceStub = sinon.createStubInstance<GameServerService>(GameServerService);

      wrapper = shallowMount<GameServerClass>(GameServerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          gameServerService: () => gameServerServiceStub,
          alertService: () => new AlertService(),

          gameService: () =>
            sinon.createStubInstance<GameService>(GameService, {
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
        comp.gameServer = entity;
        gameServerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameServerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.gameServer = entity;
        gameServerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(gameServerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGameServer = { id: 123 };
        gameServerServiceStub.find.resolves(foundGameServer);
        gameServerServiceStub.retrieve.resolves([foundGameServer]);

        // WHEN
        comp.beforeRouteEnter({ params: { gameServerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.gameServer).toBe(foundGameServer);
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
