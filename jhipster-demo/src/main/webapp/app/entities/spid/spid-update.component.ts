import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import GnidService from '@/entities/gnid/gnid.service';
import { IGnid } from '@/shared/model/gnid.model';

import PidService from '@/entities/pid/pid.service';
import { IPid } from '@/shared/model/pid.model';

import { ISpid, Spid } from '@/shared/model/spid.model';
import SpidService from './spid.service';

const validations: any = {
  spid: {
    spid: {},
    email: {},
  },
};

@Component({
  validations,
})
export default class SpidUpdate extends Vue {
  @Inject('spidService') private spidService: () => SpidService;
  @Inject('alertService') private alertService: () => AlertService;

  public spid: ISpid = new Spid();

  @Inject('gnidService') private gnidService: () => GnidService;

  public gnids: IGnid[] = [];

  @Inject('pidService') private pidService: () => PidService;

  public pids: IPid[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.spidId) {
        vm.retrieveSpid(to.params.spidId);
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
    if (this.spid.id) {
      this.spidService()
        .update(this.spid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.spid.updated', { param: param.id });
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
      this.spidService()
        .create(this.spid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.spid.created', { param: param.id });
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

  public retrieveSpid(spidId): void {
    this.spidService()
      .find(spidId)
      .then(res => {
        this.spid = res;
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
    this.pidService()
      .retrieve()
      .then(res => {
        this.pids = res.data;
      });
  }
}
