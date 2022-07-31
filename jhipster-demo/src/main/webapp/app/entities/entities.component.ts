import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import GameService from './game/game.service';
import GameServerService from './game-server/game-server.service';
import NidService from './nid/nid.service';
import GnidService from './gnid/gnid.service';
import SpidService from './spid/spid.service';
import PidService from './pid/pid.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('gameService') private gameService = () => new GameService();
  @Provide('gameServerService') private gameServerService = () => new GameServerService();
  @Provide('nidService') private nidService = () => new NidService();
  @Provide('gnidService') private gnidService = () => new GnidService();
  @Provide('spidService') private spidService = () => new SpidService();
  @Provide('pidService') private pidService = () => new PidService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
