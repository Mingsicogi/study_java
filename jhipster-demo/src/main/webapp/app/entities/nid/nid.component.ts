import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INid } from '@/shared/model/nid.model';

import NidService from './nid.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Nid extends Vue {
  @Inject('nidService') private nidService: () => NidService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public nids: INid[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNids();
  }

  public clear(): void {
    this.retrieveAllNids();
  }

  public retrieveAllNids(): void {
    this.isFetching = true;
    this.nidService()
      .retrieve()
      .then(
        res => {
          this.nids = res.data;
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

  public prepareRemove(instance: INid): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNid(): void {
    this.nidService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('shopApp.nid.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllNids();
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
