import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPid } from '@/shared/model/pid.model';

import PidService from './pid.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Pid extends Vue {
  @Inject('pidService') private pidService: () => PidService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public pids: IPid[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPids();
  }

  public clear(): void {
    this.retrieveAllPids();
  }

  public retrieveAllPids(): void {
    this.isFetching = true;
    this.pidService()
      .retrieve()
      .then(
        res => {
          this.pids = res.data;
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

  public prepareRemove(instance: IPid): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePid(): void {
    this.pidService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.pid.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPids();
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
