<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="shopApp.game.home.createOrEditLabel" data-cy="GameCreateUpdateHeading" v-text="$t('shopApp.game.home.createOrEditLabel')">
          Create or edit a Game
        </h2>
        <div>
          <div class="form-group" v-if="game.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="game.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.game.gameId')" for="game-gameId">Game Id</label>
            <input
              type="number"
              class="form-control"
              name="gameId"
              id="game-gameId"
              data-cy="gameId"
              :class="{ valid: !$v.game.gameId.$invalid, invalid: $v.game.gameId.$invalid }"
              v-model.number="$v.game.gameId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.game.gameNm')" for="game-gameNm">Game Nm</label>
            <input
              type="text"
              class="form-control"
              name="gameNm"
              id="game-gameNm"
              data-cy="gameNm"
              :class="{ valid: !$v.game.gameNm.$invalid, invalid: $v.game.gameNm.$invalid }"
              v-model="$v.game.gameNm.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.game.gameCd')" for="game-gameCd">Game Cd</label>
            <input
              type="text"
              class="form-control"
              name="gameCd"
              id="game-gameCd"
              data-cy="gameCd"
              :class="{ valid: !$v.game.gameCd.$invalid, invalid: $v.game.gameCd.$invalid }"
              v-model="$v.game.gameCd.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.game.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./game-update.component.ts"></script>
