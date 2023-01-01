<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="gnid">
        <h2 class="jh-entity-heading" data-cy="gnidDetailsHeading">
          <span v-text="$t('shopApp.gnid.detail.title')">Gnid</span> {{ gnid.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.gnid.gnid')">Gnid</span>
          </dt>
          <dd>
            <span>{{ gnid.gnid }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.gnid.gameCd')">Game Cd</span>
          </dt>
          <dd>
            <span>{{ gnid.gameCd }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.gnid.game')">Game</span>
          </dt>
          <dd>
            <div v-if="gnid.game">
              <router-link :to="{ name: 'GameView', params: { gameId: gnid.game.id } }">{{ gnid.game.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('shopApp.gnid.spid')">Spid</span>
          </dt>
          <dd>
            <div v-if="gnid.spid">
              <router-link :to="{ name: 'SpidView', params: { spidId: gnid.spid.id } }">{{ gnid.spid.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="gnid.id" :to="{ name: 'GnidEdit', params: { gnidId: gnid.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./gnid-details.component.ts"></script>
