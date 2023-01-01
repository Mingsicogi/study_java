<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="pid">
        <h2 class="jh-entity-heading" data-cy="pidDetailsHeading"><span v-text="$t('shopApp.pid.detail.title')">Pid</span> {{ pid.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.pid.pid')">Pid</span>
          </dt>
          <dd>
            <span>{{ pid.pid }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.pid.phone')">Phone</span>
          </dt>
          <dd>
            <span>{{ pid.phone }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.pid.ci')">Ci</span>
          </dt>
          <dd>
            <span>{{ pid.ci }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.pid.di')">Di</span>
          </dt>
          <dd>
            <span>{{ pid.di }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="pid.id" :to="{ name: 'PidEdit', params: { pidId: pid.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./pid-details.component.ts"></script>
