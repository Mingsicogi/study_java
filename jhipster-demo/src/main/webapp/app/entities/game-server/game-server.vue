<template>
  <div>
    <h2 id="page-heading" data-cy="GameServerHeading">
      <span v-text="$t('shopApp.gameServer.home.title')" id="game-server-heading">Game Servers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopApp.gameServer.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GameServerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-game-server"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopApp.gameServer.home.createLabel')"> Create a new Game Server </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && gameServers && gameServers.length === 0">
      <span v-text="$t('shopApp.gameServer.home.notFound')">No gameServers found</span>
    </div>
    <div class="table-responsive" v-if="gameServers && gameServers.length > 0">
      <table class="table table-striped" aria-describedby="gameServers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('shopApp.gameServer.gameServerId')">Game Server Id</span></th>
            <th scope="row"><span v-text="$t('shopApp.gameServer.region')">Region</span></th>
            <th scope="row"><span v-text="$t('shopApp.gameServer.game')">Game</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="gameServer in gameServers" :key="gameServer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GameServerView', params: { gameServerId: gameServer.id } }">{{ gameServer.id }}</router-link>
            </td>
            <td>{{ gameServer.gameServerId }}</td>
            <td>{{ gameServer.region }}</td>
            <td>
              <div v-if="gameServer.game">
                <router-link :to="{ name: 'GameView', params: { gameId: gameServer.game.id } }">{{ gameServer.game.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GameServerView', params: { gameServerId: gameServer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GameServerEdit', params: { gameServerId: gameServer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(gameServer)"
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
        ><span id="shopApp.gameServer.delete.question" data-cy="gameServerDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-gameServer-heading" v-text="$t('shopApp.gameServer.delete.question', { id: removeId })">
          Are you sure you want to delete this Game Server?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-gameServer"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGameServer()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./game-server.component.ts"></script>
