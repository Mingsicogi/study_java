/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GameServerDetailComponent from '@/entities/game-server/game-server-details.vue';
import GameServerClass from '@/entities/game-server/game-server-details.component';
import GameServerService from '@/entities/game-server/game-server.service';
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
  describe('GameServer Management Detail Component', () => {
    let wrapper: Wrapper<GameServerClass>;
    let comp: GameServerClass;
    let gameServerServiceStub: SinonStubbedInstance<GameServerService>;

    beforeEach(() => {
      gameServerServiceStub = sinon.createStubInstance<GameServerService>(GameServerService);

      wrapper = shallowMount<GameServerClass>(GameServerDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { gameServerService: () => gameServerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGameServer = { id: 123 };
        gameServerServiceStub.find.resolves(foundGameServer);

        // WHEN
        comp.retrieveGameServer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.gameServer).toBe(foundGameServer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGameServer = { id: 123 };
        gameServerServiceStub.find.resolves(foundGameServer);

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
