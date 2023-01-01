/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import GameServerComponent from '@/entities/game-server/game-server.vue';
import GameServerClass from '@/entities/game-server/game-server.component';
import GameServerService from '@/entities/game-server/game-server.service';
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
  describe('GameServer Management Component', () => {
    let wrapper: Wrapper<GameServerClass>;
    let comp: GameServerClass;
    let gameServerServiceStub: SinonStubbedInstance<GameServerService>;

    beforeEach(() => {
      gameServerServiceStub = sinon.createStubInstance<GameServerService>(GameServerService);
      gameServerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GameServerClass>(GameServerComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          gameServerService: () => gameServerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      gameServerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGameServers();
      await comp.$nextTick();

      // THEN
      expect(gameServerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.gameServers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      gameServerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(gameServerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeGameServer();
      await comp.$nextTick();

      // THEN
      expect(gameServerServiceStub.delete.called).toBeTruthy();
      expect(gameServerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
