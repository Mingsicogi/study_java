<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="shopApp.gnid.home.createOrEditLabel" data-cy="GnidCreateUpdateHeading" v-text="$t('shopApp.gnid.home.createOrEditLabel')">
          Create or edit a Gnid
        </h2>
        <div>
          <div class="form-group" v-if="gnid.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="gnid.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gnid.gnid')" for="gnid-gnid">Gnid</label>
            <input
              type="number"
              class="form-control"
              name="gnid"
              id="gnid-gnid"
              data-cy="gnid"
              :class="{ valid: !$v.gnid.gnid.$invalid, invalid: $v.gnid.gnid.$invalid }"
              v-model.number="$v.gnid.gnid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gnid.gameCd')" for="gnid-gameCd">Game Cd</label>
            <input
              type="text"
              class="form-control"
              name="gameCd"
              id="gnid-gameCd"
              data-cy="gameCd"
              :class="{ valid: !$v.gnid.gameCd.$invalid, invalid: $v.gnid.gameCd.$invalid }"
              v-model="$v.gnid.gameCd.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gnid.game')" for="gnid-game">Game</label>
            <select class="form-control" id="gnid-game" data-cy="game" name="game" v-model="gnid.game">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="gnid.game && gameOption.id === gnid.game.id ? gnid.game : gameOption"
                v-for="gameOption in games"
                :key="gameOption.id"
              >
                {{ gameOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.gnid.spid')" for="gnid-spid">Spid</label>
            <select class="form-control" id="gnid-spid" data-cy="spid" name="spid" v-model="gnid.spid">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="gnid.spid && spidOption.id === gnid.spid.id ? gnid.spid : spidOption"
                v-for="spidOption in spids"
                :key="spidOption.id"
              >
                {{ spidOption.id }}
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
            :disabled="$v.gnid.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./gnid-update.component.ts"></script>
