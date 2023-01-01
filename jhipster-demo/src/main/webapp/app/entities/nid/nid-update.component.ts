import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import GnidService from '@/entities/gnid/gnid.service';
import { IGnid } from '@/shared/model/gnid.model';

import { INid, Nid } from '@/shared/model/nid.model';
import NidService from './nid.service';

const validations: any = {
  nid: {
    nid: {},
  },
};

@Component({
  validations,
})
export default class NidUpdate extends Vue {
  @Inject('nidService') private nidService: () => NidService;
  @Inject('alertService') private alertService: () => AlertService;

  public nid: INid = new Nid();

  @Inject('gnidService') private gnidService: () => GnidService;

  public gnids: IGnid[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nidId) {
        vm.retrieveNid(to.params.nidId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.nid.id) {
      this.nidService()
        .update(this.nid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.nid.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.nidService()
        .create(this.nid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.nid.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveNid(nidId): void {
    this.nidService()
      .find(nidId)
      .then(res => {
        this.nid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gnidService()
      .retrieve()
      .then(res => {
        this.gnids = res.data;
      });
  }
}
