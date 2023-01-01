<template>
  <div>
    <h2 id="page-heading" data-cy="NidHeading">
      <span v-text="$t('shopApp.nid.home.title')" id="nid-heading">Nids</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopApp.nid.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NidCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-nid">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopApp.nid.home.createLabel')"> Create a new Nid </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && nids && nids.length === 0">
      <span v-text="$t('shopApp.nid.home.notFound')">No nids found</span>
    </div>
    <div class="table-responsive" v-if="nids && nids.length > 0">
      <table class="table table-striped" aria-describedby="nids">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('shopApp.nid.nid')">Nid</span></th>
            <th scope="row"><span v-text="$t('shopApp.nid.gnid')">Gnid</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="nid in nids" :key="nid.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NidView', params: { nidId: nid.id } }">{{ nid.id }}</router-link>
            </td>
            <td>{{ nid.nid }}</td>
            <td>
              <div v-if="nid.gnid">
                <router-link :to="{ name: 'GnidView', params: { gnidId: nid.gnid.id } }">{{ nid.gnid.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NidView', params: { nidId: nid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NidEdit', params: { nidId: nid.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(nid)"
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
        ><span id="shopApp.nid.delete.question" data-cy="nidDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-nid-heading" v-text="$t('shopApp.nid.delete.question', { id: removeId })">
          Are you sure you want to delete this Nid?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-nid"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNid()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./nid.component.ts"></script>
