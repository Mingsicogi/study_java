<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="shopApp.pid.home.createOrEditLabel" data-cy="PidCreateUpdateHeading" v-text="$t('shopApp.pid.home.createOrEditLabel')">
          Create or edit a Pid
        </h2>
        <div>
          <div class="form-group" v-if="pid.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="pid.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.pid.pid')" for="pid-pid">Pid</label>
            <input
              type="number"
              class="form-control"
              name="pid"
              id="pid-pid"
              data-cy="pid"
              :class="{ valid: !$v.pid.pid.$invalid, invalid: $v.pid.pid.$invalid }"
              v-model.number="$v.pid.pid.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.pid.phone')" for="pid-phone">Phone</label>
            <input
              type="text"
              class="form-control"
              name="phone"
              id="pid-phone"
              data-cy="phone"
              :class="{ valid: !$v.pid.phone.$invalid, invalid: $v.pid.phone.$invalid }"
              v-model="$v.pid.phone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.pid.ci')" for="pid-ci">Ci</label>
            <input
              type="text"
              class="form-control"
              name="ci"
              id="pid-ci"
              data-cy="ci"
              :class="{ valid: !$v.pid.ci.$invalid, invalid: $v.pid.ci.$invalid }"
              v-model="$v.pid.ci.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopApp.pid.di')" for="pid-di">Di</label>
            <input
              type="text"
              class="form-control"
              name="di"
              id="pid-di"
              data-cy="di"
              :class="{ valid: !$v.pid.di.$invalid, invalid: $v.pid.di.$invalid }"
              v-model="$v.pid.di.$model"
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
            :disabled="$v.pid.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./pid-update.component.ts"></script>
