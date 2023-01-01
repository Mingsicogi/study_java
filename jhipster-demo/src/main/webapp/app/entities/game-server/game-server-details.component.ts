import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGameServer } from '@/shared/model/game-server.model';
import GameServerService from './game-server.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class GameServerDetails extends Vue {
  @Inject('gameServerService') private gameServerService: () => GameServerService;
  @Inject('alertService') private alertService: () => AlertService;

  public gameServer: IGameServer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameServerId) {
        vm.retrieveGameServer(to.params.gameServerId);
      }
    });
  }

  public retrieveGameServer(gameServerId) {
    this.gameServerService()
      .find(gameServerId)
      .then(res => {
        this.gameServer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
