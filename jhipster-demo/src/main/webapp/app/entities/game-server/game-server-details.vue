<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="gameServer">
        <h2 class="jh-entity-heading" data-cy="gameServerDetailsHeading">
          <span v-text="$t('shopApp.gameServer.detail.title')">GameServer</span> {{ gameServer.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.gameServer.gameServerId')">Game Server Id</span>
          </dt>
          <dd>
            <span>{{ gameServer.gameServerId }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.gameServer.region')">Region</span>
          </dt>
          <dd>
            <span>{{ gameServer.region }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.gameServer.game')">Game</span>
          </dt>
          <dd>
            <div v-if="gameServer.game">
              <router-link :to="{ name: 'GameView', params: { gameId: gameServer.game.id } }">{{ gameServer.game.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="gameServer.id"
          :to="{ name: 'GameServerEdit', params: { gameServerId: gameServer.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./game-server-details.component.ts"></script>
