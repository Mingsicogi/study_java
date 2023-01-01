import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGameServer } from '@/shared/model/game-server.model';

import GameServerService from './game-server.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class GameServer extends Vue {
  @Inject('gameServerService') private gameServerService: () => GameServerService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public gameServers: IGameServer[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGameServers();
  }

  public clear(): void {
    this.retrieveAllGameServers();
  }

  public retrieveAllGameServers(): void {
    this.isFetching = true;
    this.gameServerService()
      .retrieve()
      .then(
        res => {
          this.gameServers = res.data;
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

  public prepareRemove(instance: IGameServer): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGameServer(): void {
    this.gameServerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.gameServer.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGameServers();
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
