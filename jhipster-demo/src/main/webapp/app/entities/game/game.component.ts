import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGame } from '@/shared/model/game.model';

import GameService from './game.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Game extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public games: IGame[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGames();
  }

  public clear(): void {
    this.retrieveAllGames();
  }

  public retrieveAllGames(): void {
    this.isFetching = true;
    this.gameService()
      .retrieve()
      .then(
        res => {
          this.games = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IGame): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGame(): void {
    this.gameService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.game.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGames();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
