<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="nid">
        <h2 class="jh-entity-heading" data-cy="nidDetailsHeading"><span v-text="$t('shopApp.nid.detail.title')">Nid</span> {{ nid.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopApp.nid.nid')">Nid</span>
          </dt>
          <dd>
            <span>{{ nid.nid }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopApp.nid.gnid')">Gnid</span>
          </dt>
          <dd>
            <div v-if="nid.gnid">
              <router-link :to="{ name: 'GnidView', params: { gnidId: nid.gnid.id } }">{{ nid.gnid.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="nid.id" :to="{ name: 'NidEdit', params: { nidId: nid.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./nid-details.component.ts"></script>
