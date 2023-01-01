<template>
  <div>
    <h2 id="page-heading" data-cy="GnidHeading">
      <span v-text="$t('shopApp.gnid.home.title')" id="gnid-heading">Gnids</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopApp.gnid.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GnidCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-gnid">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopApp.gnid.home.createLabel')"> Create a new Gnid </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && gnids && gnids.length === 0">
      <span v-text="$t('shopApp.gnid.home.notFound')">No gnids found</span>
    </div>
    <div class="table-responsive" v-if="gnids && gnids.length > 0">
      <table class="table table-striped" aria-describedby="gnids">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('shopApp.gnid.gnid')">Gnid</span></th>
            <th scope="row"><span v-text="$t('shopApp.gnid.gameCd')">Game Cd</span></th>
            <th scope="row"><span v-text="$t('shopApp.gnid.game')">Game</span></th>
            <th scope="row"><span v-text="$t('shopApp.gnid.spid')">Spid</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="gnid in gnids" :key="gnid.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GnidView', params: { gnidId: gnid.id } }">{{ gnid.id }}</router-link>
            </td>
            <td>{{ gnid.gnid }}</td>
            <td>{{ gnid.gameCd }}</td>
            <td>
              <div v-if="gnid.game">
                <router-link :to="{ name: 'GameView', params: { gameId: gnid.game.id } }">{{ gnid.game.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="gnid.spid">
                <router-link :to="{ name: 'SpidView', params: { spidId: gnid.spid.id } }">{{ gnid.spid.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GnidView', params: { gnidId: gnid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GnidEdit', params: { gnidId: gnid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(gnid)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="shopApp.gnid.delete.question" data-cy="gnidDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-gnid-heading" v-text="$t('shopApp.gnid.delete.question', { id: removeId })">
          Are you sure you want to delete this Gnid?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-gnid"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGnid()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./gnid.component.ts"></script>
