import { Component, Vue, Inject } from 'vue-property-decorator';

import { INid } from '@/shared/model/nid.model';
import NidService from './nid.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NidDetails extends Vue {
  @Inject('nidService') private nidService: () => NidService;
  @Inject('alertService') private alertService: () => AlertService;

  public nid: INid = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nidId) {
        vm.retrieveNid(to.params.nidId);
      }
    });
  }

  public retrieveNid(nidId) {
    this.nidService()
      .find(nidId)
      .then(res => {
        this.nid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
