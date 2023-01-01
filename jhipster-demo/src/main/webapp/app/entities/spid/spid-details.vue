<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="spid">
        <h2 class="jh-entity-heading" data-cy="spidDetailsHeading">
          <span v-text="$t('shopApp.spid.detail.title')">Spid</span> {{ spid.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.spid.spid')">Spid</span>
          </dt>
          <dd>
            <span>{{ spid.spid }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.spid.email')">Email</span>
          </dt>
          <dd>
            <span>{{ spid.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.spid.pid')">Pid</span>
          </dt>
          <dd>
            <div v-if="spid.pid">
              <router-link :to="{ name: 'PidView', params: { pidId: spid.pid.id } }">{{ spid.pid.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="spid.id" :to="{ name: 'SpidEdit', params: { spidId: spid.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./spid-details.component.ts"></script>
