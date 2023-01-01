import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import GameService from '@/entities/game/game.service';
import { IGame } from '@/shared/model/game.model';

import { IGameServer, GameServer } from '@/shared/model/game-server.model';
import GameServerService from './game-server.service';

const validations: any = {
  gameServer: {
    gameServerId: {},
    region: {},
  },
};

@Component({
  validations,
})
export default class GameServerUpdate extends Vue {
  @Inject('gameServerService') private gameServerService: () => GameServerService;
  @Inject('alertService') private alertService: () => AlertService;

  public gameServer: IGameServer = new GameServer();

  @Inject('gameService') private gameService: () => GameService;

  public games: IGame[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameServerId) {
        vm.retrieveGameServer(to.params.gameServerId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.gameServer.id) {
      this.gameServerService()
        .update(this.gameServer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.gameServer.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.gameServerService()
        .create(this.gameServer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.gameServer.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveGameServer(gameServerId): void {
    this.gameServerService()
      .find(gameServerId)
      .then(res => {
        this.gameServer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gameService()
      .retrieve()
      .then(res => {
        this.games = res.data;
      });
  }
}
