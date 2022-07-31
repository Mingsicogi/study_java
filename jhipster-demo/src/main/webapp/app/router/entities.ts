import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Game = () => import('@/entities/game/game.vue');
// prettier-ignore
const GameUpdate = () => import('@/entities/game/game-update.vue');
// prettier-ignore
const GameDetails = () => import('@/entities/game/game-details.vue');
// prettier-ignore
const GameServer = () => import('@/entities/game-server/game-server.vue');
// prettier-ignore
const GameServerUpdate = () => import('@/entities/game-server/game-server-update.vue');
// prettier-ignore
const GameServerDetails = () => import('@/entities/game-server/game-server-details.vue');
// prettier-ignore
const Nid = () => import('@/entities/nid/nid.vue');
// prettier-ignore
const NidUpdate = () => import('@/entities/nid/nid-update.vue');
// prettier-ignore
const NidDetails = () => import('@/entities/nid/nid-details.vue');
// prettier-ignore
const Gnid = () => import('@/entities/gnid/gnid.vue');
// prettier-ignore
const GnidUpdate = () => import('@/entities/gnid/gnid-update.vue');
// prettier-ignore
const GnidDetails = () => import('@/entities/gnid/gnid-details.vue');
// prettier-ignore
const Spid = () => import('@/entities/spid/spid.vue');
// prettier-ignore
const SpidUpdate = () => import('@/entities/spid/spid-update.vue');
// prettier-ignore
const SpidDetails = () => import('@/entities/spid/spid-details.vue');
// prettier-ignore
const Pid = () => import('@/entities/pid/pid.vue');
// prettier-ignore
const PidUpdate = () => import('@/entities/pid/pid-update.vue');
// prettier-ignore
const PidDetails = () => import('@/entities/pid/pid-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'game',
      name: 'Game',
      component: Game,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/new',
      name: 'GameCreate',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/:gameId/edit',
      name: 'GameEdit',
      component: GameUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game/:gameId/view',
      name: 'GameView',
      component: GameDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game-server',
      name: 'GameServer',
      component: GameServer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game-server/new',
      name: 'GameServerCreate',
      component: GameServerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game-server/:gameServerId/edit',
      name: 'GameServerEdit',
      component: GameServerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'game-server/:gameServerId/view',
      name: 'GameServerView',
      component: GameServerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nid',
      name: 'Nid',
      component: Nid,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nid/new',
      name: 'NidCreate',
      component: NidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nid/:nidId/edit',
      name: 'NidEdit',
      component: NidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'nid/:nidId/view',
      name: 'NidView',
      component: NidDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'gnid',
      name: 'Gnid',
      component: Gnid,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'gnid/new',
      name: 'GnidCreate',
      component: GnidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'gnid/:gnidId/edit',
      name: 'GnidEdit',
      component: GnidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'gnid/:gnidId/view',
      name: 'GnidView',
      component: GnidDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spid',
      name: 'Spid',
      component: Spid,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spid/new',
      name: 'SpidCreate',
      component: SpidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spid/:spidId/edit',
      name: 'SpidEdit',
      component: SpidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'spid/:spidId/view',
      name: 'SpidView',
      component: SpidDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pid',
      name: 'Pid',
      component: Pid,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pid/new',
      name: 'PidCreate',
      component: PidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pid/:pidId/edit',
      name: 'PidEdit',
      component: PidUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pid/:pidId/view',
      name: 'PidView',
      component: PidDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
