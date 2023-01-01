import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import GameServerService from '@/entities/game-server/game-server.service';
import { IGameServer } from '@/shared/model/game-server.model';

import { IGame, Game } from '@/shared/model/game.model';
import GameService from './game.service';

const validations: any = {
  game: {
    gameId: {},
    gameNm: {},
    gameCd: {},
  },
};

@Component({
  validations,
})
export default class GameUpdate extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('alertService') private alertService: () => AlertService;

  public game: IGame = new Game();

  @Inject('gameServerService') private gameServerService: () => GameServerService;

  public gameServers: IGameServer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameId) {
        vm.retrieveGame(to.params.gameId);
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
    if (this.game.id) {
      this.gameService()
        .update(this.game)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.game.updated', { param: param.id });
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
      this.gameService()
        .create(this.game)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.game.created', { param: param.id });
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

  public retrieveGame(gameId): void {
    this.gameService()
      .find(gameId)
      .then(res => {
        this.game = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gameServerService()
      .retrieve()
      .then(res => {
        this.gameServers = res.data;
      });
  }
}
