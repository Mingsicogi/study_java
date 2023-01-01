<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="shopApp.spid.home.createOrEditLabel" data-cy="SpidCreateUpdateHeading" v-text="$t('shopApp.spid.home.createOrEditLabel')">
          Create or edit a Spid
        </h2>
        <div>
          <div class="form-group" v-if="spid.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="spid.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.spid.spid')" for="spid-spid">Spid</label>
            <input
              type="number"
              class="form-control"
              name="spid"
              id="spid-spid"
              data-cy="spid"
              :class="{ valid: !$v.spid.spid.$invalid, invalid: $v.spid.spid.$invalid }"
              v-model.number="$v.spid.spid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.spid.email')" for="spid-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="spid-email"
              data-cy="email"
              :class="{ valid: !$v.spid.email.$invalid, invalid: $v.spid.email.$invalid }"
              v-model="$v.spid.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.spid.pid')" for="spid-pid">Pid</label>
            <select class="form-control" id="spid-pid" data-cy="pid" name="pid" v-model="spid.pid">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="spid.pid && pidOption.id === spid.pid.id ? spid.pid : pidOption"
                v-for="pidOption in pids"
                :key="pidOption.id"
              >
                {{ pidOption.id }}
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
            :disabled="$v.spid.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./spid-update.component.ts"></script>
