import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGame } from '@/shared/model/game.model';
import GameService from './game.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class GameDetails extends Vue {
  @Inject('gameService') private gameService: () => GameService;
  @Inject('alertService') private alertService: () => AlertService;

  public game: IGame = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gameId) {
        vm.retrieveGame(to.params.gameId);
      }
    });
  }

  public retrieveGame(gameId) {
    this.gameService()
      .find(gameId)
      .then(res => {
        this.game = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
