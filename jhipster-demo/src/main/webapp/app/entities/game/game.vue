<template>
  <div>
    <h2 id="page-heading" data-cy="GameHeading">
      <span v-text="$t('shopApp.game.home.title')" id="game-heading">Games</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopApp.game.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GameCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-game">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopApp.game.home.createLabel')"> Create a new Game </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && games && games.length === 0">
      <span v-text="$t('shopApp.game.home.notFound')">No games found</span>
    </div>
    <div class="table-responsive" v-if="games && games.length > 0">
      <table class="table table-striped" aria-describedby="games">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('shopApp.game.gameId')">Game Id</span></th>
            <th scope="row"><span v-text="$t('shopApp.game.gameNm')">Game Nm</span></th>
            <th scope="row"><span v-text="$t('shopApp.game.gameCd')">Game Cd</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="game in games" :key="game.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GameView', params: { gameId: game.id } }">{{ game.id }}</router-link>
            </td>
            <td>{{ game.gameId }}</td>
            <td>{{ game.gameNm }}</td>
            <td>{{ game.gameCd }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GameView', params: { gameId: game.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GameEdit', params: { gameId: game.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(game)"
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
        ><span id="shopApp.game.delete.question" data-cy="gameDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-game-heading" v-text="$t('shopApp.game.delete.question', { id: removeId })">
          Are you sure you want to delete this Game?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-game"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGame()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./game.component.ts"></script>
