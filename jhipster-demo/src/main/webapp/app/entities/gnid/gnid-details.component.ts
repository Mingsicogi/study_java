import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGnid } from '@/shared/model/gnid.model';
import GnidService from './gnid.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class GnidDetails extends Vue {
  @Inject('gnidService') private gnidService: () => GnidService;
  @Inject('alertService') private alertService: () => AlertService;

  public gnid: IGnid = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gnidId) {
        vm.retrieveGnid(to.params.gnidId);
      }
    });
  }

  public retrieveGnid(gnidId) {
    this.gnidService()
      .find(gnidId)
      .then(res => {
        this.gnid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
