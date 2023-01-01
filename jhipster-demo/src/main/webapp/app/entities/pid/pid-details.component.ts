import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPid } from '@/shared/model/pid.model';
import PidService from './pid.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PidDetails extends Vue {
  @Inject('pidService') private pidService: () => PidService;
  @Inject('alertService') private alertService: () => AlertService;

  public pid: IPid = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pidId) {
        vm.retrievePid(to.params.pidId);
      }
    });
  }

  public retrievePid(pidId) {
    this.pidService()
      .find(pidId)
      .then(res => {
        this.pid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
