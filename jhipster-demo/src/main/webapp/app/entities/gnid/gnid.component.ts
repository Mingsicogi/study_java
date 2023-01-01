import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGnid } from '@/shared/model/gnid.model';

import GnidService from './gnid.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Gnid extends Vue {
  @Inject('gnidService') private gnidService: () => GnidService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public gnids: IGnid[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGnids();
  }

  public clear(): void {
    this.retrieveAllGnids();
  }

  public retrieveAllGnids(): void {
    this.isFetching = true;
    this.gnidService()
      .retrieve()
      .then(
        res => {
          this.gnids = res.data;
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

  public prepareRemove(instance: IGnid): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGnid(): void {
    this.gnidService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.gnid.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGnids();
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
