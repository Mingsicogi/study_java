import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import GameService from '@/entities/game/game.service';
import { IGame } from '@/shared/model/game.model';

import NidService from '@/entities/nid/nid.service';
import { INid } from '@/shared/model/nid.model';

import SpidService from '@/entities/spid/spid.service';
import { ISpid } from '@/shared/model/spid.model';

import { IGnid, Gnid } from '@/shared/model/gnid.model';
import GnidService from './gnid.service';

const validations: any = {
  gnid: {
    gnid: {},
    gameCd: {},
  },
};

@Component({
  validations,
})
export default class GnidUpdate extends Vue {
  @Inject('gnidService') private gnidService: () => GnidService;
  @Inject('alertService') private alertService: () => AlertService;

  public gnid: IGnid = new Gnid();

  @Inject('gameService') private gameService: () => GameService;

  public games: IGame[] = [];

  @Inject('nidService') private nidService: () => NidService;

  public nids: INid[] = [];

  @Inject('spidService') private spidService: () => SpidService;

  public spids: ISpid[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.gnidId) {
        vm.retrieveGnid(to.params.gnidId);
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
    if (this.gnid.id) {
      this.gnidService()
        .update(this.gnid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.gnid.updated', { param: param.id });
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
      this.gnidService()
        .create(this.gnid)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('shopApp.gnid.created', { param: param.id });
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

  public retrieveGnid(gnidId): void {
    this.gnidService()
      .find(gnidId)
      .then(res => {
        this.gnid = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.gameService()
      .retrieve()
      .then(res => {
        this.games = res.data;
      });
    this.nidService()
      .retrieve()
      .then(res => {
        this.nids = res.data;
      });
    this.spidService()
      .retrieve()
      .then(res => {
        this.spids = res.data;
      });
  }
}
