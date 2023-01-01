<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="shopApp.nid.home.createOrEditLabel" data-cy="NidCreateUpdateHeading" v-text="$t('shopApp.nid.home.createOrEditLabel')">
          Create or edit a Nid
        </h2>
        <div>
          <div class="form-group" v-if="nid.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="nid.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.nid.nid')" for="nid-nid">Nid</label>
            <input
              type="number"
              class="form-control"
              name="nid"
              id="nid-nid"
              data-cy="nid"
              :class="{ valid: !$v.nid.nid.$invalid, invalid: $v.nid.nid.$invalid }"
              v-model.number="$v.nid.nid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.nid.gnid')" for="nid-gnid">Gnid</label>
            <select class="form-control" id="nid-gnid" data-cy="gnid" name="gnid" v-model="nid.gnid">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="nid.gnid && gnidOption.id === nid.gnid.id ? nid.gnid : gnidOption"
                v-for="gnidOption in gnids"
                :key="gnidOption.id"
              >
                {{ gnidOption.id }}
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
            :disabled="$v.nid.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./nid-update.component.ts"></script>
