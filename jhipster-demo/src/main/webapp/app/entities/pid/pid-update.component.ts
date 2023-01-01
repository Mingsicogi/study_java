import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import SpidService from '@/entities/spid/spid.service';
import { ISpid } from '@/shared/model/spid.model';

import { IPid, Pid } from '@/shared/model/pid.model';
import PidService from './pid.service';

const validations: any = {
  pid: {
    pid: {},
    phone: {},
    ci: {},
    di: {},
  },
};

@Component({
  validations,
})
export default class PidUpdate extends Vue {
  @Inject('pidService') private pidService: () => PidService;
  @Inject('alertService') private alertService: () => AlertService;

  public pid: IPid = new Pid();

  @Inject('spidService') private spidService: () => SpidService;

  public spids: ISpid[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pidId) {
        vm.retrievePid(to.params.pidId);
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
    if (this.pid.id) {
      this.pidService()
        .update(this.pid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.pid.updated', { param: param.id });
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
      this.pidService()
        .create(this.pid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.pid.created', { param: param.id });
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

  public retrievePid(pidId): void {
    this.pidService()
      .find(pidId)
      .then(res => {
        this.pid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.spidService()
      .retrieve()
      .then(res => {
        this.spids = res.data;
      });
  }
}
