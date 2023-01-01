import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISpid } from '@/shared/model/spid.model';

import SpidService from './spid.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Spid extends Vue {
  @Inject('spidService') private spidService: () => SpidService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public spids: ISpid[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSpids();
  }

  public clear(): void {
    this.retrieveAllSpids();
  }

  public retrieveAllSpids(): void {
    this.isFetching = true;
    this.spidService()
      .retrieve()
      .then(
        res => {
          this.spids = res.data;
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

  public prepareRemove(instance: ISpid): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSpid(): void {
    this.spidService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.spid.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllSpids();
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
