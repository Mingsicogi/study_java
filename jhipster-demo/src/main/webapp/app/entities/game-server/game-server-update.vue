<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopApp.gameServer.home.createOrEditLabel"
          data-cy="GameServerCreateUpdateHeading"
          v-text="$t('shopApp.gameServer.home.createOrEditLabel')"
        >
          Create or edit a GameServer
        </h2>
        <div>
          <div class="form-group" v-if="gameServer.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="gameServer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gameServer.gameServerId')" for="game-server-gameServerId"
              >Game Server Id</label
            >
            <input
              type="text"
              class="form-control"
              name="gameServerId"
              id="game-server-gameServerId"
              data-cy="gameServerId"
              :class="{ valid: !$v.gameServer.gameServerId.$invalid, invalid: $v.gameServer.gameServerId.$invalid }"
              v-model="$v.gameServer.gameServerId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gameServer.region')" for="game-server-region">Region</label>
            <input
              type="text"
              class="form-control"
              name="region"
              id="game-server-region"
              data-cy="region"
              :class="{ valid: !$v.gameServer.region.$invalid, invalid: $v.gameServer.region.$invalid }"
              v-model="$v.gameServer.region.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gameServer.game')" for="game-server-game">Game</label>
            <select class="form-control" id="game-server-game" data-cy="game" name="game" v-model="gameServer.game">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="gameServer.game && gameOption.id === gameServer.game.id ? gameServer.game : gameOption"
                v-for="gameOption in games"
                :key="gameOption.id"
              >
                {{ gameOption.id }}
              </option>
            </select>
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
            :disabled="$v.gameServer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./game-server-update.component.ts"></script>
