<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="game">
        <h2 class="jh-entity-heading" data-cy="gameDetailsHeading">
          <span v-text="$t('shopApp.game.detail.title')">Game</span> {{ game.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.game.gameId')">Game Id</span>
          </dt>
          <dd>
            <span>{{ game.gameId }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.game.gameNm')">Game Nm</span>
          </dt>
          <dd>
            <span>{{ game.gameNm }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.game.gameCd')">Game Cd</span>
          </dt>
          <dd>
            <span>{{ game.gameCd }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="game.id" :to="{ name: 'GameEdit', params: { gameId: game.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./game-details.component.ts"></script>
