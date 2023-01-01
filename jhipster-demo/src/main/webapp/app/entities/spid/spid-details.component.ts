import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISpid } from '@/shared/model/spid.model';
import SpidService from './spid.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SpidDetails extends Vue {
  @Inject('spidService') private spidService: () => SpidService;
  @Inject('alertService') private alertService: () => AlertService;

  public spid: ISpid = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.spidId) {
        vm.retrieveSpid(to.params.spidId);
      }
    });
  }

  public retrieveSpid(spidId) {
    this.spidService()
      .find(spidId)
      .then(res => {
        this.spid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
